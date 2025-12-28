package org.example;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockMethodsExample {

    private final ReentrantLock lock = new ReentrantLock();
    private int counter = 0;
    public int incrementAndGet() {
        System.out.println("IsLocked: " + lock.isLocked());
        System.out.println("IsHeldByCurrentThread: " + lock.isHeldByCurrentThread());
        boolean isAcquired;
        return 0;
        
    }




}
