package com.perosal;

import java.util.*;

public class Student {
    private final String name;
    private final Queue<School> preferences;
    private final Queue<Integer> preferencePriority;
    private final float finalGrade;

    Student(String name, float finalGrade) {
        this.name = name;
        this.finalGrade = finalGrade;
        this.preferences = new LinkedList<>();
        this.preferencePriority = new LinkedList<>();
    }

    public void addPreference(School school, int preferenceRank) {
        if (!preferences.contains(school)) {
            preferences.add(school);
            preferencePriority.add(preferenceRank);
        }
    }

    public List<School> getNextSchools() {
        if (!this.canApply()) {
            System.out.println("This student can not apply, null will be returned");
            return null;
        }

        List<School> schoolList = new ArrayList<>();

        int priorityNumber = preferencePriority.poll();
        schoolList.add(getNextSchool());

        int nextPriorityNumber = preferencePriority.peek() == null ? -1 : preferencePriority.peek();
        if (nextPriorityNumber != priorityNumber) {
            nextPriorityNumber = -1;
        }
        while (nextPriorityNumber != -1) {
            preferencePriority.poll();
            schoolList.add(getNextSchool());

            nextPriorityNumber = preferencePriority.peek() == null ? -1 : preferencePriority.peek();

            if (nextPriorityNumber != priorityNumber) {
                nextPriorityNumber = -1;
            }
        }

        return schoolList;
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

    public boolean better(Student student) {
        if (student == null) {
            return true;
        }
        return finalGrade > student.finalGrade;
    }
}
