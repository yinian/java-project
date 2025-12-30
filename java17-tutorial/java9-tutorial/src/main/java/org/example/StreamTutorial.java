package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTutorial {

    public static void main(String[] args) {

        streamWhile();
        streamIterate();
        optionalDemo();



    }



    private static void optionalDemo() {
        System.out.println("--- 场景 1: 能够找到用户 ---");
        Optional<String> user1 = getUsername(1);
        user1.ifPresentOrElse(
                name -> System.out.println("用户名为: " + name),
                () -> System.out.println("未找到用户")
        );

        System.out.println("\n--- 场景 2: 找不到用户 ---");
        Optional<String> user2 = getUsername(999); // 模拟不存在的 ID
        user2.ifPresentOrElse(
                name -> System.out.println("用户名为: " + name),
                () -> System.out.println("未找到用户")
        );

        System.out.println("--- 尝试获取 ID 为 1 的用户 ---");
        findUserInCache(1).or(()->findUserInDb(1))
                .ifPresentOrElse(
                        user3-> System.out.println("最终获取到用户:"+user3),
                        ()-> System.out.println("缓存和数据库中均查无此用户")
                );
        // 场景 2：缓存没有，但数据库有
        System.out.println("\n--- 尝试获取 ID 为 2 的用户 ---");
        findUserInCache(2)
                .or(()->findUserInDb(2))
                .ifPresentOrElse(
                        user -> System.out.println("最终获取到用户: " + user),
                        () -> System.out.println("缓存和数据库中均无此用户")
                );

        // 场景 3：两处都没有
        System.out.println("\n--- 尝试获取 ID 为 3 的用户 ---");
        findUserInCache(3)
                .or(() -> findUserInDb(3))
                .ifPresentOrElse(
                        user -> System.out.println("最终获取到用户: " + user),
                        () -> System.out.println("缓存和数据库中均无此用户")
                );


        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        List<String> activeUsers = integerList.stream().map(StreamTutorial::findUserById)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        System.out.println("查找到的用户列表: " + activeUsers);

        List<Order> orders = Arrays.asList(
                new Order("A1", Optional.of("DISCOUNT_10")),
                new Order("A2", Optional.empty()),
                new Order("A3", Optional.of("WELCOME_20"))
        );

        List<String> disCountList = orders.stream().map(Order::getDiscount)
                .flatMap(Optional::stream)// 过滤掉空的
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("所有有效折扣码: " + disCountList);

        Order order1 = new Order("A1", Optional.of("DISCOUNT_10"));


        System.out.println("用户1城市: " + getOrder(order1)); // 输出: 北京




    }

    private static String getOrder(Order order) {
        return Optional.ofNullable(order)
                .map(Order::getName)
                .orElse("未知城市");
    }

    private static Optional<String> findUserById(int id) {
        // 模拟只返回奇数 ID 的用户
        return (id % 2 != 0) ? Optional.of("用户-" + id) : Optional.empty();
    }





    public static void streamWhile() {
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

    public static void streamIterate() {
        Stream.iterate(new int[]{0, 1},
                        arr -> arr[0] <= 55, // 当第一个数超过55时停止
                        arr -> new int[]{arr[1], arr[0] + arr[1]})
                .map(arr -> arr[0])
                .forEach(System.out::println);

    }


    /**
     * 模拟数据库查询
     */
    public static Optional<String> getUsername(int id) {
        if (id == 1) {
            return Optional.of("张三");
        } else {
            return Optional.empty();
        }
    }

    /**
     * 模拟从 Redis 缓存查找
     */
    public static Optional<String> findUserInCache(int id) {
        if (id == 1) {
            System.out.println("[Cache] 命中缓存！");
            return Optional.of("张三(来自缓存)");
        }
        System.out.println("[Cache] 缓存未命中...");
        return Optional.empty();
    }

    /**
     * 模拟从 MySQL 数据库查找
     */
    public static Optional<String> findUserInDb(int id) {
        if (id == 2) {
            System.out.println("[DB] 数据库查询成功！");
            return Optional.of("李四(来自数据库)");
        }
        System.out.println("[DB] 数据库也找不到该用户。");
        return Optional.empty();
    }


}

