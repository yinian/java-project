package org.example.lamda;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summingDouble;

public class StreamExamples {

    public static void main(String[] args) {
        convertListToOther();
        filterList();
        findElement();
        findMaxMin();
        sumOfElements();
        flatMapList();
        sortedList();
        matchList();
        skipList();
        joiningList();
        groupingbyListViaJDK8u();
        groupingbyListAmountViaJDK8u();
        groupingbyListCountViaJDK8u();
        groupingbyListViaJDK9();
    }

    public static void sumOfElements() {
        int[] intArray = {23,45,76,12,67,89};
        Arrays.stream(intArray).reduce(Integer::sum)
                .ifPresent(s -> System.out.println("Sum: " + s));
    }


    public static void findMaxMin() {
        List<Integer> integers = Arrays.asList(3, 5, 45, 52, 5, 14);
        Optional<Integer> maxVal = integers.stream().max(Comparator.naturalOrder());
        Optional<Integer> minVal = integers.stream().min(Comparator.naturalOrder());
        System.out.println("Max: " + maxVal.get());
        System.out.println("Min: " + minVal.get());
    }

        public static void findElement() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Bytes", 18));
        persons.add(new Person("Tree", 20));
        persons.add(new Person("Streams", 22));

        Optional<Person> result = persons.stream().filter(p -> p.getName().equals("Bytes")).findAny();
        if (result.isPresent()) {
            System.out.println(result.get());
        } else {
            System.out.println("Not Found");
        }
    }

    //Convert List of objects to another List of objects
    public static void convertListToOther() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Bytes", 18));
        persons.add(new Person("Tree", 20));
        persons.add(new Person("Streams", 22));

        List<String> stringList = persons.stream().map(p -> p.getName()).collect(Collectors.toList());
        System.out.println(stringList);


    }

    //Create new list by filtering elements
    public static void filterList() {
        List<Integer> integerList = Stream.iterate(1, n -> n + 1).limit(10).collect(Collectors.toList());
        List<Integer> collected = integerList.stream().filter(n -> (n.intValue() % 2 == 0)).collect(Collectors.toList());
        System.out.println(collected);
    }

    //Create new list by filtering elements
    public static void flatMapList() {
        // 每个学生自带一个课程列表
        Student s1 = new Student("张三", Arrays.asList("Java", "Python"));
        Student s2 = new Student("李四", Arrays.asList("C++", "Go", "Java"));
        Student s3 = new Student("王五", Arrays.asList("SQL"));

        List<Student> classroom = Arrays.asList(s1, s2, s3);
        List<String> courseNameList = classroom.stream().flatMap(s -> s.getCourseList().stream()).distinct().collect(Collectors.toList());
        System.out.println(courseNameList);
        courseNameList.forEach(System.out::print);

    }

    //Create new list by filtering elements
    public static void sortedList() {

        List<String> names = Arrays.asList("Java", "Python", "C++");
        names.stream()
                .sorted()
                .forEach(System.out::println);

        Person p1 = new Person("Java", 23);
        Person p2 = new Person("python", 25);
        Person p3 = new Person("python", 27);

        Stream<Person> personStream = Stream.of(p1, p2, p3);
        personStream.sorted(Comparator.comparing(Person::getAge).reversed().thenComparing(Person::getName))      // 第二优先级：姓名升序
            .forEach(System.out::println);
        Person p5 = new Person("Java", 23);
        Person p4 = new Person("python", 25);
        Stream<Person> personStream1 = Stream.of(p3, p4);
        personStream1.sorted(Comparator.comparing(Person::getName,Comparator.nullsLast(String::compareTo))).forEach(System.out::println);

    }

    public static void matchList() {

        // 构造测试数据
        List<Order> orders = Arrays.asList(
                new Order("ORD_001", 500, true, "VIP",Arrays.asList(
                        new Product("MacBook", 15000, false),
                        new Product("Mouse", 200, false))),

                new Order("ORD_002",1400 , true, "NORMAL",Arrays.asList(
                        new Product("iPhone", 8000, true), // 缺货
                        new Product("Case", 100, false))),

                new Order("ORD_003", 2000, false, "NORMAL",Arrays.asList(
                        new Product("iPad", 5000, false))) // 未支付
        );
        // allmatch
        boolean allMatch = orders.stream().allMatch(order -> order.isPaid() && !order.getProductList().isEmpty());
        System.out.println("1. 是否所有订单都已完成支付且非空？ " + allMatch);
        // 结果：false (因为 ORD_003 未支付)
        // --- 2. anyMatch: 判定“存在风险”（嵌套流操作） ---
        // 逻辑：这批订单中，是否【存在任何一个】订单包含【缺货商品】？
        boolean anyMatch = orders.stream().anyMatch(order -> order.getProductList().stream().anyMatch(Product::isOutofStock));
        System.out.println("2. 这批订单中是否存在缺货风险？ " + anyMatch);
        // 结果：true (因为 ORD_002 里有缺货商品)
        // --- 3. noneMatch: 判定“完全合规” ---
        // 逻辑：确保这批订单中【没有任何一个】订单被标记为 "RISK" 且金额超 10万。
        boolean noneMatch = orders.stream().noneMatch(order -> "RISK".equals(order.getStatus()) && order.getProductList().stream().mapToDouble(Product::getPrice).sum() > 1000000);
        System.out.println("3. 是否通过了高额风险订单校验？ " + noneMatch);
        // 结果：true (当前没有订单是 RISK 状态)


    }

    public static void skipList() {

        List<String> names = Arrays.asList("张三", "李四", "王五", "赵六", "孙七");

        names.stream()
                .skip(3) // 跳过前 3 个（张三, 李四, 王五）
                .forEach(System.out::println);

    }

    public static void joiningList() {
        Person p1 = new Person("Java", 23);
        Person p2 = new Person("python", 25);
        Person p3 = new Person("python", 27);

        Stream<Person> persons = Stream.of(p1, p2, p3);
        String names = persons.map(Person::getName)
                .collect(Collectors.joining(", ", "[", "]")); // 输出 [Bytes, Tree, Streams]
        System.out.println(names);


    }
    // 统计缺货下的商品名
    public static void groupingbyListViaJDK8u() {
        // 1. 准备测试数据
        List<Order> orders = Arrays.asList(
                new Order("ORD_001", 15000.0, true, "NORMAL", Arrays.asList(
                        new Product("MacBook", 14000.0, false),
                        new Product("Mouse", 1000.0, true) // 缺货
                )),
                new Order("ORD_002", 500.0, true, "NORMAL", Arrays.asList(
                        new Product("Keyboard", 500.0, true) // 缺货
                )),
                new Order("ORD_003", 8000.0, false, "VIP", Arrays.asList(
                        new Product("iPhone", 8000.0, false)
                )),
                new Order("ORD_004", 3000.0, true, "VIP", Arrays.asList(
                        new Product("iPad", 3000.0, true) // 缺货
                ))
        );
        // 2. 复杂的 groupingBy 组合操作
        // 目标：Map<状态, 缺货商品名称列表>
        // 2. 使用 Java 1.8 的流操作
        // 思路：因为 1.8 的 groupingBy 无法直接在下游拍平，
        // 我们先构造一个中转对象，或者直接利用 AbstractMap.SimpleEntry
        Map<String, List<String>> result = orders.stream()
                // 第一步：用 flatMap 把 Order 转换成 "状态+缺货商品名" 的中间流
                .flatMap(order -> order.getProductList().stream()
                        .filter(Product::isOutofStock) // 只看缺货的
                        .map(product -> new AbstractMap.SimpleEntry<>(order.getStatus(), product.getName()))
                )
                // 第二步：此时流里是一个个 Entry<状态, 商品名>
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,               // 按状态(Key)分组
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList()) // 提取商品名(Value)组成列表
                ));

        // 3. 打印结果
        result.forEach((status, products) -> {
            System.out.println("状态 [" + status + "] 下的缺货商品: " + products);
        });

    }
    //统计每个状态下的订单总金额
    public static void groupingbyListAmountViaJDK8u() {

        // 1. 准备测试数据
        List<Order> orders = Arrays.asList(
                new Order("ORD_001", 15000.0, true, "NORMAL", Arrays.asList(
                        new Product("MacBook", 14000.0, false),
                        new Product("Mouse", 1000.0, true) // 缺货
                )),
                new Order("ORD_002", 500.0, true, "NORMAL", Arrays.asList(
                        new Product("Keyboard", 500.0, true) // 缺货
                )),
                new Order("ORD_003", 8000.0, false, "VIP", Arrays.asList(
                        new Product("iPhone", 8000.0, false)
                )),
                new Order("ORD_004", 3000.0, true, "VIP", Arrays.asList(
                        new Product("iPad", 3000.0, true) // 缺货
                ))
        );

        // 2. 复杂的 groupingBy 组合操作
        // 目标：Map<状态, 缺货商品名称列表>
        // 2. 使用 Java 1.8 的流操作
        // 思路：因为 1.8 的 groupingBy 无法直接在下游拍平，
        // 我们先构造一个中转对象，或者直接利用 AbstractMap.SimpleEntry
        Map<String, Double> result = orders.stream()
                // 第一步：用 flatMap 把 Order 转换成 "状态+缺货商品名" 的中间流
                .flatMap(order -> order.getProductList().stream().map(product -> new AbstractMap.SimpleEntry<>(order.getStatus(), product.getPrice()))
                )
                // 第二步：此时流里是一个个 Entry<状态, 商品名>
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,               // 按状态(Key)分组
//                        Collectors.mapping(Map.Entry::getValue, Collectors.toList()) // 提取商品名(Value)组成列表
                        summingDouble(Map.Entry::getValue)
                ));

        // 3. 打印结果
        result.forEach((status, products) -> {
            System.out.println("状态 [" + status + "] 下的商品价值总额: " + products);
        });

    }
    // 统计每个状态下有多少个订单
    public static void groupingbyListCountViaJDK8u() {

        // 1. 准备测试数据
        List<Order> orders = Arrays.asList(
                new Order("ORD_001", 15000.0, true, "NORMAL", Arrays.asList(
                        new Product("MacBook", 14000.0, false),
                        new Product("Mouse", 1000.0, true) // 缺货
                )),
                new Order("ORD_002", 500.0, true, "NORMAL", Arrays.asList(
                        new Product("Keyboard", 500.0, true) // 缺货
                )),
                new Order("ORD_003", 8000.0, false, "VIP", Arrays.asList(
                        new Product("iPhone", 8000.0, false)
                )),
                new Order("ORD_004", 3000.0, true, "VIP", Arrays.asList(
                        new Product("iPad", 3000.0, true) // 缺货
                ))
        );

        // 2. 复杂的 groupingBy 组合操作
        // 目标：Map<状态, 缺货商品名称列表>
        // 2. 使用 Java 1.8 的流操作
        // 思路：因为 1.8 的 groupingBy 无法直接在下游拍平，
        // 我们先构造一个中转对象，或者直接利用 AbstractMap.SimpleEntry
        Map<String, Long> result = orders.stream()
                // 第一步：用 flatMap 把 Order 转换成 "状态+缺货商品名" 的中间流
                .flatMap(order -> order.getProductList().stream().map(product -> new AbstractMap.SimpleEntry<>(order.getStatus(), product.getName()))
                )
                // 第二步：此时流里是一个个 Entry<状态, 商品名>
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,               // 按状态(Key)分组
//                        Collectors.mapping(Map.Entry::getValue, Collectors.toList()) // 提取商品名(Value)组成列表
                        counting()
                ));

        // 3. 打印结果
        result.forEach((status, products) -> {
            System.out.println("状态 [" + status + "] 下的商品数量为: " + products);
        });

    }

    // 用jdk 9 重新写一下：
    // 统计缺货下的商品名
    public static void groupingbyListViaJDK9() {
        // 1. 准备测试数据
        List<Order> orders = Arrays.asList(
                new Order("ORD_001", 15000.0, true, "NORMAL", Arrays.asList(
                        new Product("MacBook", 14000.0, false),
                        new Product("Mouse", 1000.0, true) // 缺货
                )),
                new Order("ORD_002", 500.0, true, "NORMAL", Arrays.asList(
                        new Product("Keyboard", 500.0, true) // 缺货
                )),
                new Order("ORD_003", 8000.0, false, "VIP", Arrays.asList(
                        new Product("iPhone", 8000.0, false)
                )),
                new Order("ORD_004", 3000.0, true, "VIP", Arrays.asList(
                        new Product("iPad", 3000.0, true) // 缺货
                ))
        );
        // 2. 复杂的 groupingBy 组合操作
        // 目标：Map<状态, 缺货商品名称列表>
        // 2. 使用 Java 1.8 的流操作
        // 思路：因为 1.8 的 groupingBy 无法直接在下游拍平，
        // 我们先构造一个中转对象，或者直接利用 AbstractMap.SimpleEntry
        Map<String, List<String>> result = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getStatus,
                        Collectors.flatMapping(
                                order -> order.getProductList().stream()
                                        .filter(Product::isOutofStock)
                                        .map(Product::getName),
                                Collectors.toList()
                        )
                ));

        // 3. 打印结果
        result.forEach((status, products) -> {
            System.out.println("状态 [" + status + "] 下的缺货商品: " + products);
        });

    }




}
