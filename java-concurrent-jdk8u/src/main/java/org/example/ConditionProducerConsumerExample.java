package org.example;

import java.sql.SQLOutput;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionProducerConsumerExample {

    private static final int MAX_SIZE = 10;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition notFull = lock.newCondition();
    private static final Condition notEmpty = lock.newCondition();
    private static int count = 0;

    // 生产者线程
    public static void produce() {
        lock.lock();

        try {
            while (count == MAX_SIZE) {
                System.out.println("Queue is full, producer is waiting...");
                notFull.await();// 队列满了，生产者等待
            }
            count++;
            System.out.println("Produce 1 item, count: " +count);
            notEmpty.signal();// 通知消费者消费商品
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }finally {
            lock.unlock();
        }
    }
    // 消费者线程
    public static void consume() {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("Queue is empty, consumer is waiting...");
                notEmpty.await();// 队列空了，消费者等待
            }
            count--;
            System.out.println("Consumed 1 item, count: " + count);
            notFull.signal();//通知生产者继续生产
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {

        Runnable producer = () -> {
            for (int i = 0; i < 20; i++) {
                produce();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable consumer = () -> {
            for (int i = 0; i < 20; i++) {
                consume();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        new Thread(producer).start();
        new Thread(consumer).start();


    }




}