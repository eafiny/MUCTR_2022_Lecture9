package ru.muctr.Students;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class StudentStream {
    public static void main(String[] args){
        List<Student> firstFaculty = List.of(
                new Student("Ivanov", 67, 1),
                new Student("Petrov", 98,1),
                new Student("Efimova", 45,1),
                new Student("Baranova", 77,1),
                new Student("Rodionova", 80,2),
                new Student("Ivanova", 50,2),
                new Student("Vorobiev", 68,2),
                new Student("Krasnov", 91,2)
        );
        List<Student> secondFaculty = List.of(
                new Student("Klementieva", 59,3),
                new Student("Izmailov", 63,3),
                new Student("Kozlov", 40,3),
                new Student("Volkov", 54,3),
                new Student("Voronova", 85,4),
                new Student("Afanasiev", 97,4),
                new Student("Nikolaeva", 49,4),
                new Student("Petrenko", 70,4)
        );

//1. Вывести фамилии студентов, не аттестованных по рейтингу
        List <String> printList = firstFaculty.stream()
                  .filter(stud -> stud.reiting < 50)
                  .map(Student :: getSurname)
                  .collect(Collectors.toList());
        System.out.println(printList);

//        2. Вывести фамилии студентов двух факультетов,не аттестованных по рейтингу
        List<String> printList1 = Stream.of(firstFaculty,secondFaculty)
                .flatMap(Collection::stream)
                .filter(stud -> stud.getReiting() < 50)
                .map(Student :: getSurname)
                .sorted()
                .collect(toList());
        System.out.println(printList1);

//       3. Comparator
        List <Student> sortedList = firstFaculty.stream()
//                .sorted((o1,o2) -> o1.getSurname().compareTo(o2.getSurname()))
                .sorted(Comparator.comparing(Student::getSurname))
                .collect(Collectors.toList());
        System.out.println("Сортированный список студентов: " + sortedList);

//        4. Числовой стрим
        OptionalDouble averageReiting = firstFaculty.stream()
                    .mapToInt(Student :: getReiting)
                    .filter(x -> x > 50)
                    .average();
        System.out.println("Средний рейтинг = " + averageReiting.getAsDouble());


        long numberNonPassed = secondFaculty.stream()
                .mapToInt(Student::getReiting)
                .filter(x -> x < 50)
                .count();
        System.out.println("Не аттестовано " + numberNonPassed);


//        5. Вывести N-й экземпляр потока
        Optional<String> opt = firstFaculty.stream()
                .skip(3)
                .map(Student::getSurname)
                .findFirst();
        System.out.println("10 студент: " + opt.orElse("отсутствует"));


//       6. Метод count()
        long numberOfStudents = firstFaculty.stream()
                .count();
        System.out.println("На факультете 1 обучается " + numberOfStudents + " студентов");

//        7. Метод allMAtch(), anyMatch()
        boolean groupNumberPredicate = secondFaculty.stream()
                .anyMatch(student -> student.getGroupNumber() == 3);
        System.out.println("Студенты 2 фак-та учатся в 3 группе? " + groupNumberPredicate);


//        Метод forEach
        System.out.println("forEach operator: ");
        firstFaculty.stream()
                .limit(5)
                .map(Student::getSurname)
                .forEach(System.out::println);



//        КОЛЛЕКТОРЫ
//        8. Порождение новой коллекции
        Set<String> printSet = Stream.of(firstFaculty,secondFaculty)
                .flatMap(Collection::stream)
                .filter(stud -> stud.getReiting() < 50)
                .map(Student :: getSurname)
                .collect(toCollection(TreeSet::new));
        System.out.println(printSet);


//        9.Порождение нового значения
        Optional<Student> maxReitingStudent = firstFaculty.stream()
                .collect(maxBy(comparing(Student::getReiting)));
        System.out.println("Студент с максимальным рейтингом: " + maxReitingStudent.get().getSurname());


//        10. Разбиение данных
        Map <Boolean, List<Student>> attestedStudent = secondFaculty.stream()
                .collect(partitioningBy(stud -> stud.getReiting() > 50));

        System.out.println("Список аттестованных студентов второго факультета\n" + attestedStudent.get(true));
        //или
        System.out.println("Список аттестованных студентов второго факультета: ");
        attestedStudent.get(true).stream()
                .map(Student::getSurname)
                .forEach(System.out::println);
        System.out.println();


//        11. Группировка данных
        Map <Integer, List <Student>> groupLists = Stream.of(firstFaculty, secondFaculty)
                .flatMap(s -> s.stream())
                .collect(groupingBy(Student::getGroupNumber));
        System.out.println("Списки групп студентов" + groupLists);


//        12. Создание строки
        String StudentsGroup1 = firstFaculty.stream()
                .filter(student -> student.getGroupNumber() == 1)
                .map(Student::getSurname)
                .collect(joining(", ","Студенты группы №1: ","."));
        System.out.println(StudentsGroup1);


//        13. КОМПОЗИЦИЯ КОЛЛЕКТОРОВ
//        В каждой группе найти студента с наибольшим рейтингом
        Map <Integer, Optional<Student>> maxReitingInGroup = Stream.of(firstFaculty,secondFaculty)
                .flatMap(x -> x.stream())
                .collect(groupingBy(Student::getGroupNumber, maxBy(comparing(x -> x.getReiting() > 50))));
        System.out.println("Студенты с наибольшим рейтингом в каждой группе: ");
        System.out.println(maxReitingInGroup);


//        Найти средний рейтинг в каждой группе
        Map<Integer, Double> averageReitingInGroup = Stream.of(firstFaculty,secondFaculty)
                .flatMap(x -> x.stream())
                .collect(groupingBy(Student::getGroupNumber, averagingInt(Student::getReiting)));

        System.out.println("Средний рейтинг в каждой группе: " + averageReitingInGroup);

//      Найти группу с лучшей успеваемостью
        Optional<Map.Entry<Integer, Double>> bestGroup = averageReitingInGroup.entrySet().stream()
                .collect(maxBy(comparing(x -> x.getValue())));
        System.out.println("Лучшая успеваемость в группе: " + bestGroup.get());


//        Сколько студентов в каждой группе
        Map<Integer, Long> numberOfStudentsInGroup = Stream.of(firstFaculty,secondFaculty)
                .flatMap(x -> x.stream())
                .collect(groupingBy(Student::getGroupNumber, counting()));
        System.out.println("Число студентов в каждой группе" + numberOfStudentsInGroup);
    }
}
