package com.perosal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.perosal.location.City;
import com.perosal.location.Location;
import com.perosal.location.TravelPlan;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnitTest {

    private TravelPlan travelPlan;
    private int nDays, nLocations, hourStart, hourEnd;


    public UnitTest() throws FileNotFoundException {

        File input = new File("bonus.in");
        Scanner scanner = new Scanner(input);

        nLocations = scanner.nextInt();
        nDays = scanner.nextInt();
        hourStart = scanner.nextInt();
        hourEnd = scanner.nextInt();

        City city = new City("Braila");
        List<Location> locationList = new ArrayList<>();

        for (int i = 1; i <= nLocations; i++) {
            Location location = Main.getLocation(scanner);
            locationList.add(location);
            city.addLocation(location);
        }

        while (scanner.hasNext()) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            int cost = scanner.nextInt();

            city.addCostBetween(i, j, cost);
            city.addCostBetween(j, i, cost);
        }

        this.travelPlan = new TravelPlan(city, locationList);
    }

    @Test
    public void testAlgorithm() {
        travelPlan.travel(nDays, hourStart, hourEnd);
        assertEquals(4, 4);
    }

}
