package tests.desktop;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.HomePage;

import static org.junit.jupiter.api.Assertions.*;

public class NavigationDesktopTest {
    private static final String BASE_URL = "https://meinesuppe.de";
    private WebDriver driver;
    private HomePage homePage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(BASE_URL + "/");
        homePage = new HomePage(driver);
        homePage.acceptCookies();
    }

    @Test
    public void testFullNavigationAndBreadcrumbs() {
        assertHome();
        homePage.goToHaendlerAngebote();
        waitShort();
        assertUrlPathEquals("/haendler-angebote/");
        assertEquals("Startseite / Händler Angebote", homePage.getBreadcrumbText());

        homePage.goToManufaktur();
        waitShort();
        assertUrlPathEquals("/manufaktur/");
        assertEquals("Startseite / Manufaktur", homePage.getBreadcrumbText());

        homePage.goToMeinKonto();
        waitShort();
        assertUrlPathEquals("/mein-konto/");
        assertEquals("Startseite / Mein Konto", homePage.getBreadcrumbText());

        homePage.goToKontakt();
        waitShort();
        assertUrlPathEquals("/kontakt/");
        assertEquals("Startseite / Kontakt", homePage.getBreadcrumbText());

        homePage.goToHome();
        waitShort();
        assertUrlPathEquals("/");
    }

    private void assertHome() {
        String url = driver.getCurrentUrl();
        assertTrue(url.equals(BASE_URL + "/") || url.equals(BASE_URL));
        assertFalse(homePage.getHeadlineText().isEmpty());
    }

    private void assertUrlPathEquals(String expectedPath) {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.startsWith(BASE_URL));
        String actualPath = currentUrl.substring(BASE_URL.length());
        assertEquals(expectedPath, actualPath);
    }

    private void waitShort() {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}