package com.perosal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/** A source instance has all the destination kept into a PriorityQueue*/
abstract public class Source {
    public final String name;
    private final SourceType type;
    public int supply;
    private boolean checkDuplicates;
    private int transportCost;

    private final PriorityQueue<ToDestination> pq;
    private final ArrayList<ToDestination> destinations; // for printing purposes

    Source(String name, SourceType sourceType, int supply) {
        this.name = name;
        this.type = sourceType;
        this.supply = supply;
        this.transportCost = 0;
        this.checkDuplicates = true;
        this.pq = new PriorityQueue<>(new DestinationComparator());
        this.destinations = new ArrayList<>();
    }

    /** Having this source, it pushed as much as possible supply
     * taking first the destination with the smallest transportation cost.
     * If there are no more sources to push, then the problem is solved. */
    public boolean pushCommodities() {

        while (!this.isEmpty()) {
            int cost = 0;

            while (!pq.isEmpty() && pq.peek().isFull()) {
                pq.poll();
            }

            if (pq.isEmpty()) {
                return true; // problem solved
            }

            ToDestination toDest = pq.poll();
            Destination dest = toDest.destination;

            int oldSupply = supply;
            supply = dest.pushSupply(supply);

            int supplyPushed = oldSupply - supply;

            cost += supplyPushed * toDest.costToDestination;
//            System.out.println(name + " -> " + dest.name + " " + supplyPushed + " +" + cost);
            transportCost += cost;
        }

        return false; // problem still need to be solved
    }

    /** Adds a unique destination to the PriorityQueue */
    public void addDestination(Destination d, int cost) {
        ToDestination td = new ToDestination(d, cost);

        if (checkDuplicates) {
            for (ToDestination ds : pq) {
                if (ds.equals(td)) {
                    return;
                }
            }
        }

        destinations.add(td);
        pq.add(td);
    }

    public void checkDuplicates(boolean b) {
        checkDuplicates = b;
    }

    /** Check whether the source is empty or not */
    private boolean isEmpty() {
        return supply == 0;
    }

    /** @return the type of the source */
    public SourceType getType() {
        return type;
    }

    /** @return the transportation cost */
    public int getTransportCost() {
        return transportCost;
    }

    public String showDestinationsCost() {
        StringBuilder s = new StringBuilder();

        for (ToDestination td : destinations) {
            s.append(td.costToDestination).append("      ");
        }

        return s.toString();
    }

    public String showDestinationNames() {
        StringBuilder sb = new StringBuilder();
        for (ToDestination td : destinations) {
            sb.append(td.destination.name).append("    ");
        }
        sb.append("\n");

        return sb.toString();
    }

    public String showDestinationsCapacity() {
        StringBuilder sb = new StringBuilder();

        for (ToDestination td : destinations) {
            sb.append(td.destination.maxSupply).append("     ");
        }
        sb.append("\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Source: " + "Name: " + name + " type: " + type + " supply:" + supply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return name.equals(source.name);
    }

}

/** This comparator is used for the PriorityQueue in the Source class */
class DestinationComparator implements Comparator<ToDestination> {
    @Override
    public int compare(ToDestination o1, ToDestination o2) {
        return o1.costToDestination - o2.costToDestination;
    }
}
