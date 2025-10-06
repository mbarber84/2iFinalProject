// Define the folder (package) where this class is stored
package uk.co.twoitesting.twoitesting.pomclasses;

// Import tools from Selenium to find elements and control the browser
import org.openqa.selenium.By; // Lets us locate elements on a webpage
import org.openqa.selenium.WebDriver; // Lets us control the browser
import org.openqa.selenium.support.ui.WebDriverWait; // Lets us wait for things to appear

// Import NavPOM in case we need it for navigation (not used directly here)
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;

// Define the AccountPOM class to handle account-related actions (like logout)
public class AccountPOM extends BasePOM { // Inherits common functions from BasePOM

    // Locate the "Logout" link on the account page
    private final By logoutLink = By.linkText("Logout");

    // Constructor for AccountPOM. Runs when we create a new AccountPOM object
    public AccountPOM(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        super(driver, wait); // Call BasePOM constructor to set up driver and wait
    }

    // Log out from the account if the logout link is visible
    public void logout() {
        if (isVisible(logoutLink)) { // Check if the "Logout" link is on the page
            click(logoutLink); // Click the "Logout" link to log out
        }
    }
}
