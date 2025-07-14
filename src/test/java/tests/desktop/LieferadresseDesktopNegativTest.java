package tests.desktop;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.EditAddressPage;
import tests.TestData;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Lieferadresse")
@Feature("Negative Rechnungsadresse Desktop")
public class LieferadresseDesktopNegativTest {
    private static final String BASE_URL = "https://meinesuppe.de";
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL + "/");
        homePage = new HomePage(driver);
        homePage.acceptCookies();
    }

    @Test
    @Story("Validierungsfehler bei Rechnungsadresse")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Rechnungsadresse mit fehlenden Pflichtfeldern")
    public void testEditBillingAddressValidationErrors() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        homePage.goToMeinKonto();
        waitShort();

        loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        waitShort();

        driver.get(BASE_URL + "/mein-konto/edit-address/");
        wait.until(ExpectedConditions.urlContains("/mein-konto/edit-address/"));

        EditAddressPage editAddressPage = new EditAddressPage(driver);
        editAddressPage.clickAddBillingAddress();

        editAddressPage.fillBillingAddress(
                "", // Vorname leer
                "", // Nachname leer
                TestData.COUNTRY,
                "", // Straße leer
                TestData.POSTCODE,
                TestData.CITY,
                TestData.EMAIL
        );
        editAddressPage.saveAddress();

        List<String> errors = editAddressPage.getErrorMessages();
        assertEquals(3, errors.size());
        assertTrue(errors.contains("Vorname ist ein Pflichtfeld."));
        assertTrue(errors.contains("Nachname ist ein Pflichtfeld."));
        assertTrue(errors.contains("Straße ist ein Pflichtfeld."));
    }

    @Step("Kurze Wartezeit")
    private void waitShort() {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}