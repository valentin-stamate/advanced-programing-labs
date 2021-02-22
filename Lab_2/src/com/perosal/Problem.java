package com.perosal;

import java.util.ArrayList;

/** This class solved the problem */
public class Problem {
    private final ArrayList<Source> sources;
    private int totalCost;
    private boolean checkDuplicates;

    Problem() {
        sources = new ArrayList<>();
        totalCost = 0;
        checkDuplicates = true;
    }

    /** Adds a unique source to ArrayList */
    public void addSource(Source s) {
        if (checkDuplicates) {
            if (sources.contains(s)) {
                return;
            }
        }

        sources.add(s);
    }

    /** Loops through all the sources am for every source
     * push all it's capacity to the destinations that have
     * the smallest transportation cost */
    public void solve() {
        for (Source s : sources) {
            boolean finished = s.pushCommodities();
            totalCost += s.getTransportCost();

            if (finished) {
                break;
            }

        }

    }

    public int getTotalCost() {
        return totalCost;
    }

    /** For bigger inputs it's better to disable this*/
    public void checkDuplicates(boolean b) {
        checkDuplicates = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Source sr = sources.get(0);
        sb.append("        ");
        sb.append(sr.showDestinationNames());

        for (Source s : sources) {
            sb.append(s.name);
            sb.append("      ");
            sb.append(s.showDestinationsCost());
            sb.append(s.supply);
            sb.append("\n");
        }
        sb.append("        ");
        sb.append(sr.showDestinationsCapacity());
        sb.append("\n");
        return sb.toString();
    }


}


