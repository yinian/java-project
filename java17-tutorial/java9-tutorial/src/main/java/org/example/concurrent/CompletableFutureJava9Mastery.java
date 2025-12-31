package org.example.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureJava9Mastery {


    public static void main(String[] args) {

        CompletableFutureJava9Mastery service = new CompletableFutureJava9Mastery();
        System.out.println("--- [2025 异步流水线启动] ---");
        // 调用支付处理
        CompletableFuture<String> finalResult = processPaymentWithResilience("txn_12345");
        // 阻塞主线程仅为查看演示结果
        try {
            System.out.println("最终状态: " + finalResult.get());
        } catch (Exception e) {
            System.err.println("流程异常中止: " + e.getCause());
        }

    }

    /**
     * 模拟一个具有弹性（Resilience）的支付流程
     */
    public static  CompletableFuture<String> processPaymentWithResilience(String txId) {
        // 1. 获取一个内部任务
        CompletableFuture<String> internalTask = startRemotePayment(txId,2);
        // 2. 【Java 9: copy()】
        // 创建副本返回给外部调用者，防止外部代码通过 complete() 篡改内部支付逻辑的状态
        CompletableFuture<String> protectedTask = internalTask.copy();
        // 3. 【Java 9: completeOnTimeout()】
        // 如果 5 秒内整个重试链条都没完成，则返回“处理中”状态，实现优雅降级而非报错
        return protectedTask.completeOnTimeout("PENDING(系统繁忙，请稍后查询结果)", 5, TimeUnit.SECONDS)
                // 4. 【Java 9: orTimeout()】
                // 设定硬性死线（例如 8 秒），如果由于某种极端原因超过 8 秒，直接抛出 TimeoutException
                .orTimeout(8, TimeUnit.SECONDS);

    }

    /**
     * 模拟远程调用，包含【Java 9: delayedExecutor】和【Java 9: failedFuture】
     */
    private static CompletableFuture<String> startRemotePayment(String txId, int retries) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "] 尝试支付请求: " + txId);
            // 模拟 70% 概率失败，触发重试逻辑
            if (Math.random() < 0.7) {
                throw new RuntimeException("网络连接异常");
            }
            return "SUCCESS (支付成功)";
        }).handle((res, ex) -> {
            if (ex == null) {
                return CompletableFuture.completedFuture(res);
            }
            if (retries > 0) {
                System.out.println("检测到异常，准备 2 秒后重试... 剩余次数: " + retries);
                // 5. 【Java 9: delayedExecutor()】
                // 替代 Thread.sleep 或 ScheduledExecutorService，优雅实现异步延迟重试
                Executor delayeded = CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS);
                return CompletableFuture.supplyAsync(() -> startRemotePayment(txId, retries - 1), delayeded)
                        .thenCompose(f -> f);// 展平嵌套的future
            } else {
                // 6. 【Java 9: failedFuture()】
                // 重试耗尽，快速返回一个包含原始异常的 Future 实例
                return CompletableFuture.<String>failedFuture(ex);
            }
        }).thenCompose(f -> f);
    }
}
