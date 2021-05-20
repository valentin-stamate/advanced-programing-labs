package source;

import org.junit.jupiter.api.Test;

public class SourceClass {

    public static void test(String... args) {
        System.out.println("Hello world!");
    }

    public int sum(int a, int b) {
        return a + b;
    }

    @Test
    public int prod(int a, int b, int c) {
        int result = a * b * c;
        return a * b * c;
    }

    @Test
    public static String getString() {
        int i = 0;

        while (i < 100) {
            i++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "Hello world!";
    }

}
