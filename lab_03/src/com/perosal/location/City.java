package com.perosal.location;

import com.perosal.location.places.Church;
import com.perosal.location.places.Museum;
import com.perosal.location.places.types.Payable;
import com.perosal.location.places.types.Visitable;

import java.util.ArrayList;
import java.util.Comparator;
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

        List<Location> freeToVisit = new ArrayList<>();

        for (Location location : locationList) {
            if (location instanceof Visitable && !(location instanceof Payable)) {
                freeToVisit.add(location);
            }
        }

        freeToVisit.sort(new LocationHourComparator());

        for (Location location : freeToVisit) {
            System.out.println(location);
        }

    }

    protected List<Location> getLocations() {
        return locationList;
    }

}

class LocationHourComparator implements Comparator<Location> {

    @Override
    public int compare(Location location1, Location location2) {
        long time1 = 0;
        long time2 = 0;

        time1 = getTime(location1);
        time2 = getTime(location2);

        return (int)(time1 - time2);
    }

    private long getTime(Location location) {
        if (location instanceof Church) {
            Church church = (Church) location;
            return church.getOpeningTimeInMs();
        }

        Museum museum = (Museum) location;
        return museum.getOpeningTimeInMs();
    }

}
