package com.perosal.lab_11.util;

import java.util.List;

public class Util {

    /* INSPIRED FROM https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/ */
    public static void dfsUtil(int n, List<Integer>[] Graph, boolean[] visited, int[] discovered, int[] low, int[] parent, boolean[] artPoints, Time time) {
        int children = 0;

        visited[n] = true;
        discovered[n] = low[n] = ++time.time;

        for (int j : Graph[n]) {

            if (!visited[j]) {
                children++;
                parent[j] = n;

                dfsUtil(j, Graph, visited, discovered, low, parent, artPoints, time);

                low[n] = Math.min(low[n], low[j]);

                if (parent[n] == -1 && children > 1) {
                    artPoints[n] = true;
                }

                if (parent[n] != -1 && low[j] >= discovered[n]) {
                    artPoints[n] = true;
                }

                continue;
            }

            if (j != parent[n]) {
                low[n] = Math.min(low[n], discovered[j]);
            }

        }

    }

}
