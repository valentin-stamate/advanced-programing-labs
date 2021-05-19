package configuration_reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class ConfigurationReader {
    private static final String CONFIG_PATH = "C:\\Users\\Valentin\\Desktop\\Sem.ll\\Programare Avansata\\Laborator\\Laborator-Java\\lab_09\\src\\main\\resources\\config.properties";

    public static String readImplementationType() {
        return readProperty("implementation.type");
    }

    private static String readProperty(String property) {
        Properties properties = new Properties();

        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH);
            properties.load(fileInputStream);
            return properties.getProperty(property);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
