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
@Feature("Negative Registrierung Desktop")
public class RegistrationDesktopNegativeTest {
    private static final String BASE_URL = "https://meinesuppe.de";
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(BASE_URL + "/");
        acceptCookiesIfVisible();
        driver.findElement(By.linkText("Mein Konto")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/mein-konto/"));
    }

    @Step("Cookies akzeptieren, falls sichtbar")
    private void acceptCookiesIfVisible() {
        try {
            WebElement cookieBar = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("cookie-law-info-bar")));
            WebElement acceptButton = cookieBar.findElement(By.cssSelector("button, .cli-plugin-main-button"));
            acceptButton.click();
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.invisibilityOf(cookieBar));
        } catch (TimeoutException ignored) {
            // Cookie-Banner nicht sichtbar, nichts tun
        }
    }

    @Test
    @Story("Registrierung ohne Datenschutzerklärung")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Registrierung ohne Zustimmung zur Datenschutzerklärung")
    public void testRegistrationWithoutDataPrivacy() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterUsername(TestData.generateUsername());
        registrationPage.enterEmail(TestData.generateEmail());
        registrationPage.enterPassword(TestData.generatePassword());
        registrationPage.clickRegister();
        assertTrue(registrationPage.isDataPrivacyErrorVisible());
    }

    @Test
    @Story("Registrierung mit leerem Passwort")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Registrierung ohne Passwort")
    public void testRegistrationWithEmptyPassword() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterUsername(TestData.generateUsername());
        registrationPage.enterEmail(TestData.generateEmail());
        registrationPage.acceptDataPrivacy();
        registrationPage.clickRegister();
        assertTrue(registrationPage.isPasswordFieldRequired());
    }

    @Test
    @Story("Registrierung mit leerer E-Mail")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Registrierung ohne E-Mail")
    public void testRegistrationWithEmptyEmail() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterUsername(TestData.generateUsername());
        registrationPage.enterPassword(TestData.generatePassword());
        registrationPage.acceptDataPrivacy();
        registrationPage.clickRegister();
        assertTrue(registrationPage.isEmailFieldRequired());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}