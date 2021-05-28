package app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class ResourceProperties {
    private String prompt = "";
    private String locales = "";
    private String localeSet = "";
    private String info = "";
    private String invalid = "";

    public ResourceProperties(Locale locale) {
        String languageTag = locale.toString();

        Properties prop = new Properties();
        String path = "Messages.properties";
        if (languageTag.equals("ro_RO")) {
            path = "Messages_ro.properties";
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);

        if (inputStream != null) {
            try {
                prop.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            prompt = prop.getProperty("prompt");
            locales = prop.getProperty("locales");
            localeSet = prop.getProperty("locale.set");
            info = prop.getProperty("info");
            invalid = prop.getProperty("invalid");
            return;
        }

        System.out.println("Null Input Stream");
    }

    public String getPrompt() {
        return prompt;
    }

    public String getLocales() {
        return locales;
    }

    public String getLocaleSet() {
        return localeSet;
    }

    public String getInfo() {
        return info;
    }

    public String getInvalid() {
        return invalid;
    }
}
