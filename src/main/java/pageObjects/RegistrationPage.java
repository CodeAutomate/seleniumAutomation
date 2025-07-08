package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;
    private By usernameField = By.id("reg_username");
    private By emailField = By.id("reg_email");
    private By passwordField = By.id("reg_password");
    private By dataPrivacyCheckbox = By.id("reg_data_privacy");
    private By registerButton = By.cssSelector("button[name='register']");
    private By infoBox = By.cssSelector("div.woocommerce-info");
    private By errorList = By.cssSelector("ul.woocommerce-error");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void acceptDataPrivacy() {
        driver.findElement(dataPrivacyCheckbox).click();
    }

    public void clickRegister() {
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(registerButton));
        btn.click();
    }

    public boolean isActivationInfoVisible() {
        WebElement info = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(infoBox));
        return info.getText().contains("Keine Aktivierungs-E-Mail erhalten?");
    }

    // Prüft, ob die Datenschutzerklärung-Fehlermeldung angezeigt wird
    public boolean isDataPrivacyErrorVisible() {
        try {
            WebElement error = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(errorList));
            return error.getText().contains("Bitte akzeptiere unsere Datenschutzerklärung, um ein neues Kundenkonto zu erstellen");
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Prüft, ob das Passwortfeld weiterhin als required markiert ist
    public boolean isPasswordFieldRequired() {
        WebElement password = driver.findElement(passwordField);
        return password.getAttribute("required") != null;
    }

    // Prüft, ob das E-Mail-Feld weiterhin als required markiert ist
    public boolean isEmailFieldRequired() {
        WebElement email = driver.findElement(emailField);
        return email.getAttribute("required") != null;
    }
}