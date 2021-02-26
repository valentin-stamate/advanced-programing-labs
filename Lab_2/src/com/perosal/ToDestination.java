package com.perosal;

public class ToDestination {
    public final Destination destination;
    private int costToDestination;

    ToDestination(Destination destination, int cost) {
        this.destination = destination;
        setCostToDestination(cost);
    }

    /** Getter for costToDestination meaning "The cost between this source
     * and and this destination is costToDestination" */
    public int getCostToDestination() {
        return costToDestination;
    }

    /** Setter for costToDestination */
    private void setCostToDestination(int cost) {
        if (cost > 0) {
            this.costToDestination = cost;
        } else {
            System.out.println("The cost(" + cost + ")" + " to source: " + destination.getName() + " should be grater than 0. It will be set to 1.");
            this.costToDestination = 1;
        }
    }

    /** Check if the destination is full */
    public boolean isFull() {
        return destination.isFull();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        ToDestination toDestination = (ToDestination) object;
        return destination.equals(toDestination.destination);
    }

}
