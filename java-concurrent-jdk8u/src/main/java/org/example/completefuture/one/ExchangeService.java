package org.example.completefuture.one;

public class ExchangeService {

    public enum CurrencyCode {
        EUR, USD;
    }

    public static double getUsdEur() {
        Util.delay(500L);
        return 0.8937;
    }
}
