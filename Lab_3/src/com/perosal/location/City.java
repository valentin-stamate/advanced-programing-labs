package com.perosal.location;

import com.perosal.location.places.types.Payable;
import com.perosal.location.places.types.Visitable;

import java.util.ArrayList;
import java.util.List;

public class City {
    private final String cityName;
    private List<Location> locationList;

    public City(String cityName) {
        this.cityName = cityName;
        this.locationList = new ArrayList<>();
    }

    public void addLocation(Location location) {
        locationList.add(location);
    }


    public void addCostBetween(int i, int j, int cost) {
        Location locationA = locationList.get(i);
        Location locationB = locationList.get(j);
        locationA.addCostTo(locationB, cost);
    }

    public void showFreeToVisit() {
        for (Location location : locationList) {
            Class clazz = location.getClass();
            if (Visitable.class.isAssignableFrom(clazz) && !Payable.class.isAssignableFrom(clazz)) {
                System.out.println(location);
            }
        }
    }

    protected List<Location> getLocations() {
        return locationList;
    }

}
