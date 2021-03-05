package com.perosal.location.places;

import com.perosal.location.Location;
import com.perosal.location.places.types.Classifiable;

public class Restaurant extends Location implements Classifiable {
    private int rank;

    public Restaurant(String name, int rank) {
        super(name, LocationType.RESTAURANT);
        this.setRank(rank);
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public void setRank(int rank) {
        if (rank > 5 || rank < 1) {
            System.out.println("The rank should be between 1 and 5 but is " + rank);
            this.rank = 1;
        }
        this.rank = rank;
    }
}
