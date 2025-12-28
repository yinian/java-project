package org.example.lamda;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamCreation {

    public static void main(String[] args) {
        streamOfCollection();
        streamOfSet();
        steamOfValues();
        streamOfArray();
        streamBuilder();
        streamIterate();
        lazyOperations();
    }

    private static void lazyOperations() {

        Stream<String> stream = Stream.of("one", "two", "three").map(s -> s.toUpperCase()).peek(System.out::println);
        System.out.println(stream.count());


    }



    private static void streamOfCollection() {

        HashSet<String> stringList = new HashSet<>(Arrays.asList("One", "Two", "Three"));
        Stream<String> streamed = stringList.stream();
        System.out.println("Stream of collection:");
        streamed.forEach(System.out::println);

    }
    private static void streamOfSet() {
        List<String> stringColle = Arrays.asList("One", "Two", "Three");
        Stream<String> streamed = stringColle.stream();
        System.out.println("Stream of collection:");
        streamed.forEach(System.out::println);

    }

    private static void steamOfValues() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        System.out.println("Stream of Values:");
        integerStream.forEach(System.out::println);
    }

    private static void streamOfArray() {

        System.out.println("Stream from arrays:");
        Person[] personArray = {new Person("bytes",18),new Person("tree",20)};
        Stream<Person> stream = Arrays.stream(personArray);
        stream.forEach(person -> System.out.println("person name"+person.getName()));

        System.out.println("\nPremitive Stream From Arrays:");
        int[] intArray = {1, 2, 3};
        IntStream intStream = Arrays.stream(intArray);
        intStream.forEach(System.out::println);
    }

    private static void streamBuilder() {
        Stream.Builder<String> objectBuilder = Stream.builder();
        Stream<String> streamFromBuilder = objectBuilder.add("One").add("Two").add("three").build();
        streamFromBuilder.forEach(System.out::println);
    }

    private static void streamIterate() {
        Stream<Integer> streamFromIterate = Stream.iterate(0, n -> n + 2).limit(10);
        streamFromIterate.forEach(System.out::println);
    }







}
