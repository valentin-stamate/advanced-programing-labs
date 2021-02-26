package com.perosal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/** A source instance has all the destination kept into a PriorityQueue*/
abstract public class Source {
    private final String name;
    private final SourceType type;
    private int supply;
    private boolean checkDuplicates;
    private int transportCost;

    private final PriorityQueue<ToDestination> priorityQueue;
    private final ArrayList<ToDestination> destinations; // for printing purposes

    Source(String name, SourceType sourceType, int supply) {
        this.name = name;
        this.type = sourceType;
        setSupply(supply);
        this.transportCost = 0;
        this.checkDuplicates = true;
        this.priorityQueue = new PriorityQueue<>(new DestinationComparator());
        this.destinations = new ArrayList<>();
    }

    /** Setter for supply */
    private void setSupply(int supply) {
        if (supply >= 0) {
            this.supply = supply;
        } else {
            supply = 0;
            System.out.println("Supply " + name + " should be grater than 0");
        }
    }

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Setter for supply */
    public int getSupply() {
        return supply;
    }

    /** Having this source, it pushed as much as possible supply
     * taking first the destination with the smallest transportation cost.
     * If there are no more sources to push, then the problem is solved. */
    public boolean pushCommodities() {

        while (!this.isEmpty()) {
            int cost = 0;

            while (!priorityQueue.isEmpty() && priorityQueue.peek().isFull()) {
                priorityQueue.poll();
            }

            if (priorityQueue.isEmpty()) {
                return true; // problem solved
            }

            ToDestination toDest = priorityQueue.poll();
            Destination dest = toDest.destination;

            int oldSupply = supply;
            supply = dest.pushSupply(supply);

            int supplyPushed = oldSupply - supply;

            cost += supplyPushed * toDest.getCostToDestination();
            transportCost += cost;
        }

        return false; // problem still need to be solved
    }

    /** Adds a unique destination to the PriorityQueue */
    public void addDestination(Destination destination, int cost) {
        ToDestination toNewDestination = new ToDestination(destination, cost);

        if (checkDuplicates) {
            for (ToDestination toDestination : priorityQueue) {
                if (toDestination.equals(toNewDestination)) {
                    return;
                }
            }
        }

        destinations.add(toNewDestination);
        priorityQueue.add(toNewDestination);
    }

    /** For a better time complexity this setter activate/deactivate the checking
     * for duplicated when adding instances */
    public void checkDuplicates(boolean checkDuplicates) {
        this.checkDuplicates = checkDuplicates;
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

    /** Return as a string all the cost destinations from this source */
    public String showDestinationsCost() {
        StringBuilder stringBuilder = new StringBuilder();

        for (ToDestination toDestination : destinations) {
            stringBuilder.append(toDestination.getCostToDestination()).append("      ");
        }

        return stringBuilder.toString();
    }

    /** Returns as a string all de destination names */
    public String showDestinationNames() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ToDestination toDestination : destinations) {
            stringBuilder.append(toDestination.destination.getName()).append("    ");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    /** Returns as a string all the destination capacities */
    public String showDestinationsCapacity() {
        StringBuilder stringBuilder = new StringBuilder();

        for (ToDestination toDestination : destinations) {
            stringBuilder.append(toDestination.destination.getMaxSupply()).append("     ");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Source: " + "Name: " + name + " type: " + type + " supply:" + supply;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Source source = (Source) object;
        return name.equals(source.name);
    }

}

/** This comparator is used for the PriorityQueue in the Source class */
class DestinationComparator implements Comparator<ToDestination> {
    @Override
    public int compare(ToDestination toDestination1, ToDestination toDestination2) {
        return toDestination1.getCostToDestination() - toDestination2.getCostToDestination();
    }
}
