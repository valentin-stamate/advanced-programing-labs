package app;

import com.DisplayLocales;
import com.Info;
import com.SetLocale;
import java.util.Locale;
import java.util.Scanner;

public abstract class LocaleExplore {

    public static void start() {

        while (true) {
            Locale locale = Locale.getDefault();
            ResourceProperties resourceProperties = new ResourceProperties(locale);

            System.out.printf("%s $ ", resourceProperties.getPrompt());

            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            String[] tokens = command.split(" ");
            if (tokens[0].equals("display") && tokens[1].equals("locales")) {
                System.out.printf("%s\n", resourceProperties.getLocales());
                DisplayLocales.show();
            } else if (tokens[0].equals("set") && tokens[1].equals("locale")) {
                if (tokens.length == 3) {
                    SetLocale.set(tokens[2]);
                    locale = Locale.getDefault();
                    resourceProperties = new ResourceProperties(locale);
                    System.out.println(resourceProperties.getLocaleSet());
                    continue;
                }
                SetLocale.set();

                locale = Locale.getDefault();
                resourceProperties = new ResourceProperties(locale);
                System.out.println(resourceProperties.getLocaleSet());
            } else if (tokens[0].equals("info")) {
                System.out.println(resourceProperties.getInfo());
                Info.show();
            } else {
                System.out.println(resourceProperties.getInvalid());
            }

            System.out.println("");
        }

    }

}
