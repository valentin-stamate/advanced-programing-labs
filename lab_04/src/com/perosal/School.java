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
        return student.getGrade() >= minGrade && !isFull();
    }

    public void showStudentList() {
        studentsAssigned.forEach(System.out::println);
    }

    public void removeStudent(Student student) {
        studentsAssigned.remove(student);
    }

    public Student getWorstStudent() {
        if (studentsAssigned.size() == 0) {
            return null;
        }

        Student worstStudent = studentsAssigned.get(0);

        for (Student student : studentsAssigned) {
            if (worstStudent.better(student)) {
                worstStudent = student;
            }
        }

        return worstStudent;
    }
}
