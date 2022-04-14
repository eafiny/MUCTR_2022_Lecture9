package ru.muctr.DifferentStreams;

import ru.muctr.Students.Student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Evgenia Skichko
 */
public class SimpleStreams {
    public static void main(String[] args) {
        //     Стрим из List
        List<Integer> list = List.of(3,8,4,6,2,3,15,1,80);
        list.stream()
                .filter(x -> x < 5)
                .map(x -> x * 10)
                .forEach(System.out::println);

        //      Стрим из массива
        Integer[] intArray = new Integer[]{4,6,2,7,6};
        List<Integer> listFromArray = Arrays.stream(intArray)
                .collect(Collectors.toList());
        System.out.println(listFromArray);

        //      Стрим из множества
        Set<String> set = new HashSet<>(List.of("Java", "Python", "Php"));
        set.stream()
                .filter(x -> x.length() > 3)
                .forEach(System.out::println);

        //      Стрим из Map
//        Map<Integer, String> map = new HashMap<>();
//        map.put(1, "Java");
//        map.put(2, "Python");
//        map.put(3, "Php");
//        map.values().stream()
//                .forEach(System.out::println);

        //       Optional<T>
//        System.out.println("Optional");
//        Optional<Integer> opt = Optional.ofNullable(null);
//        System.out.println(opt.get());

//        Операция reduce
        List<Integer> reduceList = List.of(12, 58, 3, 45, 8);
//        Optional<Integer> sum = reduceList.stream()
//                .reduce((acc, el) -> acc + el);
//
//        int sum1 = reduceList.stream()
//                .reduce(10000, (acc, el) -> acc + el);
//        System.out.println("Сумма = " + sum1);


        //        Числовой стрим
        int sumInt = reduceList.stream()
                .mapToInt(x -> x)
                .sum();
        System.out.println("Сумма числового потока: " + sumInt);
//
//
        OptionalInt maxInt = reduceList.stream()
                .mapToInt(x -> x)
                .max();
        System.out.println("Максимум числового потока: " + maxInt.getAsInt());

        Optional<Integer> maxInt1 = reduceList.stream()
                .max(Integer::compareTo);
        System.out.println("Максимум числового потока: " + maxInt.getAsInt());


        List<String> numberList = List.of("1", "2", "3", "4", "5");
        OptionalDouble averageStringList = numberList.stream()
                .mapToInt(x -> Integer.parseInt(x))
                .average();
        System.out.println("Среднее значение строчного списка: " + averageStringList.getAsDouble());


        List<String> stringList = List.of("Java", "Python", "JavaScript", "C++", "C");
        OptionalInt maxLengthString = stringList.stream()
                .mapToInt(x -> x.length())
                .max();
        System.out.println("Самая длинная строка: " + maxLengthString.getAsInt() + " символов");
    }
}
