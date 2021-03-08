package com.perosal;

public class Solution {

    public static void solve(Problem problem) {
        problem.sortStudents();

        problem.getStudentList()
            .forEach( student -> {
                while (student.canApply()) {
                    School school = student.getNextSchool();

                    if (!school.isFull() && school.canAccept(student)) {
                        school.addStudent(student);
                        break;
                    }
                }
            });

        problem.showAssignedStudents();

    }

}
