package org.example.thread;

public class RunnableExample {

    public static void main(String[] args) {
        System.out.println("Inside : " + Thread.currentThread().getName());
        Runnable runnable = () -> System.out.println("Inside : " + Thread.currentThread().getName());
        System.out.println("Creating Thread");
        Thread thread = new Thread(runnable);
        System.out.println("Starting Thread");
        thread.start();
    }
}
