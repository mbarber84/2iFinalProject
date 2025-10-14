package uk.co.twoitesting.twoitesting.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.utilities.ConfigLoader;
import uk.co.twoitesting.twoitesting.utilities.Helpers;
import uk.co.twoitesting.twoitesting.pomclasses.BasePOM;

public class LoginActions extends BasePOM {

    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.name("login");
    private final By logoutButton = By.linkText("Logout");

    // Constructor
    public LoginActions(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /** Open login page */
    public void openLoginPage() {
        driver.get(ConfigLoader.get("base.url") + "/my-account/");
    }

    /** Perform login using config credentials */
    public void login() {
        type(usernameField, ConfigLoader.get("username"));
        type(passwordField, ConfigLoader.get("password"));
        click(loginButton);
    }

    /** Check if user is logged in */
    public boolean isUserLoggedIn() {
        return isVisible(logoutButton);
    }

    /** Log out user if logged in */
    public void logout() {
        if (isUserLoggedIn()) {
            click(logoutButton);
        }
    }
}
