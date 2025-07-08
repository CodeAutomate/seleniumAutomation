package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private By cookieAcceptButton = By.id("cookie_action_close_header");
    private By headline = By.cssSelector(".has-text-align-left.has-luminous-vivid-orange-color.has-text-color.has-large-font-size");
    private By haendlerAngeboteButton = By.id("menu-item-465");
    private By manufakturTab = By.id("menu-item-21");
    private By meinKontoTab = By.id("menu-item-3112");
    private By kontaktTab = By.id("menu-item-24");
    private By homeTab = By.id("menu-item-20");
    private By breadcrumb = By.cssSelector("nav.woocommerce-breadcrumb");

    private By hamburgerMenuButton = By.id("rmp_menu_trigger-2569");
    private By mobileMenuContainer = By.id("rmp-container-2569");
    private By mobileHaendlerAngebote = By.id("rmp-menu-item-465");
    private By mobileManufaktur = By.id("rmp-menu-item-21");
    private By mobileMeinKonto = By.id("rmp-menu-item-3112");
    private By mobileKontakt = By.id("rmp-menu-item-24");
    private By mobileHome = By.id("rmp-menu-item-20");
    private By mobileWarenkorb = By.id("rmp-menu-item-1883");

    // NEU: Selector für "Leckere Bio Gerichte"
    private By leckereBioGerichte = By.cssSelector("div#woocommerce_product_categories-4 ul.product-categories li.cat-item-20");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void acceptCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton));
            driver.findElement(cookieAcceptButton).click();
        } catch (Exception e) {
            // Button nicht vorhanden oder nicht klickbar, ignoriere
        }
    }

    public String getHeadlineText() {
        return driver.findElement(headline).getText();
    }

    public void goToHaendlerAngebote() {
        clickWhenClickable(haendlerAngeboteButton);
    }

    public void goToManufaktur() {
        clickWhenClickable(manufakturTab);
    }

    public void goToMeinKonto() {
        clickWhenClickable(meinKontoTab);
    }

    public void goToKontakt() {
        clickWhenClickable(kontaktTab);
    }

    public void goToHome() {
        clickWhenClickable(homeTab);
    }

    public boolean isBreadcrumbVisible() {
        return driver.findElements(breadcrumb).size() > 0 && driver.findElement(breadcrumb).isDisplayed();
    }

    public boolean isHamburgerMenuVisible() {
        return driver.findElements(hamburgerMenuButton).size() > 0 && driver.findElement(hamburgerMenuButton).isDisplayed();
    }

    public void openHamburgerMenu() {
        clickWhenClickable(hamburgerMenuButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(mobileMenuContainer));
    }

    public void goToMobileHaendlerAngebote() {
        clickMobileMenuItem(mobileHaendlerAngebote);
    }

    public void goToMobileManufaktur() {
        clickMobileMenuItem(mobileManufaktur);
    }

    public void goToMobileMeinKonto() {
        clickMobileMenuItem(mobileMeinKonto);
    }

    public void goToMobileKontakt() {
        clickMobileMenuItem(mobileKontakt);
    }

    public void goToMobileWarenkorb() {
        clickMobileMenuItem(mobileWarenkorb);
    }

    public void goToMobileHome() {
        clickMobileMenuItem(mobileHome);
    }

    public String getBreadcrumbText() {
        return driver.findElement(breadcrumb).getText();
    }

    // NEU: Methode für "Leckere Bio Gerichte"
    public void goToLeckereBioGerichte() {
        clickWhenClickable(leckereBioGerichte);
    }

    private void clickWhenClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    private void clickMobileMenuItem(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }
}