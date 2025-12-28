package org.example.completefuture.one;

import java.util.Currency;


public class Quote {


    private final String shopName;
    private final double price;
    private final DiscountService.DiscountCode discountCode;
    private final ExchangeService.CurrencyCode currency;

    public Quote(String shopName, double price, DiscountService.DiscountCode code,
                 ExchangeService.CurrencyCode currency) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = code;
        this.currency = currency;
    }

    public static Quote parse(String str) {
        String[] split = str.split(":");
        return new Quote(split[0], Double.parseDouble(split[1]),
                DiscountService.DiscountCode.valueOf(split[2]), ExchangeService.CurrencyCode.valueOf(split[3]));
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public DiscountService.DiscountCode getDiscountCode() {
        return discountCode;
    }

    public ExchangeService.CurrencyCode getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "shopName='" + shopName + '\'' +
                ", price=" + price +
                ", discountCode=" + discountCode +
                ", currency=" + currency +
                '}';
    }

}
