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
    public static String getString() {
        return "Hello world!";
    }

}
