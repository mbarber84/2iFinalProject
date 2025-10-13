// This is the package where this file lives. Think of it like a folder for your code.
package uk.co.twoitesting.twoitesting.basetests;

// Importing tools we need from other places to use in this file.
import org.junit.jupiter.api.AfterEach; // Runs after each test
import org.junit.jupiter.api.BeforeEach; // Runs before each test
import org.openqa.selenium.WebDriver; // Lets us control a browser
import org.openqa.selenium.support.ui.WebDriverWait; // Waits for things to load on a page
import uk.co.twoitesting.twoitesting.pomclasses.*; // Import all main POM classes (like login, shop, cart)
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM; // Navigation bar POM
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.PopUpPOM; // Pop-up POM
import uk.co.twoitesting.twoitesting.utilities.Helpers; // Some helper functions we wrote

import java.time.Duration; // To define how long we wait

// This is the main class for all our tests. Other test classes can use it to share setup and teardown code.
public class BaseTests {

    // This is our browser driver. It talks to Chrome, Firefox, etc.
    protected WebDriver driver;
    // This is used to wait for things to appear on the page before we try to click them.
    protected WebDriverWait wait;

    // These are all the Page Object Model classes we use to interact with the website.
    protected LoginPOM loginPOM;
    protected ShopPOM shopPOM;
    protected CartPOM cartPOM;
    protected AccountPOM accountPOM;
    protected NavPOM navPOM;
    protected PopUpPOM popUpPOM;
    protected CheckoutPOM checkoutPOM;
    protected OrdersPOM ordersPOM;
    protected Helpers helpers;

    // This method runs before each test. It sets up the browser and all the pages we need.
    @BeforeEach
    void setUpBase() {
        // Get the browser name from system properties. Default to "chrome" if nothing is provided.
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        // Open the right browser based on what was passed in
        switch (browser) {
            case "firefox":
                driver = new org.openqa.selenium.firefox.FirefoxDriver(); // Open Firefox
                break;
            case "chrome":
            default:
                driver = new org.openqa.selenium.chrome.ChromeDriver(); // Open Chrome
                break;
        }

        driver.manage().window().maximize(); // Make the browser full screen
        wait = new WebDriverWait(driver, Duration.ofSeconds(3)); // Wait up to 7 seconds for things to appear
        helpers = new Helpers(); // Create a helpers object to use utility functions

        // Initialize basic components first (these are independent)
        popUpPOM = new PopUpPOM(driver, wait); // Pop-up handling
        navPOM = new NavPOM(driver, wait); // Navigation bar

        // Initialize the other pages (these might need navPOM)
        loginPOM = new LoginPOM(driver, wait); // Login page
        shopPOM = new ShopPOM(driver, wait); // Shop page
        cartPOM = new CartPOM(driver, wait); // Cart page
        accountPOM = new AccountPOM(driver, wait, navPOM); // Account page
        checkoutPOM = new CheckoutPOM(driver, wait, navPOM); // Checkout page
        ordersPOM = new OrdersPOM(driver, wait, navPOM); // Orders page
    }

    // This method runs after each test. It cleans up everything.
    @AfterEach
    void tearDownBase() {
        Helpers.takeScreenshot(driver, "FinalState"); // Take a screenshot of the final state
        emptyCart(); // Remove anything left in the cart
        accountPOM.logout(); // Log out from the account
        driver.quit(); // Close the browser completely
    }

    // This method empties the cart. Useful to make sure tests start fresh.
    protected void emptyCart() {
        try {
            // Go to the cart page
            driver.get(System.getProperty("base.url", "https://www.edgewordstraining.co.uk/demo-site") + "/cart/");
            // Remove coupons if any
            cartPOM.removeCoupon("edgewords");
            cartPOM.removeCoupon("2idiscount");
            // Remove any products in the cart
            cartPOM.removeProduct();
            System.out.println("Cart emptied successfully."); // Let us know it worked
        } catch (Exception e) {
            // If something goes wrong (like cart is already empty), just print a message
            System.out.println("Cart already empty or error: " + e.getMessage());
        }
    }
}
