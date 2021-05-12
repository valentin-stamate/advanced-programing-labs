package com.perosal;

import java.util.*;

public class Solution {

    public static void solve(Problem problem) {
        problem.sortStudents();

        problem.getStudentList()
            .forEach( student -> {
                while (student.canApply()) {
                    School school = student.getNextSchool();

                    if (school.canAccept(student)) {
                        school.addStudent(student);
                        break;
                    }
                }
            });

        problem.showAssignedStudents();
    }

    public static void solveWithGaleShapley(Problem problem) {

        Map<School, List<Student>> schoolListMap = new HashMap<>();
        Map<Student, School> studentSchoolMap = new HashMap<>();

        List<Student> studentList = problem.getStudentList();
        List<School> schoolList = problem.getSchoolList();

        schoolList.forEach(school -> {
            schoolListMap.put(school, new ArrayList<>());
        });

        Queue<Student> freeStudents = new LinkedList<>(studentList);

        while(!freeStudents.isEmpty()) {
            Student student = freeStudents.poll();

            if (student.canApply()) {
                List<School> schoolsToPropose = student.getNextSchools();

                for (School schoolToPropose : schoolsToPropose) {
                    if (schoolToPropose.canAccept(student)) {
                        if (!schoolToPropose.isFull()) {
                            schoolListMap.get(schoolToPropose).add(student);
                            schoolToPropose.addStudent(student);
                            break;
                        } else if (schoolListMap.get(schoolToPropose).size() != 0) {

                            Student worstStudent = schoolToPropose.getWorstStudent();

                            if (student.better(worstStudent)) {
                                schoolListMap.get(schoolToPropose).remove(worstStudent);
                                schoolToPropose.removeStudent(worstStudent);

                                freeStudents.add(worstStudent);

                                schoolListMap.get(schoolToPropose).add(student);
                                schoolToPropose.addStudent(student);
                                break;
                            }
                        }
                    }
                }
            }
        }

        schoolListMap.forEach((school, studentsAssigned) -> {
            System.out.println(school);
            studentsAssigned.forEach(System.out::println);
            System.out.println("");
        });

    }
}
