package tests.desktop;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.HomePage;
import pageObjects.CategoryPage;
import pageObjects.ProductPage;
import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import tests.TestData;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class BestellprozessDesktopTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void testBestellprozessDesktop() throws InterruptedException {
        driver.get("https://meinesuppe.de/");
        assertEquals("https://meinesuppe.de/", driver.getCurrentUrl());

        HomePage homePage = new HomePage(driver);
        homePage.acceptCookies();

        homePage.goToLeckereBioGerichte();
        wait.until(d -> driver.getCurrentUrl().contains("/produkt-kategorie/leckere-bio-gerichte/"));
        assertEquals("https://meinesuppe.de/produkt-kategorie/leckere-bio-gerichte/", driver.getCurrentUrl());

        new CategoryPage(driver).clickFirstProduct();
        wait.until(d -> driver.getCurrentUrl().contains("/produkt/alb-leisa-linsenschmaus/"));
        assertEquals("https://meinesuppe.de/produkt/alb-leisa-linsenschmaus/", driver.getCurrentUrl());

        ProductPage productPage = new ProductPage(driver);
        productPage.selectMenge175gKba();
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.single_add_to_cart_button")));
        productPage.addToCart();

        productPage.clickShowCart();
        wait.until(d -> driver.getCurrentUrl().contains("/warenkorb/"));
        assertEquals("https://meinesuppe.de/warenkorb/", driver.getCurrentUrl());

        CartPage cartPage = new CartPage(driver);
        assertEquals("1", cartPage.getQuantity());
        cartPage.setQuantity("2");
        cartPage.updateCart();
        assertEquals("2", cartPage.getQuantity());

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