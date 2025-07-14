package tests.desktop;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Login")
@Feature("Negativer Desktop Login")
public class LoginDesktopNegativeTest {
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
    @Story("Login mit ungültigen Zugangsdaten")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login mit falschem Benutzernamen und Passwort")
    public void testLoginWithInvalidCredentials() {
        String invalidUsername = "invalidUser" + System.currentTimeMillis();
        String invalidPassword = "invalidPass" + System.nanoTime();

        homePage.goToMeinKonto();
        waitShort();

        loginPage = new LoginPage(driver);
        loginPage.login(invalidUsername, invalidPassword);
        waitShort();

        assertTrue(loginPage.isErrorMessageVisible());
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