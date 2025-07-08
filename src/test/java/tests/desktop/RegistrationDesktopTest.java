package tests.desktop;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import pageObjects.RegistrationPage;
import tests.TestData;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationDesktopTest {
    private static final String BASE_URL = "https://meinesuppe.de";
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(BASE_URL + "/");
    }

    @Test
    public void testRegistrationDesktop() {
        driver.findElement(By.linkText("Mein Konto")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("/mein-konto/"));
        assertTrue(driver.getCurrentUrl().contains("/mein-konto/"));

        String username = TestData.generateUsername();
        String email = TestData.generateEmail();
        String password = TestData.generatePassword();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterUsername(username);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.acceptDataPrivacy();
        registrationPage.clickRegister();

        assertTrue(registrationPage.isActivationInfoVisible());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}