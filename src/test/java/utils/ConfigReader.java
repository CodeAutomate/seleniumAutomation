package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties konnte nicht geladen werden");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("config.properties konnte nicht geladen werden", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
