package org.example.thread;

public class ThreadMethodExample {

    public static void main(String[] args) {
        Runnable task1 = () -> {
            System.out.println("Enterred Thread 1" + Thread.currentThread().getName());
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Exiting Thread 1" + Thread.currentThread().getName());
        };
        Runnable task2 = () -> {
            System.out.println("Enterred Thread 2" + Thread.currentThread().getName());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Exiting Thread 2" + Thread.currentThread().getName());
        };
        System.out.println("Starting Thread 1");
        Thread thread1 = new Thread(task1);
        thread1.setName("Thread-1");
        thread1.start();

        while (thread1.isAlive()) {
            System.out.println("Thread 1 is still running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("waiting for Thread1 to complete");

        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Waited enough! Starting Thread 2 now");
        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        Thread thread2 = new Thread(task2);
        thread2.setName("Thread-2");
        thread2.start();

        try {
            Thread.sleep(2000);
            System.out.println("Interrupting Thread 2...");
            thread2.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Main thread calling yield()");
        Thread.yield();


    }
}
