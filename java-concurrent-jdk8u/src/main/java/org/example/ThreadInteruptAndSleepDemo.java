package org.example;

public class ThreadInteruptAndSleepDemo {

    public static void main(String[] args) {
        // 创建并启动一个线程来模拟长时间运行的任务
        Thread taskThread = new Thread(() -> {
            try {
                long startTime = System.nanoTime();
                for (int i = 0; i < 10; i++) {
                    if (Thread.interrupted()) {
                        System.out.println("Thread was interrupted (checked with Thread.interrupted())");
                        return; // 任务中断，提前返回
                    }

                    // 模拟任务处理
                    System.out.println("Task is running, step " + (i + 1));
                    Thread.sleep(500); // 模拟耗时操作

                    // 每隔一段时间检查中断状态
                    if (System.nanoTime() - startTime > 1000000000L) { // 1秒
                        System.out.println("Checking interruption...");
                    }
                }
            } catch (InterruptedException e) {
                // 捕获到中断异常时恢复中断状态
                System.out.println("Caught InterruptedException, restoring interrupt status");
                Thread.currentThread().interrupt();  // 恢复中断状态
            }
        });

        // 启动任务线程
        taskThread.start();

        try {
            // 主线程休眠一段时间，模拟任务执行一段时间后进行中断
            Thread.sleep(1500); // 等待1.5秒
            System.out.println("Main thread interrupting the task thread...");
            taskThread.interrupt(); // 主线程中断任务线程
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复当前线程的中断状态
        }

        try {
            // 等待任务线程完成
            taskThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }


}
