package com.perosal;

import java.util.LinkedList;
import java.util.Queue;

public class Student {
    private final String name;
    private final Queue<School> preferences;
    private final float finalGrade;

    Student(String name, float finalGrade) {
        this.name = name;
        this.finalGrade = finalGrade;
        this.preferences = new LinkedList<>();
    }

    public void addPreference(School school) {
        if (!preferences.contains(school)) {
            preferences.add(school);
        }
    }

    public float getGrade() {
        return finalGrade;
    }

    @Override
    public String toString() {
        return name + " " + finalGrade;
    }

    public School getNextSchool() {
        return preferences.poll();
    }

    public boolean canApply() {
        return preferences.size() != 0;
    }
}
