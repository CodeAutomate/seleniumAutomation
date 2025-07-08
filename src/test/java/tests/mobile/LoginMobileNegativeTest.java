package tests.mobile;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObjects.HomePage;
import pageObjects.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class LoginMobileNegativeTest {
    private static final String BASE_URL = "https://meinesuppe.de";
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=375,812"); // iPhone X Größe
        driver = new ChromeDriver(options);
        driver.get(BASE_URL + "/");
        homePage = new HomePage(driver);
        homePage.acceptCookies();
    }

    @Test
    public void testLoginWithInvalidCredentialsMobile() {
        String invalidUsername = "invalidUser" + System.currentTimeMillis();
        String invalidPassword = "invalidPass" + System.nanoTime();

        if (homePage.isHamburgerMenuVisible()) {
            homePage.openHamburgerMenu();
            waitShort();
            homePage.goToMobileMeinKonto();
        } else {
            homePage.goToMeinKonto();
        }
        waitShort();

        loginPage = new LoginPage(driver);
        loginPage.login(invalidUsername, invalidPassword);
        waitShort();

        assertTrue(loginPage.isErrorMessageVisible());
    }

    private void waitShort() {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}