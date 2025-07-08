package tests.mobile;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.*;
import tests.TestData;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class BestellprozessMobileTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 812);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.2 Mobile/15E148 Safari/604.1");

        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void testBestellprozessMobile() {
        driver.get("https://meinesuppe.de/");
        assertEquals("https://meinesuppe.de/", driver.getCurrentUrl());

        HomePage homePage = new HomePage(driver);
        homePage.acceptCookies();

        // Kategorie auswählen über Page Object
        homePage.goToLeckereBioGerichte();
        wait.until(d -> driver.getCurrentUrl().contains("/produkt-kategorie/leckere-bio-gerichte/"));
        assertEquals("https://meinesuppe.de/produkt-kategorie/leckere-bio-gerichte/", driver.getCurrentUrl());

        new CategoryPage(driver).clickFirstProduct();
        wait.until(d -> driver.getCurrentUrl().contains("/produkt/alb-leisa-linsenschmaus/"));
        assertEquals("https://meinesuppe.de/produkt/alb-leisa-linsenschmaus/", driver.getCurrentUrl());

        ProductPage productPage = new ProductPage(driver);
        productPage.setQuantity("2");
        productPage.addToCart();
        assertTrue(productPage.isMessageVisible());
        assertEquals("https://meinesuppe.de/produkt/alb-leisa-linsenschmaus/", driver.getCurrentUrl());

        productPage.clickShowCart();
        wait.until(d -> driver.getCurrentUrl().contains("/warenkorb/"));
        assertEquals("https://meinesuppe.de/warenkorb/", driver.getCurrentUrl());

        CartPage cartPage = new CartPage(driver);
        assertEquals("2", cartPage.getQuantity());
        cartPage.setQuantity("1");
        cartPage.updateCart();
        assertEquals("1", cartPage.getQuantity());

        cartPage.proceedToCheckout();
        wait.until(d -> driver.getCurrentUrl().contains("/kasse/"));
        assertEquals("https://meinesuppe.de/kasse/", driver.getCurrentUrl());

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.selectTitle("Frau");
        checkoutPage.fillForm(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                TestData.ADDRESS,
                TestData.POSTCODE,
                TestData.CITY,
                TestData.EMAIL
        );
    }
}