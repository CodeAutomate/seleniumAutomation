package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CategoryPage {
    private WebDriver driver;
    private By firstProduct = By.cssSelector("ul.products.columns-3 li:first-child");

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickFirstProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();
    }
}