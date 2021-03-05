package com.perosal.location.places;

import com.perosal.location.Location;
import com.perosal.location.places.types.Classifiable;
import com.perosal.location.places.types.Payable;

public class Hotel extends Location implements Classifiable, Payable {
    private int rank;
    private int accommodationFee;

    public Hotel(String name, int rank, int accommodationFee) {
        super(name, LocationType.HOTEL);
        setRank(rank);
        setFee(accommodationFee);
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public void setRank(int rank) {
        if (rank > 5 || rank < 1) {
            System.out.println("The rank should be between 1 and 5");
            this.rank = 1;
        }
        this.rank = rank;
    }

    @Override
    public int getFee() {
        return accommodationFee;
    }

    @Override
    public void setFee(int fee) {
        this.accommodationFee = fee;
    }
}
