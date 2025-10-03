// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses;

// Import Selenium classes for interacting with web elements

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;

// Define the AccountPOM class which handles account-related actions (e.g., logout)
public class AccountPOM extends BasePOM {

    private final By logoutLink = By.linkText("Logout");

    public AccountPOM(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        super(driver, wait);
    }

    public void logout() {
        if (isVisible(logoutLink)) {
            click(logoutLink);
        }
    }
}
