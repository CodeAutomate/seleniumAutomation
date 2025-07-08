package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button.woocommerce-form-login__submit");
    private By greetingStrong = By.cssSelector("div.woocommerce-MyAccount-content > p > strong");
    private By errorList = By.cssSelector("ul.woocommerce-error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getGreetingName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement strongElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(greetingStrong)
        );
        return strongElement.getText().trim();
    }

    public boolean isErrorMessageVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorList)
            );
            return errorElement.getText().contains("The username or password you entered is incorrect");
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }
}