package com;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public abstract class Info {
    public static void show() {
        Locale locale = Locale.getDefault();
        Currency currency = Currency.getInstance(locale);
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);

        StringBuilder monthsStringBuilder = new StringBuilder();
        String[] months = dateFormatSymbols.getMonths();
        for (String month : months) {
            monthsStringBuilder.append(month).append(" ");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy (dd MMMMM yyyy)", locale);

        System.out.printf("| %-15s | %-90s |\n", "Country", locale.getDisplayCountry());
        System.out.printf("| %-15s | %-90s |\n", "Language", locale.getDisplayLanguage());
        System.out.printf("| %-15s | %-90s |\n", "Currency", currency.getSymbol());
        System.out.printf("| %-15s | %-90s |\n", "Week Days", getWeekdays(locale));
        System.out.printf("| %-15s | %-90s |\n", "Months", monthsStringBuilder);
        System.out.printf("| %-15s | %-90s |\n", "Today", simpleDateFormat.format(new Date()));
    }

    private static String getWeekdays(Locale loc) {
        WeekFields wf = WeekFields.of(loc);
        DayOfWeek day = wf.getFirstDayOfWeek();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < DayOfWeek.values().length; i++) {
            stringBuilder.append(day.getDisplayName(TextStyle.SHORT, loc)).append(" ");
            day = day.plus(1);
        }

        return stringBuilder.toString();
    }
}
