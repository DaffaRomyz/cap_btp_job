package customer.cap_btp_job.handlers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnInsert;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.mainservice.GenerateReportContext;
import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.Report;
import cds.gen.mainservice.Report_;
import cds.gen.mainservice.Transaction;
import cds.gen.mainservice.Transaction_;
import customer.cap_btp_job.util.CurrencyExchange;
import customer.cap_btp_job.util.CurrencyExchange.Currency;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServiceReportHandler implements EventHandler{
    
    @Autowired
    PersistenceService db;

    @On(event = GenerateReportContext.CDS_NAME)
    void onGenerateReport(GenerateReportContext context) {
        
        Instant endInstant = Instant.now();
        Instant startIntant = endInstant.minusSeconds(900);

        Report report = Report.create();

        report.setCurrencyCode("USD");
        report.setTimestamp(endInstant);

        Double amount = 0d;

        CqnSelect select = Select.from(Transaction_.CDS_NAME).where(w -> w.get(Transaction.TIMESTAMP).between(startIntant, endInstant));

        List<Transaction> transactionList = db.run(select).listOf(Transaction.class);

        for (Transaction transaction : transactionList) {
            
            Double convertedAmount = CurrencyExchange.convertCurrency(
                transaction.getAmount().doubleValue(), 
                Currency.valueOf(transaction.getCurrencyCode()), 
                Currency.valueOf("USD")
            );

            amount += convertedAmount;
        }

        report.setTotalmoved(new BigDecimal(amount));

        CqnInsert insert = Insert.into(Report_.CDS_NAME).entry(report);
        db.run(insert);

        context.setCompleted();
    }
}
