package com.perosal;

import java.util.ArrayList;

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

        Problem p = new Problem();

        Factory f1 = new Factory("S1", 10);
        Warehouse w1 = new Warehouse("S2", 35);
        Warehouse w2 = new Warehouse("S3", 25);

        Source s1 = f1;
        Source s2 = w1;
        Source s3 = w2;

        System.out.println(s1.toString());
        System.out.println(s2.toString());
        System.out.println(s3.toString());

    }

    private static void optional() {
        System.out.println("\n---=== Optional ===---");

        Problem p = new Problem();

        Factory s1 = new Factory("S1", 10);
        Warehouse s2 = new Warehouse("S2", 35);
        Warehouse s3 = new Warehouse("S3", 25);

        Destination d1 = new Destination("D1", 20);
        Destination d2 = new Destination("D2", 25);
        Destination d3 = new Destination("D3", 25);

        s1.addDestination(d1, 2);
        s1.addDestination(d2, 3);
        s1.addDestination(d3, 1);
        s1.addDestination(d3, 19);

        s2.addDestination(d1, 5);
        s2.addDestination(d2, 4);
        s2.addDestination(d3, 8);
        s2.addDestination(d3, 10);

        s3.addDestination(d1, 5);
        s3.addDestination(d2, 6);
        s3.addDestination(d3, 8);

        p.addSource(s1);
        p.addSource(s1);
        p.addSource(s2);
        p.addSource(s3);

        System.out.println(p.toString());

        p.solve();

        System.out.println(p.getTotalCost());
    }

    private static void bonus() {
        System.out.println("\n---=== Bonus ===---");
        Problem p = new Problem();
        p.checkDuplicates(false);

        ArrayList<Destination> destinations = new ArrayList<>();

        int nSources = 7000;
        int nDestinations = 7000;
        for (int i = 0; i < 7000; i++) {
            int r = (int) (Math.random() * 10000);
            Destination d = new Destination("" + i, r);
            destinations.add(d);
        }

        for (int i = 0; i < 7000; i++) {
            int r = (int) (Math.random() * 10000);
            Source s = new Factory("" + i, r);

            s.checkDuplicates(false);

            for (Destination d : destinations) {
                r = (int) (Math.random() * 30);
                s.addDestination(d, r);
            }

            p.addSource(s);

        }

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();


        long t1 = System.currentTimeMillis();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        p.solve();
        long t2 = System.currentTimeMillis();

        System.out.println(p.getTotalCost());

        System.out.println("\nPerformance for " + nSources + " sources and " + nDestinations + " destinations:");
        System.out.println("Time: " + (t2 - t1) + "ms");
        System.out.println("Memory: " + memory / (1024 * 1024) + "mb");

    }

}


