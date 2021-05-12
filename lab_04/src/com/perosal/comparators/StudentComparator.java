package com.perosal.comparators;

import com.perosal.Student;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {

    @Override
    public int compare(Student firstStudent, Student secondStudent) {
        float gradeA = firstStudent.getGrade();
        float gradeB = secondStudent.getGrade();

        return Float.compare(gradeB, gradeA);

    }
}
