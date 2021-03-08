package com.perosal.comparators;

import com.perosal.Student;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {

    @Override
    public int compare(Student firstStudent, Student secondStudent) {
        return (int)Math.floor(secondStudent.getGrade() - firstStudent.getGrade());
    }
}
