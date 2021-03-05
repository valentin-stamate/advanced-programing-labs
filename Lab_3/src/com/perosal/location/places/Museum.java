package com.perosal.location.places;

import com.perosal.location.Location;
import com.perosal.location.places.types.Payable;
import com.perosal.location.places.types.Visitable;

import java.util.Calendar;

public class Museum extends Location implements Payable, Visitable {
    private int entryFee;
    private Calendar openStart;
    private Calendar openEnd;

    public Museum(String name, int fee, Calendar openStart, Calendar openEnd) {
        super(name, LocationType.MUSEUM);
        this.setFee(fee);
        this.setOpeningHours(openStart, openEnd);
    }

    public Museum(String name, int fee) {
        super(name, LocationType.MUSEUM);
        this.setFee(fee);
        openStart = Calendar.getInstance();
        openEnd = Calendar.getInstance();
        this.setDefaultHours(openStart, openEnd);
    }

    public Calendar getOpenStart() {
        return openStart;
    }

    public Calendar getOpenEnd() {
        return openEnd;
    }

    @Override
    public int getFee() {
        return entryFee;
    }

    @Override
    public void setFee(int fee) {
        this.entryFee = fee;
    }

    @Override
    public void setOpeningHours(Calendar openStart, Calendar openEnd) {
        this.openStart = openStart;
        this.openEnd = openEnd;
    }

    @Override
    public String getOpeningHours() {
        return "The museum " + getName() + " is opened between: "
                + openStart.get(Calendar.HOUR) + ":" + openStart.get(Calendar.MINUTE)
                + " and "
                + openEnd.get(Calendar.HOUR) + ":" + openEnd.get(Calendar.MINUTE);
    }
}
