package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class FineGrainedSynchronizedCounter {
    private int count = 0;

    public void increment() {
        // Synchronized Block
        synchronized (this) {
            count = count + 1;
        }
    }

    public int getCount() {
        return count;
    }
}


public class SynchronizedBlockExample {

    public static void main(String[] args)  {
        SynchronizedBlockExampleTest2();
    }

    public static void SynchronizedBlockExampleTest1(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        FineGrainedSynchronizedCounter counter = new FineGrainedSynchronizedCounter();
        executorService.submit(() -> {
            for(int i = 0; i < 1000; i++) {
                System.out.println("Thread name: " + Thread.currentThread().getName());
                counter.increment();
            }
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Final count is " + counter.getCount());
    }

    public static void SynchronizedBlockExampleTest2(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        FineGrainedSynchronizedCounter counter = new FineGrainedSynchronizedCounter();
            for(int i = 0; i < 1000; i++) {
                executorService.submit(() -> {
                        System.out.println("Thread name: " + Thread.currentThread().getName());
                        counter.increment();
                });
            }

        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Final count is " + counter.getCount());
    }
}
