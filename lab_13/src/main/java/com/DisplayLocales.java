package com;

import java.text.DateFormat;
import java.util.Locale;

public abstract class DisplayLocales {

    public static void show() {
        Locale[] list = DateFormat.getAvailableLocales();
        System.out.printf("| %-15s | \n", "Locales");
        for (Locale aLocale : list) {
            System.out.printf("| %-15s |\n", aLocale.toString());
        }
    }

}
