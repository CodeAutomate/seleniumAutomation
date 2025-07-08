package tests.mobile;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObjects.HomePage;
import pageObjects.RegistrationPage;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class RegistrationMobileTest {
    private static final String BASE_URL = "https://meinesuppe.de";
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=375,812"); // iPhone X Größe
        driver = new ChromeDriver(options);
        driver.get(BASE_URL + "/");
    }

    @Test
    public void testRegistrationMobile() {
        HomePage homePage = new HomePage(driver);
        homePage.acceptCookies();
        homePage.openHamburgerMenu();
        homePage.goToMobileMeinKonto();

        assertTrue(driver.getCurrentUrl().contains("/mein-konto/"));

        String username = "user" + System.currentTimeMillis();
        String email = "test" + System.nanoTime() + "@mailinator.com";
        String password = "PW" + System.nanoTime() + "!Aa";

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