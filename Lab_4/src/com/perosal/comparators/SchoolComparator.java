package com.perosal.comparators;

import com.perosal.School;

import java.util.Comparator;

public class SchoolComparator implements Comparator<School> {

    @Override
    public int compare(School schoolA, School schoolB) {
        return schoolA.getName().compareTo(schoolB.getName());
    }
}
