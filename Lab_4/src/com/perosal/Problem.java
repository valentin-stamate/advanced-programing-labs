package com.perosal;

import com.perosal.comparators.StudentComparator;

import java.util.List;

public class Problem {
    private final List<Student> studentList;
    private final List<School> schoolList;

    Problem(List<Student> studentList, List<School> schoolList) {
        this.studentList = studentList;
        this.schoolList = schoolList;
    }

    public void sortStudents() {
        studentList.sort(new StudentComparator());
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void showAssignedStudents() {
        schoolList.forEach(school -> {
            System.out.println(school);
            school.showStudentList();
            System.out.println("");
        });
    }
}
