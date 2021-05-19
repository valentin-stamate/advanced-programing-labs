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
        System.out.println(result);
        return a * b * c;
    }

    @Test
    public static String getString() {
        return "Hello world!";
    }

}
