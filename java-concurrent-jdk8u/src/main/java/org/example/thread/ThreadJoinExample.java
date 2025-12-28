package org.example.thread;

public class ThreadJoinExample {

    public static void main(String[] args) {
        Runnable task1 = () -> {
            System.out.println("Enterred Thread 1");
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Exiting Thread 1");
        };
        Runnable task2 = () -> {
            System.out.println("Enterred Thread 2");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Exiting Thread 2");
        };
        System.out.println("Starting Thread 1");
        Thread thread1 = new Thread(task1);
        thread1.start();
        System.out.println("waiting for Thread1 to complete");

        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        System.out.println("Waited enough! Starting Thread 2 now");
        Thread thread2 = new Thread(task2);
        thread2.start();

    }
}
