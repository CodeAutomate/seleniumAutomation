package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();

    static {
        try {
            props.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("config.properties konnte nicht geladen werden", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}