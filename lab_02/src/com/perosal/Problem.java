package com.perosal;

import java.util.ArrayList;
import java.util.List;

/** This class solved the problem */
public class Problem {
    private final List<Source> sources;
    private int totalCost;
    private boolean checkDuplicates;

    Problem() {
        sources = new ArrayList<>();
        totalCost = 0;
        checkDuplicates = true;

    }

    /** Adds a unique source to ArrayList */
    public void addSource(Source newSource) {
        if (checkDuplicates) {
            for (Source source : sources) {
                if (newSource.equals(source)) {
                    return;
                }
            }
        }

        sources.add(newSource);
    }

    /** Loops through all the sources am for every source
     * push all it's capacity to the destinations that have
     * the smallest transportation cost */
    public void solve() {
        for (Source source : sources) {
            boolean finished = source.pushCommodities();
            totalCost += source.getTransportCost();

            if (finished) {
                break;
            }

        }

    }

    /** Getter for totalCost */
    public int getTotalCost() {
        return totalCost;
    }

    /** For bigger inputs it's better to disable this*/
    public void checkDuplicates(boolean checkDuplicates) {
        this.checkDuplicates = checkDuplicates;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        Source sr = sources.get(0);
        stringBuilder.append("        ");
        stringBuilder.append(sr.showDestinationNames());

        for (Source s : sources) {
            stringBuilder.append(s.getName());
            stringBuilder.append("      ");
            stringBuilder.append(s.showDestinationsCost());
            stringBuilder.append(s.getSupply());
            stringBuilder.append("\n");
        }
        stringBuilder.append("        ");
        stringBuilder.append(sr.showDestinationsCapacity());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}


