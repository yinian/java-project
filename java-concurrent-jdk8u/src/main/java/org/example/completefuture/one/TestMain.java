package org.example.completefuture.one;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.example.completefuture.one.TestHelpers.*;

public class TestMain {



    public static void main(String[] args) {

        BestPriceFinderTestSingleShoprunFindPriceAsync();

    }


    public static void BestPriceFinderTestAllShops() {
        BestPriceFinder mFinder = new BestPriceFinder();
        String mProduct = "BestProduct";  // "NA"
        say("> Calling findAllPricesAsync");
        long startTime = getTime();
        Stream<CompletableFuture<ShopPrice>> fut = mFinder.findAllPricesAsync(mProduct);
        say("< findAllPricesAsync returns after " + (getTime() - startTime) + " milliseconds");

        CompletableFuture[] futArr = fut.map(f -> f.thenAccept(price ->
                        System.out.println(String.format("%s (%d milliseconds)", price, (getTime() - startTime)))
                ))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futArr).join();
        say("All shops returned results after " + (getTime() - startTime) + " milliseconds");
    }


    public static void BestPriceFinderTestSingleShoprunFindPrice() {
         BestPriceFinder mFinder = new BestPriceFinder();
         String mProduct = "BestProduct";  // "NA"
         String mShop = "BestShop";
        say("> Calling findPrice");
        long startTime = getTime();
        ShopPrice price = mFinder.findPrice(mShop, mProduct);
        say("< findPrice returns after " + (getTime() - startTime) + " milliseconds");
        say("I have been blocked until now :(");
        say(price.toString());
    }
    public static void BestPriceFinderTestSingleShoprunFindPriceAsync() {
        BestPriceFinder mFinder = new BestPriceFinder();
        String mProduct = "BestProduct";  // "NA"
        String mShop = "BestShop";
        say("> Calling findPriceAsync");
        long startTime = getTime();
        Future<ShopPrice> futurePrice = mFinder.findPriceAsync(mShop, mProduct);
        say("< findPriceAsync returns a Future after " + (getTime() - startTime) + " milliseconds");

        say("I can now dow anything I want :)");
        doSomethingElse();

        ShopPrice price;
        try {
            price = futurePrice.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        say("The Future is ready after " + (getTime() - startTime) + " milliseconds");
        say(price.toString());
    }



}
