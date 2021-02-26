package com.perosal;

import java.util.Objects;

public class ToDestination {
    public final Destination destination;
    public final int costToDestination;

    ToDestination(Destination d, int cost) {
        this.destination = d;
        this.costToDestination = cost;
    }

    /** Check if the destination is full */
    public boolean isFull() {
        return destination.isFull();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        ToDestination that = (ToDestination) o;
        return destination.equals(that.destination);
    }

}
