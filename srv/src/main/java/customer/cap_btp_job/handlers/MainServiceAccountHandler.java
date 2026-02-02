package customer.cap_btp_job.handlers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.mainservice.Account;
import cds.gen.mainservice.Account_;
import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.Transaction;
import cds.gen.mainservice.Transaction_;
import cds.gen.mainservice.UpdateAccountBalanceContext;

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
            if (!accountMap.containsKey(senderID)) {
                CqnSelect selectSender = Select.from(Account_.CDS_NAME).where(w -> w.get(Account.ID).eq(senderID));
                Account sender = db.run(selectSender).single(Account.class);
                accountMap.put(senderID, sender);
            }

            String receiverID = transaction.getReceiverId();
            if (!accountMap.containsKey(receiverID)) {
                CqnSelect selectReceiver = Select.from(Account_.CDS_NAME).where(w -> w.get(Account.ID).eq(receiverID));
                Account receiver = db.run(selectReceiver).single(Account.class);
                accountMap.put(receiverID, receiver);
            }

            accountMap.get(senderID).getBalance();
        }

    }
}
