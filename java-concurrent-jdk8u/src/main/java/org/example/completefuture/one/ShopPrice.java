package org.example.completefuture.one;

import java.util.Currency;

public class ShopPrice {

    private String mShop;
    private double mPrice;
    private ExchangeService.CurrencyCode mCurrency;

    public ShopPrice(String shop, double price, ExchangeService.CurrencyCode currency) {
        mShop = shop;
        mPrice = price;
        mCurrency = currency;
    }

    public String getShop() {
        return mShop;
    }

    public double getPrice() {
        return mPrice;
    }

    public ExchangeService.CurrencyCode getCurrency() {
        return mCurrency;
    }

    public void convertCurrency(double rate, ExchangeService.CurrencyCode toCurrency) {
        mPrice = mPrice * rate;
        mCurrency = toCurrency;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f %s", mShop, mPrice, getCurrency());
    }
}
