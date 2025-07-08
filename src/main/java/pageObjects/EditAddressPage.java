package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class EditAddressPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By firstName = By.id("billing_first_name");
    private By lastName = By.id("billing_last_name");
    private By countryDropdown = By.cssSelector("span#select2-billing_country-container");
    private By countryInput = By.cssSelector("input.select2-search__field");
    private By address1 = By.id("billing_address_1");
    private By postcode = By.id("billing_postcode");
    private By city = By.id("billing_city");
    private By email = By.id("billing_email");
    private By saveButton = By.cssSelector("button[name='save_address']");
    private By successMsg = By.cssSelector("div.woocommerce-message[role='alert']");
    private By addBillingAddressBtn = By.cssSelector("a[href$='/mein-konto/edit-address/billing/'].edit");

    public EditAddressPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAddBillingAddress() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(addBillingAddressBtn));
        btn.click();
        wait.until(ExpectedConditions.urlContains("/mein-konto/edit-address/billing/"));
    }

    public void fillBillingAddress(String vorname, String nachname, String land, String strasse, String plz, String ort, String emailAdresse) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).clear();
        driver.findElement(firstName).sendKeys(vorname);

        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(nachname);

        driver.findElement(countryDropdown).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(countryInput));
        input.sendKeys(land);
        input.sendKeys(Keys.ENTER);

        driver.findElement(address1).clear();
        driver.findElement(address1).sendKeys(strasse);

        driver.findElement(postcode).clear();
        driver.findElement(postcode).sendKeys(plz);

        driver.findElement(city).clear();
        driver.findElement(city).sendKeys(ort);

        WebElement emailField = driver.findElement(email);
        emailField.clear();
        emailField.sendKeys(emailAdresse);
    }

    public void saveAddress() {
        driver.findElement(saveButton).click();
    }

    public String getSuccessMessage() {
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg));
        return msg.getText().trim();
    }

    public List<String> getErrorMessages() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement ul = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.woocommerce-error")));
        List<WebElement> lis = ul.findElements(By.tagName("li"));
        return lis.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}