package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private By titleSelect = By.id("billing_title");
    private By firstName = By.id("billing_first_name");
    private By lastName = By.id("billing_last_name");
    private By address = By.id("billing_address_1");
    private By postcode = By.id("billing_postcode_field");
    private By city = By.id("billing_city");
    private By email = By.id("billing_email_field");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectTitle(String value) {
        WebElement select = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(titleSelect));
        new Select(select).selectByVisibleText(value);
    }

    public void fillForm(String first, String last, String addr, String zip, String cityVal, String mail) {
        driver.findElement(firstName).sendKeys(first);
        driver.findElement(lastName).sendKeys(last);
        driver.findElement(address).sendKeys(addr);
        driver.findElement(postcode).sendKeys(zip);
        driver.findElement(city).sendKeys(cityVal);
        driver.findElement(email).sendKeys(mail);
    }
}