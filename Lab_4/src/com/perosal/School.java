package com.perosal;

import java.util.ArrayList;
import java.util.List;

public class School {
    private final String name;
    private final int availablePlaces;
    private final float minGrade;
    private final List<Student> studentsAssigned;

    School(String name, int availablePlaces, float minGrade) {
        this.name = name;
        this.availablePlaces = availablePlaces;
        this.minGrade = minGrade;
        this.studentsAssigned = new ArrayList<>();
    }

    public void addStudent(Student student) {
        studentsAssigned.add(student);
    }

    public String getName() {
        return name;
    }

    public boolean isFull() {
        return studentsAssigned.size() >= availablePlaces;
    }

    @Override
    public String toString() {
        return name + " " + availablePlaces + " minGrade: " + minGrade;
    }

    public List<Student> acceptList(List<Student> students) {
        return students.subList(0, availablePlaces);
    }

    public boolean canAccept(Student student) {
        return student.getGrade() >= minGrade;
    }

    public void showStudentList() {
        studentsAssigned.forEach(System.out::println);
    }
}
