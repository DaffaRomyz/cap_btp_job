package customer.cap_btp_job.util;

import java.util.Map;

public class CurrencyExchange {

    public static enum Currency {
        USD,
        IDR,
        CNY
    }

    // "USDIDR", 16000d,
    // "USDCNY", 6.4d,
    // "CNYUSD", 0.15625d,
    // "CNYIDR", 2500d,
    // "IDRUSD", 0.0000625d,
    // "IDRCNY", 0.00040d,
    public static Map<Currency, Map<Currency, Double>> exchangeRates = Map.of(
        Currency.USD, Map.of(Currency.IDR, 16000d, Currency.CNY, 6.4d),
        Currency.CNY, Map.of(Currency.USD, 0.15625d, Currency.IDR, 2500d),
        Currency.IDR, Map.of(Currency.USD, 0.0000625d, Currency.CNY, 0.00040d)
    );
    
    public static Double convertCurrency(Double amount, Currency currencyFrom, Currency currencyTo) {
        if (currencyFrom.compareTo(currencyTo) == 0) {
            return amount;
        }
        return amount * exchangeRates.get(currencyFrom).get(currencyTo);
    }
}
