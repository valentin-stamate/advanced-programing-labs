package com.perosal;

import java.util.ArrayList;
import java.util.List;

public class School {
    private final String name;
    private final int availablePlaces;

    School(String name, int availablePlaces) {
        this.name = name;
        this.availablePlaces = availablePlaces;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " " + availablePlaces;
    }

    public List<Student> acceptList(List<Student> students) {
        return students.subList(0, availablePlaces);
    }
}
