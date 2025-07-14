package tests.desktop;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import pageObjects.RegistrationPage;
import tests.TestData;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Registrierung")
@Feature("Desktop Registrierung")
public class RegistrationDesktopTest {
    private static final String BASE_URL = "https://meinesuppe.de";
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(BASE_URL + "/");
    }

    @Test
    @Story("Nutzer kann sich registrieren")
    @Description("Testet die Registrierung eines neuen Nutzers über die Desktop-Oberfläche")
    public void testRegistrationDesktop() {
        openAccountPage();
        verifyAccountPageUrl();

        String username = TestData.generateUsername();
        String email = TestData.generateEmail();
        String password = TestData.generatePassword();

        fillRegistrationForm(username, email, password);

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.acceptDataPrivacy();
        registrationPage.clickRegister();

        assertTrue(registrationPage.isActivationInfoVisible());
    }

    @Step("Klick auf 'Mein Konto'")
    private void openAccountPage() {
        driver.findElement(By.linkText("Mein Konto")).click();
    }

    @Step("Prüfe URL enthält '/mein-konto/'")
    private void verifyAccountPageUrl() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/mein-konto/"));
        assertTrue(driver.getCurrentUrl().contains("/mein-konto/"));
    }

    @Step("Fülle Registrierungsformular aus")
    private void fillRegistrationForm(String username, String email, String password) {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterUsername(username);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}