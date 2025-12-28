package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Hello world!
 *
 */

class ReentrantLockMethodsCounter {
    private final ReentrantLock lock = new ReentrantLock();

    private int count = 0;

    public int incrementAndGet() {
        // Check if the lock is currently acquired by any thread
        System.out.println("IsLocked : " + lock.isLocked());

        // Check if the lock is acquired by the current thread itself.
        System.out.println("IsHeldByCurrentThread : " + lock.isHeldByCurrentThread());

        // Try to acquire the lock
        boolean isAcquired;
        try {
            isAcquired = lock.tryLock(1, TimeUnit.SECONDS);
            System.out.println("Lock Acquired : " + isAcquired + "\n");
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        if(isAcquired) {
            try {
                Thread.sleep(2000);
                count = count + 1;
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.unlock();
            }
        }
        return count;
    }
}
class ReentrantLockConditionMethodsCounter {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private int count = 0;

    public int incrementAndGet() {
        // Check if the lock is currently acquired by any thread
        System.out.println("IsLocked : " + lock.isLocked());

        // Check if the lock is acquired by the current thread itself.
        System.out.println("IsHeldByCurrentThread : " + lock.isHeldByCurrentThread());

        // Try to acquire the lock
        boolean isAcquired;
        try {
            isAcquired = lock.tryLock(1, TimeUnit.SECONDS);
            System.out.println("Lock Acquired : " + isAcquired + "\n");

        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        if(isAcquired) {
            try {
                Thread.sleep(2000);//模拟任务执行
                count = count + 1;
                condition.signal();// 唤醒其他等待的线程（如果有）
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.unlock();
            }
        }
        return count;
    }

    // 等待计数器的值为某个特定值
    public void waitUntilCount(int targetCount) {
        lock.lock();//获取锁

        try {
            while (count < targetCount) {
                condition.await();
            }
            System.out.println("Count has reached the target: " + count);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
}
public class ReentrantLockMethodsExample
{
    public static void main(String[] args) {
        ReentrantLockConditionMethodsCounter();
    }

    private static void ReentrantLockMethodsCounterMethod() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        ReentrantLockMethodsCounter lockMethodsCounter = new ReentrantLockMethodsCounter();

        executorService.submit(() -> {
            System.out.println("IncrementCount (First Thread) : " +
                    lockMethodsCounter.incrementAndGet() + "\n");
        });

        executorService.submit(() -> {
            System.out.println("IncrementCount (Second Thread) : " +
                    lockMethodsCounter.incrementAndGet() + "\n");
        });

        executorService.shutdown();
    }

    private static void ReentrantLockConditionMethodsCounter() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        ReentrantLockConditionMethodsCounter lockMethodsCounter = new ReentrantLockConditionMethodsCounter();

        // 创建第一个任务：递增计数
        executorService.submit(() -> {
            System.out.println("IncrementCount (First Thread) : " +
                    lockMethodsCounter.incrementAndGet() + "\n");
        });

        // 创建第二个任务：递增计数
        executorService.submit(() -> {
            System.out.println("IncrementCount (Second Thread) : " +
                    lockMethodsCounter.incrementAndGet() + "\n");
        });

        // 创建一个任务来等待 count 达到 2
        executorService.submit(() -> {
            lockMethodsCounter.waitUntilCount(2);  // 等待 count 达到 2
        });

        executorService.shutdown();
    }


}
