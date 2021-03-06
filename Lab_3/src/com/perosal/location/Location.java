package com.perosal.location;

import com.perosal.location.places.LocationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Location {
    private final String name;
    private final LocationType locationType;
    private final Map<Location, Integer> costToLocation;

    public Location(String name, LocationType type) {
        this.name = name;
        this.locationType = type;
        costToLocation = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addCostTo(Location location, int cost) {
        costToLocation.put(location, cost);
    }

    public int getCostTo(Location location) {
        return costToLocation.get(location);
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", locationType=" + locationType + "}";
    }

    public List<Location> getNeighbours() {
        List<Location> neighbours = new ArrayList<>();

        for (Map.Entry<Location, Integer> entry : costToLocation.entrySet()) {
            neighbours.add(entry.getKey());
        }

        return neighbours;
    }

    public LocationType getType() {
        return this.locationType;
    }

}
