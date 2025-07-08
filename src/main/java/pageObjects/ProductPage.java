package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ProductPage {
    private final WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectMenge175gKba() {
        WebElement select = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("menge")));
        Select dropdown = new Select(select);

        dropdown.selectByVisibleText("175g kbA");
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", select
        );
    }

    public void addToCart() {
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.single_add_to_cart_button")));
        btn.click();
    }

    public void clickShowCart() {
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.button.wc-forward")));
        btn.click();
    }

    public void setQuantity(String quantity) {
        WebElement input = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.input-text.qty.text")));
        input.clear();
        input.sendKeys(quantity);
    }

    public boolean isMessageVisible() {
        try {
            WebElement message = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("div.woocommerce-message, .woocommerce-notices-wrapper")));
            return message.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}