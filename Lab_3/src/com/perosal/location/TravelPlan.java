package com.perosal.location;

import java.util.*;

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
