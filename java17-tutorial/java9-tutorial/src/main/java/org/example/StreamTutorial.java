package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTutorial {

    public static void main(String[] args) {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 0, 5);
        List<Integer> takeWhileList = integerStream.takeWhile(n -> n < 3).collect(Collectors.toList());
        System.out.println(takeWhileList);
        Stream<Integer> integStream = Stream.of(1, 2, 3, 4, 0, 5);
        List<Integer> dropWhileList = integStream.dropWhile(n -> n < 3).collect(Collectors.toList());
        System.out.println(dropWhileList);

        Map<String, List<String>> userRolesMap = new HashMap<>();
        userRolesMap.put("admin", Arrays.asList("READ", "WRITE", "DELETE"));
        userRolesMap.put("guest", null); // 测试 null 情况

        // 1. 处理存在的 Key
        System.out.println("--- Admin Roles ---");
        Stream.ofNullable(userRolesMap.get("admin"))
                .flatMap(Collection::stream) // 正确：指向具体集合实例的 stream() 方法
                .forEach(System.out::println);

        // 2. 处理不存在或为 null 的 Key
        System.out.println("--- Guest Roles ---");
        Stream.ofNullable(userRolesMap.get("guest"))
                .flatMap(Collection::stream) // 安全跳过，不会报错
                .forEach(System.out::println);


    }
}
