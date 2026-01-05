package org.example.http;

import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class Http2ClientDemo {

    @Test
    public void testHttp2() {
        //1.创建httpclient--默认支持http/2
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        // 2.构造 HttpRequest
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://www.google.com"))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        // 3.异步发送请求(CompletableFuture)
        System.out.println("正在发送Http/2请求...");
        CompletableFuture<HttpResponse<String>> responseFuture = client
                .sendAsync(request,
                        HttpResponse.BodyHandlers.ofString());


        // 4.处理响应
        responseFuture.thenAccept(response -> {
            System.out.println("状态码：" + response.statusCode());
            System.out.println("使用的协议：" + response.version());
            System.out.println("响应正文" + response.body());

        }).exceptionally(ex -> {
            System.err.println("请求失败：" + ex.getMessage());
            return null;
        });

        // 阻塞主线程以等待异步结果-仅为了演示
        responseFuture.join();

    }
}
