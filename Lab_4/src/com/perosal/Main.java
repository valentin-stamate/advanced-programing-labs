package com.perosal;

import com.perosal.comparators.SchoolComparator;
import com.perosal.comparators.StudentComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        compulsory();
    }

    private static void compulsory() throws FileNotFoundException {
        File inputFile = new File("input.in");
        Scanner scanner = new Scanner(inputFile);

        int nStudents = scanner.nextInt();

        List<Student> students = new LinkedList<>();

        List<Student> studentList = new ArrayList<>();
        List<School> schoolList = new ArrayList<>();

        IntStream.range(0, nStudents)
                .forEach( i -> {
                    String studentName = scanner.next();
                    float grade = scanner.nextFloat();

                    Student student = new Student(studentName, grade);

                    students.add(student);
                    studentList.add(student);
                });

        students.sort(new StudentComparator());

        students.forEach(System.out::println);

        int nSchools = scanner.nextInt();

        SortedSet<School> schools = new TreeSet<>(new SchoolComparator());

        IntStream.range(0, nSchools)
                .forEach( i -> {
                    String schoolName = scanner.next();
                    int schoolCapacity = scanner.nextInt();

                    School school = new School(schoolName, schoolCapacity);

                    schools.add(school);
                    schoolList.add(school);
                });

        schools.forEach(System.out::println);

        System.out.println("");

        Map<Student, List<School>> studentsPreferences = new HashMap<>();

        IntStream.range(0, nStudents)
                .forEach( i -> {
                    int nPreferences = scanner.nextInt();

                    Student student = studentList.get(i);

                    IntStream.range(0, nPreferences)
                            .forEach( j -> {
                                int schoolIndex = scanner.nextInt();

                                School school = schoolList.get(schoolIndex);

                                if (!studentsPreferences.containsKey(student)) {
                                    studentsPreferences.put(student, new ArrayList<>());
                                }

                                studentsPreferences.get(student).add(school);

                            });
                });

        System.out.println("Below are the students with their preferences:");
        studentsPreferences.forEach((student, preferenceList) -> {
            System.out.println(student);
            System.out.println(preferenceList);
            System.out.println("");
        });


        System.out.println("");

        Map<School, List<Student>> schoolPreferences = new HashMap<>();

        schools.forEach(school -> {
            school.acceptList(students)
                    .forEach(student -> {
                        if (!schoolPreferences.containsKey(school)) {
                            schoolPreferences.put(school, new ArrayList<>());
                        }

                        schoolPreferences.get(school).add(student);
                    });
        });

        System.out.println("Below are the schools and the potential students:");
        schoolPreferences.forEach((school, potentialStudents) -> {
            System.out.println(school);
            System.out.println(potentialStudents);
            System.out.println("");
        });

    }

}