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

import static org.junit.jupiter.api.Assertions.*;

@Epic("Lieferadresse")
@Feature("Rechnungsadresse Desktop")
public class LieferadresseDesktopTest {
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
    @Story("Login und Rechnungsadresse bearbeiten")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login und Änderung der Rechnungsadresse am Desktop")
    public void testLoginAndEditBillingAddress() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        homePage.goToMeinKonto();
        waitShort();

        loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        waitShort();

        assertEquals(username, loginPage.getGreetingName());
        assertTrue(driver.getCurrentUrl().endsWith("/mein-konto/"));
        assertEquals("Startseite / Mein Konto", homePage.getBreadcrumbText());

        driver.get(BASE_URL + "/mein-konto/edit-address/");
        wait.until(ExpectedConditions.urlContains("/mein-konto/edit-address/"));

        EditAddressPage editAddressPage = new EditAddressPage(driver);
        editAddressPage.clickAddBillingAddress();

        editAddressPage.fillBillingAddress(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                TestData.COUNTRY,
                TestData.ADDRESS,
                TestData.POSTCODE,
                TestData.CITY,
                TestData.EMAIL
        );
        editAddressPage.saveAddress();

        String successMsg = editAddressPage.getSuccessMessage();
        assertTrue(successMsg.contains("Adresse erfolgreich geändert."), "Erfolgsmeldung nicht gefunden!");
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