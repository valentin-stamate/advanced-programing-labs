package com.perosal;

import com.github.javafaker.Faker;
import com.perosal.comparators.SchoolComparator;
import com.perosal.comparators.StudentComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        compulsory();
        optional();
        bonus();
    }

    private static void bonus() {
        System.out.println("----==== Bonus ====----");
        int nStudents = 45;
        int nSchools = 6;

        List<Student> studentList = generateRandomStudentList(nStudents);
        List<School> schoolList = generateRandomSchoolList(nSchools);
        assignRandomSchoolsToStudents(studentList, schoolList);

        Problem problem = new Problem(studentList, schoolList);
        Solution.solveWithGaleShapley(problem);
    }

    private static void optional() {
        System.out.println("");

        int nStudents = 40;
        int nSchools = 5;

        List<Student> studentList = generateRandomStudentList(nStudents);
        List<School> schoolList = generateRandomSchoolList(nSchools);
        assignRandomSchoolsToStudents(studentList, schoolList);

        Problem problem = new Problem(studentList, schoolList);
        Solution.solve(problem);

    }

    private static void assignRandomSchoolsToStudents(List<Student> studentList, List<School> schoolList) {
        int nSchools = schoolList.size();

        studentList.forEach(student -> {
            int lastPreferenceRank = 1;

            for (int i = 1; i <= nSchools; i++) {

                student.addPreference(schoolList.get((int)(generateNumber(0, nSchools - 0.1))), lastPreferenceRank);

                double randomRaw = generateNumber(0, 13);
                int random = (int)(randomRaw) % 3 == 0 ? 0 : 1;
                lastPreferenceRank += random;
            }
        });
    }

    private static List<School> generateRandomSchoolList(int nSchools) {
        List<School> schoolList = new ArrayList<>();
        Faker faker = new Faker();

        IntStream.range(0, nSchools)
                .forEach(i -> {
                    School school = new School("School " + faker.name().fullName(), 7, (float) generateNumber(7.5, 8.5));
                    schoolList.add(school);
                });

        return schoolList;
    }

    private static List<Student> generateRandomStudentList(int nStudents) {
        List<Student> studentList = new ArrayList<>();
        Faker faker = new Faker();

        IntStream.range(0, nStudents)
                .forEach( i -> {
                    Student student = new Student(faker.name().fullName(), (float) generateNumber(7.8, 10));
                    studentList.add(student);
                });

        return  studentList;
    }

    private static double generateNumber(double start, double end) {
        double difference = Math.abs(end - start);

        double random = Math.random();
        random *= 1000;
        random = random - (int)(random / difference) * difference;

        start += random;

        return ((double)(int)(start * 100)) / 100;
    }

    private static void compulsory() throws FileNotFoundException {
        System.out.println("----==== Compulsory ====----");
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
                    float schoolMinGrade = scanner.nextFloat();

                    School school = new School(schoolName, schoolCapacity, schoolMinGrade);

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
                                int schoolPriority = scanner.nextInt();

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

        System.out.println("----==== Optional ====----");

        System.out.println("The school list is:");
        System.out.println(schoolList + "\n");
        System.out.println("The student list is:");
        System.out.println(studentList + "\n");

        System.out.println("The students that can enter in these schools are:");
        studentList.stream()
                .filter( student -> {
                    boolean canBeAccepted = false;

                    for(School school : schoolList) {
                        canBeAccepted = school.canAccept(student) || canBeAccepted;
                    }

                    return  canBeAccepted;
                } )
                .forEach(System.out::println);

        Student topStudent = studentList.get(3);

        System.out.println("\nThe schools that can accept student " + topStudent + " are:");
        schoolList.stream()
                .filter( school -> school.canAccept(topStudent))
                .forEach(System.out::println);

    }

}