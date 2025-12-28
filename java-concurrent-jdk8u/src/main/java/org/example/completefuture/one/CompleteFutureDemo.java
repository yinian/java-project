package org.example.completefuture.one;

import java.util.concurrent.CompletableFuture;

public class CompleteFutureDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5);
                future.thenApply(x -> x * 2)
                .thenAccept(System.out::println);

        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> 5);
        supplyAsync.thenCompose(x -> CompletableFuture.supplyAsync(() -> x * 2))
                .thenAccept(System.out::println);

        CompletableFuture<Integer> supplyAsync1 = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(() -> 10);
        supplyAsync1.thenCombine(supplyAsync2, (x, y) -> x + y)
                .thenAccept(System.out::println);

    }
}
