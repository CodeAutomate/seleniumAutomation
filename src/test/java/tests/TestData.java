package tests;

public class TestData {
    public static final String VALID_USER = "dein_user";
    public static final String VALID_PASS = "dein_passwort";
    public static final String FIRST_NAME = "Max";
    public static final String LAST_NAME = "Mustermann";
    public static final String ADDRESS = "Musterstraße 1";
    public static final String POSTCODE = "12345";
    public static final String CITY = "Musterstadt";
    public static final String EMAIL = "max.mustermann@example.com";
    public static final String COUNTRY = "Deutschland";

    // Dynamische Testdaten für Registrierung
    public static String generateUsername() {
        return "user" + System.currentTimeMillis();
    }

    public static String generateEmail() {
        return "test" + System.nanoTime() + "@mailinator.com";
    }

    public static String generatePassword() {
        return "PW" + System.nanoTime() + "!Aa";
    }
}