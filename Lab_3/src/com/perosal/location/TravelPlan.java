package com.perosal.location;

import java.util.*;

import com.perosal.location.places.Church;
import com.perosal.location.places.Museum;

public class TravelPlan {
    private final City city;
    private final List<Location> locationPreferences;
    private final Map<Location, Integer> mapToIndex;

    public TravelPlan(City city, List<Location> preferences) {
        this.city = city;
        preferences.sort(new LocationComparator());
        this.locationPreferences = preferences;
        this.mapToIndex = new HashMap<>();

        for (int i = 0; i < locationPreferences.size(); i++) {
            mapToIndex.put(locationPreferences.get(i), i);
        }

    }

    public void shortestPathBetween(Location locationA, Location locationB) {
        int n = locationPreferences.size();

        int[] parent = new int[n];
        int[] distance = new int[n];
        for (int i = 0; i < n ; i++) {
            parent[i] = -1;
            distance[i] = Integer.MAX_VALUE;
        }
        distance[mapToIndex.get(locationA)] = 0;

        Queue<Location> priorityQueue = new PriorityQueue<>(new DijkstraNodeComparator(distance, mapToIndex));

        priorityQueue.add(locationA);

        int finalIndex = -1;
        while (!priorityQueue.isEmpty()) {
            Location location = priorityQueue.poll();

            int indexA = mapToIndex.get(location);

            if (location == locationB) {
                finalIndex = indexA;
                break;
            }

            List<Location> neighbours = location.getNeighbours();

            for (Location neighbour : neighbours) {
                int indexB = mapToIndex.get(neighbour);
                int newCost = distance[indexA] + location.getCostTo(neighbour);

                if (distance[indexB] > newCost) {
                    distance[indexB] = newCost;
                    priorityQueue.add(neighbour);
                    parent[indexB] = indexA;
                }
            }
        }

        Stack<String> stack = new Stack<>();
        stack.add(locationPreferences.get(finalIndex).getName());

        while (parent[finalIndex] != -1) {
            int parentFinal = parent[finalIndex];
            stack.add(locationPreferences.get(parentFinal).getName());
            finalIndex = parentFinal;
        }

        while (!stack.empty()) {
            System.out.print(stack.pop());
            if (stack.size() != 0) {
                System.out.print(" -> ");
            }
        }
        System.out.println("");
    }

    public void travel(int nDays, int hourStart, int hourEnd) {
        Location locationStart = locationPreferences.get(0); // hotel

        int nLocations = locationPreferences.size();

        boolean[] visited = new boolean[nLocations];
        int[] locationVisitNumber = new int[nLocations];

        int hoursToVisit = hourEnd - hourStart;
        for (int day = 1; day <= nDays; day++) {
            Location currentLocation = locationStart;

            int remainingHours = hoursToVisit;

            while (remainingHours >= hoursToVisit / 2) {
                Location bestLocation = getBestLocation(currentLocation, locationVisitNumber);

                System.out.println(remainingHours + " - " + hoursToVisit);

                if (currentLocation == bestLocation) {
                    break;
                }

                int duration = getLocationDuration(bestLocation);

                if (remainingHours - duration >= 0) {
                    locationVisitNumber[mapToIndex.get(bestLocation)]++;
                    remainingHours = remainingHours - duration;
                    currentLocation = bestLocation;
                    visited[mapToIndex.get(bestLocation)] = true;
                }

                remainingHours = remainingHours - 1;
            }
        }

        System.out.println("The following locations were visited:");
        for (int i = 0; i < nLocations; i++) {
            if (visited[i]) {
                System.out.println(locationPreferences.get(i));
            }
        }

    }

    private int getLocationDuration(Location bestLocation) {
        switch (bestLocation.getType()) {
            case CHURCH:
                return ((Church)bestLocation).getVisitingDurationInHours();
            case MUSEUM:
                return ((Museum)bestLocation).getVisitingDurationInHours();
            case HOTEL:
            case RESTAURANT:
                return 0;
        }

        return 24;
    }

    private Location getBestLocation(Location currentLocation, int[] locationVisitNumber) {

        List<Location> neighbours = currentLocation.getNeighbours();
        neighbours.sort(new LocationEndingHourComparator());

        for (Location neighbour : neighbours) {
            if (locationVisitNumber[mapToIndex.get(neighbour)] == 0 && getEndingHourFromLocation(currentLocation) <= getStartHourFromLocation(neighbour)) {
                return neighbour;
            }
        }

        return neighbours.get(0);

    }

    private int getStartHourFromLocation(Location neighbour) {
        switch (neighbour.getType()) {
            case CHURCH:
                return ((Church)neighbour).getStartingHour();
            case MUSEUM:
                return ((Museum)neighbour).getStartingHour();
            case HOTEL:
            case RESTAURANT:
                return 0;
        }
        return 24;
    }

    private int getEndingHourFromLocation(Location neighbour) {
        switch (neighbour.getType()) {
            case CHURCH:
                return ((Church)neighbour).getEndingHour();
            case MUSEUM:
                return ((Museum)neighbour).getEndingHour();
            case HOTEL:
            case RESTAURANT:
                return 0;
        }
        return 24;
    }
}

class LocationComparator implements Comparator<Location> {

    @Override
    public int compare(Location locationA, Location locationB) {
        return locationA.getName().compareTo(locationB.toString());
    }
}

class DijkstraNodeComparator implements Comparator<Location> {

    private final int[] distance;
    private final Map<Location, Integer> mapToIndex;

    DijkstraNodeComparator(int[] distance, Map<Location, Integer> mapToIndex) {
        this.distance = distance;
        this.mapToIndex = mapToIndex;
    }

    @Override
    public int compare(Location locationA, Location locationB) {
        return distance[mapToIndex.get(locationA)] - distance[mapToIndex.get(locationB)];
    }
}

class LocationEndingHourComparator implements Comparator<Location> {

    @Override
    public int compare(Location locationA, Location locationB) {

        int locationHourEndingA = getEndingHour(locationA);
        int locationHourEndingB = getEndingHour(locationB);

        return locationHourEndingA - locationHourEndingB;
    }

    private int getEndingHour(Location location) {
        switch (location.getType()) {
            case CHURCH:
                return ((Church)location).getEndingHour();
            case MUSEUM:
                return ((Museum)location).getEndingHour();
            case HOTEL:
            case RESTAURANT:
                return 0;
        }
        return 0;
    }

}
