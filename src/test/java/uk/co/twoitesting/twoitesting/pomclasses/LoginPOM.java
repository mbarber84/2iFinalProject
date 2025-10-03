// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses;

// Import Selenium classes for interacting with web elements and handling waits

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;
import uk.co.twoitesting.twoitesting.utilities.*;

// Define the LoginPOM class for performing actions on the login page
public class LoginPOM extends BasePOM {

    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.name("login");
    private final By logoutButton = By.linkText("Logout");

    public LoginPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.get(ConfigLoader.get("base.url") + "/my-account/");
    }

    public void login() {
        type(usernameField, ConfigLoader.get("username"));
        type(passwordField, ConfigLoader.get("password"));
        click(loginButton);
    }

    public boolean isUserLoggedIn() {
        return isVisible(logoutButton);
    }

    public void logout() {
        if (isUserLoggedIn()) {
            click(logoutButton);
        }
    }
}
