package org.example;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hello world!
 *
 */

class AtomicCount {

    private AtomicInteger count = new AtomicInteger(0);

    public void incrementAndGet() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
public class AtomicCountExample
{

    public static void main( String[] args ) throws InterruptedException {
        runExecutorServiceShutDownNow();
    }

    public static void runExecutorServiceShutDown() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        AtomicCount atomicCount = new AtomicCount();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(()->atomicCount.incrementAndGet());

        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Final Count is : " + atomicCount.getCount());
    }

    public static void runExecutorServiceShutDownNow() {
        ExecutorService executor = getExecutor();
        AtomicCount atomicCount = new AtomicCount();
        try {
            for (int i = 0; i < 1000; i++) {
                executor.submit(()->atomicCount.incrementAndGet());
            }
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5000, TimeUnit.SECONDS)) {
                    List<Runnable> runnables = executor.shutdownNow();
                    handleUnFinishedTasks(runnables);
                }else{
                    System.out.println("Final Count is : " + atomicCount.getCount());
                    System.out.println("the executor has finished!");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                executor.shutdownNow();
            }
        }


    }

    public static void handleUnFinishedTasks(List<Runnable> runnables) {
        ExecutorService executor = getExecutor();

        if (!runnables.isEmpty()) {
            System.out.println("未完成任务数：" + runnables.size());
            for (int i = 0; i < runnables.size(); i++) {
                executor.submit(runnables.get(i));
            }
        }



    }

    public static ThreadPoolExecutor getExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(processors * 2,
                processors * 4,
                100000,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(processors * 100),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolExecutor;
    }



}
