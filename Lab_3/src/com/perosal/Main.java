package com.perosal;

import com.perosal.location.City;
import com.perosal.location.Location;
import com.perosal.location.TravelPlan;
import com.perosal.location.places.*;
import com.perosal.location.places.types.Visitable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("----==== Compulsory ====----");
        File input = new File("input.in");
        Scanner scanner = new Scanner(input);

        int nLocations = scanner.nextInt();

        City city = new City("Braila");
        List<Location> locationList = new ArrayList<>();

        for (int i = 1; i <= nLocations; i++) {
            Location location = getLocation(scanner);
            city.addLocation(location);
            locationList.add(location);
        }

        while (scanner.hasNext()) {
            int i = scanner.nextInt() - 1;
            int j = scanner.nextInt() - 1;
            int cost = scanner.nextInt();

            city.addCostBetween(i, j, cost);
        }

        System.out.println("objects created\n");

        System.out.println("----==== Optional ====----");

        city.showFreeToVisit();

        Museum museum = new Museum("Name", 4);
        System.out.println(Visitable.getVisitingDuration(museum.getOpenStart(), museum.getOpenEnd()).getSeconds());

        TravelPlan travelPlan = new TravelPlan(city, locationList);
        travelPlan.shortestPathBetween(locationList.get(0), locationList.get(5));

    }

    private static Location getLocation(Scanner scanner) {
        String type = scanner.next();

        Location location = getLocationFromType(type, scanner);

        return location;
    }

    private static Location getLocationFromType(String type, Scanner scanner) {
        LocationType locationType;
        Location location;

        String name = scanner.next();

        if (type.equals("CHURCH")) {

            Calendar openStart = Calendar.getInstance();
            Calendar openEnd = Calendar.getInstance();

            setOpenHours(openStart, openEnd, scanner);

            location = new Church(name, openStart, openEnd);

        } else if (type.equals("HOTEL")) {

            int rank = getRank(scanner);
            int fee = getFee(scanner);

            location = new Hotel(name, rank, fee);

        } else if (type.equals("MUSEUM")) {

            int fee = getFee(scanner);
            Calendar openStart = Calendar.getInstance();
            Calendar openEnd = Calendar.getInstance();

            setOpenHours(openStart, openEnd, scanner);

            location = new Museum(name, fee, openStart, openEnd);

        } else if (type.equals("RESTAURANT")) {

            int rank = getRank(scanner);

            location = new Restaurant(name, rank);

        } else {

            System.out.println("Incorrect format");
            location = new Hotel("Null", 4, 5);
        }

        return location;
    }

    private static int getFee(Scanner scanner) {
        return scanner.nextInt();
    }

    private static int getRank(Scanner scanner) {
        return scanner.nextInt();
    }

    private static void setOpenHours(Calendar openStart, Calendar openEnd, Scanner scanner) {
        int hourStart = scanner.nextInt();
        int hourEnd = scanner.nextInt();

        openStart.set(Calendar.HOUR, hourStart);
        openEnd.set(Calendar.HOUR, hourEnd);
    }
}
