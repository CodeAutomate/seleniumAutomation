package tests.desktop;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Login")
@Feature("Desktop Login")
public class LoginDesktopTest {
    private static final String BASE_URL = "https://meinesuppe.de";
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(BASE_URL + "/");
        homePage = new HomePage(driver);
        homePage.acceptCookies();
    }

    @Test
    @Story("Login mit gültigen Daten")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Erfolgreicher Login am Desktop")
    public void testLoginDesktop() {
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