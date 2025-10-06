// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses;

// Import Selenium tools to interact with elements and wait for them
import org.openqa.selenium.By; // Lets us find elements on the webpage
import org.openqa.selenium.WebDriver; // Lets us control the browser
import org.openqa.selenium.support.ui.ExpectedConditions; // Conditions to wait for
import org.openqa.selenium.support.ui.WebDriverWait; // Wait until conditions are met

// Import NavPOM in case we need navigation tools (not directly used here)
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;
// Import helpers for config and utilities
import uk.co.twoitesting.twoitesting.utilities.*;

// Define the LoginPOM class to interact with the login page
public class LoginPOM extends BasePOM { // Inherits useful functions from BasePOM

    // Locate the username and password fields and the login/logout buttons
    private final By usernameField = By.id("username"); // Username input field
    private final By passwordField = By.id("password"); // Password input field
    private final By loginButton = By.name("login"); // Login button
    private final By logoutButton = By.linkText("Logout"); // Logout link

    // Constructor for LoginPOM
    public LoginPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait); // Call BasePOM constructor
    }

    // Open the login page
    public void open() {
        driver.get(ConfigLoader.get("base.url") + "/my-account/");
        // Go to the my-account page using URL from config
    }

    // Perform login using credentials from config
    public void login() {
        type(usernameField, ConfigLoader.get("username")); // Type username
        type(passwordField, ConfigLoader.get("password")); // Type password
        click(loginButton); // Click login button
    }

    // Check if the user is currently logged in
    public boolean isUserLoggedIn() {
        return isVisible(logoutButton); // If logout button is visible, user is logged in
    }

    // Log out if user is logged in
    public void logout() {
        if (isUserLoggedIn()) { // Check if user is logged in
            click(logoutButton); // Click logout button
        }
    }
}
