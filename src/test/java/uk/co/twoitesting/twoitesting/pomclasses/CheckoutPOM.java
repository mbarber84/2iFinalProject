// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses;

// Import Selenium tools to interact with elements and wait for them
import org.openqa.selenium.By; // Lets us find elements on the page
import org.openqa.selenium.WebDriver; // Lets us control the browser
import org.openqa.selenium.WebElement; // Represents an element on the page
import org.openqa.selenium.support.ui.ExpectedConditions; // Conditions to wait for
import org.openqa.selenium.support.ui.WebDriverWait; // Wait until conditions are met

// Import NavPOM for navigation bar interactions
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;
// Import utility helpers for screenshots and config
import uk.co.twoitesting.twoitesting.utilities.*;
import uk.co.twoitesting.twoitesting.actions.CheckoutActions;

// Define CheckoutPOM to interact with the checkout page
public class CheckoutPOM extends BasePOM {
    private final NavPOM navPOM;
    private final CheckoutActions checkoutActions;

    public CheckoutPOM(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        super(driver, wait);
        this.navPOM = navPOM;
        this.checkoutActions = new CheckoutActions(driver, wait);
    }

    public boolean fillBillingDetailsFromConfig() {
        return checkoutActions.fillBillingDetailsFromConfig();
    }

    public void selectCheckPayments() {
        checkoutActions.selectCheckPayments();
    }

    public void placeOrder() {
        checkoutActions.placeOrder();
    }

    public String captureOrderNumber() {
        return checkoutActions.captureOrderNumber();
    }
}

