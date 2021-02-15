package com.perosal;

import javax.swing.text.Style;
import java.util.Scanner;
import java.util.Stack;

/** Author: Stamate Valentin(2B4) */
public class Main {

    public static void main(String[] args) {

        Compulsory();
        Optional(args[0]);
        Bonus();

    }

    private static void Compulsory() {
        System.out.println(" --== Compulsory ==--");
        /* Step 1 */
        System.out.println("Hello world!");

        /* Step 2 */
        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};

        /* Step 3 */
        int n =  (int) (Math.random() * 1_000_000);

        /* Step 4 */
        n *= 3;
        n += Integer.parseInt("10101");
        n += 0xFF;
        n *= 6;

        /* Step 5 */
        n = computeDigits(n);
        while (n > 9) {
            n = computeDigits(n);
        }

        /* Step 6 */

        System.out.println("Willy-nilly, this semester I will learn " + languages[n]);
    }

    private static void Optional(String arg) {
        System.out.println(" --== Optional ==--");
        long t1 = System.nanoTime();

        /* Step 1 */
        int n = Integer.parseInt(arg);

        if (n % 2 == 0) {
            System.out.println("Enter an odd number");
            return;
        }

        int[][] a = new int[n + 1][n + 1];
        initializeRandomGraph(a, n);

        if (n < 50) {
            showGraph(a, n);
        }
        boolean b = connectedComponents(a, n);
        System.out.println(b ? "\nGraph is connected" : "\nGraph is not connected");

        /* Step 2 */

        int[][] partialTree = getPartialTree(a, n);

        if (n < 50) {
            System.out.println("\nPartial tree form a[][]:");
            showGraph(partialTree, n);
        }

        /* Step 3 */
        if (n > 50) {
            long t2 = System.nanoTime();

            System.out.println("Time elapsed in ns: " + (t2 - t1));
        }

        /* Step 4 */
        /* java -Xms4G -Xmx4G com.perosal.Main 30001 */
    }

    private static void Bonus() {
        System.out.println(" --== Bonus ==--");
        int n = ((int)(Math.random() * 1000000)) % 30 + 5;

        int[][] a = new int[n + 1][n + 1];
        initializeRandomGraph(a, n);

        a = getPartialTree(a, n);

        boolean[] visited = new boolean[n + 1];

        showTree(1, a, n, 0, visited);

    }

    /** Based on a DFS algorithm
     * For every node an empty string is concatenated based on the current level
     * Complexity: O(n^2)
     * */
    private static void showTree(int node, int[][] a, int n, int level, boolean[] visited) {
        String spacer = "   ";
        for (int i = 1; i <= level; i++) {
            System.out.print(spacer);
        }

        visited[node] = true;

        System.out.println("+Node" + node);

        for (int j = 1; j <= n; j++) {
            if (a[node][j] == 1 && !visited[j]) {
                showTree(j, a, n, level + 1, visited);
            }
        }

    }

    /** Computes and returns the sum of the digits from the given number */
    private static int computeDigits(int n) {
        int sum = 0;

        while (n != 0) {
            sum += (n % 10);

            n /= 10;
        }

        return sum;
    }

    /** Returns a partial tree from the array given as argument
     * This algorithm is based on DFS iterative : if a node is not visited, add it to the three
     * Complexity: O(n^2)
     * */
    private static int[][] getPartialTree(int[][] a, int n) {

        int[][] partialTree = new int[n + 1][n + 1];
        boolean[] visited = new boolean[n + 1];

        Stack<Integer> st = new Stack<>();
        st.push(1);
        visited[1] = true;

        while (!st.empty()) {
            int node = st.peek();
            st.pop();

            for (int j = 1; j <= n; j++) {
                if (a[node][j] == 1 && !visited[j]) {
                    partialTree[node][j] = 1;
                    partialTree[j][node] = 1;

                    st.push(j);
                    visited[j] = true;
                }
            }
        }

        return partialTree;
    }

    /** Shows all the connected components from the array given as argument
     * The number connected components is equal with the number of times DFS()
     * function is called within the for loop
     * The function returns true if the graph is connected false otherwise
     * Complexity: O(n^2)
     * */
    private static boolean connectedComponents(int[][] a, int n) {
        int components = 0;

        boolean[] visited = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                components++;
                System.out.print("{ ");
                DFS(i, a, n, visited);
                System.out.print("} ");
            }
        }

        return components == 1;
    }

    /** A recursive DFS */
    private static void DFS(int node, int[][] a, int n, boolean[] visited) {
        visited[node] = true;

        if (n < 50) {
            System.out.print(node + " ");
        }
        for (int j = 1; j <= n; j++) {
            if (a[node][j] == 1 && !visited[j]) {
                DFS(j, a, n, visited);
            }
        }
    }

    /** It generates a random graph */
    private static void initializeRandomGraph(int[][] a, int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                a[i][j] = (int) (Math.random() * 1000000) % 2;
                a[j][i] = a[i][j];
            }
        }
    }

    /** Shows the graph given as argument */
    private static void showGraph(int[][] a, int n) {
        System.out.println("\nThe size of the array: " + n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println("");
        }
    }

}
