package org.example;

public class VolatileDemo {

    // 使用 volatile，确保可见性和防止指令重排序
    private static volatile VolatileDemo instance;

    // 私有构造函数，防止外部直接创建
    private VolatileDemo() {
    }

    public static VolatileDemo getInstance() {
        // 第一次检查（不加锁）
        if (instance == null) {
            // 只在 instance == null 时才进入同步块
            synchronized (VolatileDemo.class) {
                // 第二次检查（进入锁后再检查）
                if (instance == null) {
                    instance = new VolatileDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        // 创建多个线程来测试单例模式
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                VolatileDemo singleton = VolatileDemo.getInstance();
                System.out.println(Thread.currentThread().getName() + " -> " + singleton);
            }).start();
        }
    }
}
