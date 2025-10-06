// Define the folder (package) where this class is stored
package uk.co.twoitesting.twoitesting.pomclasses.componentPOM;

// Import tools from Selenium to control the browser and wait for things to appear
import org.openqa.selenium.By; // Used to locate elements on the webpage
import org.openqa.selenium.WebDriver; // Lets us control the browser
import org.openqa.selenium.support.ui.ExpectedConditions; // Helps us wait for things
import org.openqa.selenium.support.ui.WebDriverWait; // Waits for things to appear on the page

// Import our own BasePOM class, which has common functions for all pages
import uk.co.twoitesting.twoitesting.pomclasses.BasePOM;

// Define the NavPOM class, which is used to interact with the website's navigation bar
public class NavPOM extends BasePOM { // "extends BasePOM" means it inherits common functions from BasePOM

    // Define the links in the navigation bar we want to click
    private final By shopLink = By.linkText("Shop"); // Finds the "Shop" link
    private final By cartLink = By.linkText("Cart"); // Finds the "Cart" link
    private final By checkoutLink = By.linkText("Checkout"); // Finds the "Checkout" link
    private final By accountLink = By.linkText("My account"); // Finds the "My account" link

    // Constructor for NavPOM. Runs when we create a new NavPOM object
    public NavPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait); // Call the constructor of BasePOM to set up the driver and wait
    }

    // Go to the Shop page by clicking the Shop link
    public void goToShop() {
        click(shopLink); // click() is a function from BasePOM that clicks an element
    }

    // Go to the Cart page by clicking the Cart link
    public void goToCart() {
        click(cartLink);
    }

    // Go to the Checkout page by clicking the Checkout link
    public void goToCheckout() {
        click(checkoutLink);
    }

    // Go to the Account page by clicking the My Account link
    public void goToAccount() {
        click(accountLink);
    }
}
