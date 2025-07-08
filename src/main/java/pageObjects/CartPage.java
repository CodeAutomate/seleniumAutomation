package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private By quantityInput = By.cssSelector("input.input-text.qty.text");
    private By updateCartButton = By.cssSelector("button[name='update_cart'][value='Warenkorb aktualisieren']");
    private By checkoutButton = By.cssSelector(".checkout-button.button.alt.wc-forward");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getQuantity() {
        return driver.findElement(quantityInput).getAttribute("value");
    }

    public void setQuantity(String quantity) {
        WebElement input = driver.findElement(quantityInput);
        input.clear();
        input.sendKeys(quantity);
    }

    public void updateCart() {
        driver.findElement(updateCartButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElementValue(quantityInput, getQuantity()));
    }

    public void proceedToCheckout() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }
}