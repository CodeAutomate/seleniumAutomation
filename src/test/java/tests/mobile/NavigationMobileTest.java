package tests.mobile;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import pageObjects.HomePage;

import static org.junit.jupiter.api.Assertions.*;

@Disabled // Disable this test for now, as it may not be fully implemented or stable
public class NavigationMobileTest {
    private static final String BASE_URL = "https://meinesuppe.de";
    private WebDriver driver;
    private HomePage homePage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(800, 900));
        driver.get(BASE_URL + "/");
        homePage = new HomePage(driver);
        homePage.acceptCookies();
    }

    @Test
    public void testMobileNavigationAndBreadcrumbs() {
        assertTrue(homePage.isHamburgerMenuVisible());
        homePage.openHamburgerMenu();

        homePage.goToMobileHaendlerAngebote();
        waitShort();
        assertUrlPathEquals("/haendler-angebote/");
        assertEquals("Startseite / Händler Angebote", homePage.getBreadcrumbText());

        homePage.openHamburgerMenu();
        homePage.goToMobileManufaktur();
        waitShort();
        assertUrlPathEquals("/manufaktur/");
        assertEquals("Startseite / Manufaktur", homePage.getBreadcrumbText());

        homePage.openHamburgerMenu();
        homePage.goToMobileMeinKonto();
        waitShort();
        assertUrlPathEquals("/mein-konto/");
        assertEquals("Startseite / Mein Konto", homePage.getBreadcrumbText());

        homePage.openHamburgerMenu();
        homePage.goToMobileKontakt();
        waitShort();
        assertUrlPathEquals("/kontakt/");
        assertEquals("Startseite / Kontakt", homePage.getBreadcrumbText());

        homePage.openHamburgerMenu();
        homePage.goToMobileWarenkorb();
        waitShort();
        assertUrlPathEquals("/warenkorb/");
        assertEquals("Startseite / Warenkorb", homePage.getBreadcrumbText());

        homePage.openHamburgerMenu();
        homePage.goToMobileHome();
        waitShort();
        assertUrlPathEquals("/");
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