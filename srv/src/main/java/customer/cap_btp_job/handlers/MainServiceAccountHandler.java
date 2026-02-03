package customer.cap_btp_job.handlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.services.EventContext;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.mainservice.Account;
import cds.gen.mainservice.Account_;
import cds.gen.mainservice.Currencies;
import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.Transaction;
import cds.gen.mainservice.Transaction_;
import cds.gen.mainservice.UpdateAccountBalanceContext;
import customer.cap_btp_job.util.CurrencyExchange;
import customer.cap_btp_job.util.CurrencyExchange.Currency;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServiceAccountHandler implements EventHandler {

    @Autowired
    PersistenceService db;

    @On(event = UpdateAccountBalanceContext.CDS_NAME)
    void onUpdateAccountBalance(UpdateAccountBalanceContext context) {

        CqnSelect selectAllTranscation = Select.from(Transaction_.CDS_NAME);

        List<Transaction> transactionList = db.run(selectAllTranscation).listOf(Transaction.class);

        HashMap<String, Account> accountMap = new HashMap<>();

        for (Transaction transaction : transactionList) {
            String senderID = transaction.getSenderId();
            if (senderID != null && !senderID.isBlank()) {

                if (!accountMap.containsKey(senderID)) {
                    CqnSelect selectSender = Select.from(Account_.CDS_NAME).where(w -> w.get(Account.ID).eq(senderID));
                    Account sender = db.run(selectSender).single(Account.class);
                    sender.setBalance(BigDecimal.ZERO);
                    accountMap.put(senderID, sender);
                }

                Account sender = accountMap.get(senderID);

                Double convertedAmount = CurrencyExchange.convertCurrency(
                    transaction.getAmount().doubleValue(), 
                    Currency.valueOf(transaction.getCurrencyCode()), 
                    Currency.valueOf(sender.getCurrencyCode())
                );

                sender.setBalance(sender.getBalance().subtract(new BigDecimal(convertedAmount)));
            }

            String receiverID = transaction.getReceiverId();
            if (receiverID != null && !receiverID.isBlank()) {

                if (!accountMap.containsKey(receiverID)) {
                    CqnSelect selectReceiver = Select.from(Account_.CDS_NAME).where(w -> w.get(Account.ID).eq(receiverID));
                    Account receiver = db.run(selectReceiver).single(Account.class);
                    receiver.setBalance(BigDecimal.ZERO);
                    accountMap.put(receiverID, receiver);
                }
                Account receiver = accountMap.get(receiverID);

                Double convertedAmount = CurrencyExchange.convertCurrency(
                    transaction.getAmount().doubleValue(), 
                    Currency.valueOf(transaction.getCurrencyCode()), 
                    Currency.valueOf(receiver.getCurrencyCode())
                );

                receiver.setBalance(receiver.getBalance().add(new BigDecimal(convertedAmount)));
            }

        }

        if (!accountMap.isEmpty()) {
            CqnUpdate update = Update.entity(Account_.CDS_NAME).entries(accountMap.values());
            db.run(update);
        }
        context.setCompleted();
    }
}
