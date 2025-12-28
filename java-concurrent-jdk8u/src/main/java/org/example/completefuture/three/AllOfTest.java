package org.example.completefuture.three;

import com.spotify.futures.CompletableFutures;
import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AllOfTest {



    @Test
    public void testAllOfCorrect() {
        CompletableFuture<String> futureA = CompletableFuture.completedFuture("A");
        CompletableFuture<String> futureB = CompletableFuture.completedFuture("B");
        CompletableFuture<String> futureC = CompletableFuture.completedFuture("C");

        CompletableFuture<String> joined = futureA.thenCombine(futureB, (a, b) -> a + b)
                .thenCombine(futureC, (a, b) -> a + b);
        joined.thenAccept(result -> {
            // 这里是异步执行的，不会阻塞主线程
            assertEquals("ABC", result);

        });
    }


        @Test
        public void testAllOfIncorrect() {
            expectTimeout(() -> {
                CompletableFuture<String> futureA = new CompletableFuture<>();
                CompletableFuture<String> futureB = new CompletableFuture<>();
                CompletableFuture<String> futureC = new CompletableFuture<>();
                CompletableFuture<String> futureD = new CompletableFuture<>();

                // Oops, forgot to include futureD here!
                CompletableFuture<Void> futureAll = CompletableFuture.allOf(futureA, futureB, futureC,futureD);
                CompletableFuture<String> joined = futureAll.thenApply(x -> futureA.join() + futureB.join() + futureC.join() + futureD.join());

                futureA.complete("A");
                futureB.complete("B");
                futureC.complete("C");
//                futureD.complete("D");

                // futureAll is complete now, but callback is deadlocked!

                joined.join();
            });
        }

    private void expectTimeout(Runnable runnable) {

        try {
            CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
                runnable.run();
                return null;
            });
            future.get(5, TimeUnit.SECONDS);
            fail("Unexpected success");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBetterApproach() {
        CompletableFuture<String> futureA = new CompletableFuture<>();
        CompletableFuture<String> futureB = new CompletableFuture<>();
        CompletableFuture<String> futureC = new CompletableFuture<>();

        CompletionStage<String> futureAll = CompletableFutures.combine(
                futureA, futureB, futureC,
                (a, b, c) -> a + b + c);

        futureA.complete("A");
        futureB.complete("B");
        futureC.complete("C");

        assertEquals("ABC", futureAll.toCompletableFuture().join());
    }





}


