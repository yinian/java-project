package org.example;


import java.util.concurrent.*;

public class FutureDoneExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        futureAndCallable();
    }


    public static void futureDone() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> future = executorService.submit(() -> {
            Thread.sleep(2000);
            return "Hello from Callable";
        });

        try {
            while(!future.isDone()) {
                System.out.println("Task is still not done...");
                Thread.sleep(200);
            }

            System.out.println("Task completed! Retrieving the result");
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();
    }

    public static void futureDancel() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            // Perform some computation
            Thread.sleep(2000);
            return "Hello from Callable";
        };

        long startTime = System.nanoTime();
        Future<String> future = executorService.submit(callable);

        while(!future.isDone()) {
            try {
                System.out.println("Task is still not done...");
                Thread.sleep(200);
                double elapsedTimeInSec = (System.nanoTime() - startTime) / 1000000000.0;

                if (elapsedTimeInSec > 1) {
                    // cancel future if the elapsed time is more than one second
                    future.cancel(true);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Check if future is cancelled before retrieving the result
        if(!future.isCancelled()) {
            System.out.println("Task completed! Retrieving the result");
            // Future.get() blocks until the result is available
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Task was cancelled");
        }

        executorService.shutdown();
    }


    public static void futureAndCallable() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            // Perform some computation
            System.out.println("Entered Callable");
            Thread.sleep(2000);
            return "Hello from Callable";
        };

        System.out.println("Submitting Callable");
        Future<String> future = executorService.submit(callable);
        // This line executes immediately
        System.out.println("Do something else while callable is getting executed");

        System.out.println("Retrieve the result of the future");
        // Future.get() blocks until the result is available
        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();
    }
}



