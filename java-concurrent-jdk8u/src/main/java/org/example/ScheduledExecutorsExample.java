package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorsExample {

    public static void main(String[] args) {

        scheduledAtFixedDelayRate();

    }


    public static void scheduledAtFixedRate() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // 每 2 秒执行一次任务，假设任务的执行时间是 3 秒
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Task started at:  " + System.currentTimeMillis());
                Thread.sleep(3000);  // 模拟一个耗时 3 秒的任务
                System.out.println("Task finished at: " + System.currentTimeMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public static void scheduledAtFixedDelayRate() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // 每 2 秒执行一次任务，假设任务的执行时间是 3 秒
        scheduler.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("Task started at:  " + System.currentTimeMillis());
                Thread.sleep(3000);  // 模拟一个耗时 3 秒的任务
                System.out.println("Task finished at: " + System.currentTimeMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

}
