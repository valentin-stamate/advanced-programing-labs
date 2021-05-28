package app;

import com.DisplayLocales;
import com.Info;
import com.SetLocale;

import java.util.Locale;
import java.util.Scanner;

public abstract class LocaleExplore {

    public static void start() {

        while (true) {

            System.out.print("$ ");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            String[] tokens = command.split(" ");
            if (tokens[0].equals("display") && tokens[1].equals("locales")) {
                DisplayLocales.show();
            } else if (tokens[0].equals("set") && tokens[1].equals("locale")) {
                if (tokens.length == 3) {
                    SetLocale.set(tokens[2]);
                    continue;
                }
                SetLocale.set();
            } else if (tokens[0].equals("info")) {
                Info.show();
            } else {
                System.out.println("Wrong command");
            }

            System.out.println("");
        }

    }

}
