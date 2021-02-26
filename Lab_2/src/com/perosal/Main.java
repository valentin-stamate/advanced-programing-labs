package com.perosal;

import java.util.ArrayList;
import java.util.List;

/** @author Stamate Valentin
 * Date created: 22.02.2021 */
public class Main {

    public static void main(String[] args) {

        compulsory();
        optional();
        bonus();
    }

    private static void compulsory() {
        System.out.println("\n---=== Compulsory ===---");

        Problem problem = new Problem();

        Factory fact1 = new Factory("S1", 10);
        Warehouse ware1 = new Warehouse("S2", 35);
        Warehouse ware2 = new Warehouse("S3", 25);

        Source source1 = fact1;
        Source source2 = ware1;
        Source source3 = ware2;

        System.out.println(source1.toString());
        System.out.println(source2.toString());
        System.out.println(source3.toString());

    }

    private static void optional() {
        System.out.println("\n---=== Optional ===---");

        Problem problem = new Problem();

        Factory fact1 = new Factory("S1", 10);
        Warehouse ware1 = new Warehouse("S2", 35);
        Warehouse ware2 = new Warehouse("S3", 25);

        Destination dest1 = new Destination("D1", 20);
        Destination dest2 = new Destination("D2", 25);
        Destination dest3 = new Destination("D3", 25);

        fact1.addDestination(dest1, 2);
        fact1.addDestination(dest2, 3);
        fact1.addDestination(dest3, 1);
        fact1.addDestination(dest3, 19);

        ware1.addDestination(dest1, 5);
        ware1.addDestination(dest2, 4);
        ware1.addDestination(dest2, 8);
        ware1.addDestination(dest3, 10);

        ware2.addDestination(dest1, 5);
        ware2.addDestination(dest2, 6);
        ware2.addDestination(dest3, 8);

        problem.addSource(fact1);
        problem.addSource(ware1);
        problem.addSource(ware2);
        problem.addSource(ware2);

        System.out.println(problem.toString());

        problem.solve();

        System.out.println(problem.getTotalCost());
    }

    private static void bonus() {
        System.out.println("\n---=== Bonus ===---");

        Problem problem = new Problem();
        problem.checkDuplicates(false);

        List<Destination> destinations = new ArrayList<>();

        int nSources = 7000;
        int nDestinations = 7000;
        for (int i = 0; i < 7000; i++) {
            int rand = (int) (Math.random() * 5000);
            Destination destination = new Destination("" + i, rand);
            destinations.add(destination);
        }

        for (int i = 0; i < 7000; i++) {
            int rand = (int) (Math.random() * 10000);
            Source source = new Factory("" + i, rand);

            source.checkDuplicates(false);

            for (Destination destination : destinations) {
                rand = ((int) (Math.random() * 10000)) % 30 + 1;
                source.addDestination(destination, rand);
            }

            problem.addSource(source);

        }

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long time1 = System.currentTimeMillis();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        problem.solve();
        long time2 = System.currentTimeMillis();

        System.out.println(problem.getTotalCost());

        System.out.println("\nPerformance for " + nSources + " sources and " + nDestinations + " destinations:");
        System.out.println("Time: " + (time2 - time1) + "ms");
        System.out.println("Memory: " + memory / (1024 * 1024) + "mb");

    }

}


