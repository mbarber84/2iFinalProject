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

// Define CheckoutPOM to interact with the checkout page
public class CheckoutPOM extends BasePOM { // Inherits common functions from BasePOM

    private final NavPOM navPOM; // Reference to navigation bar POM
    private boolean billingFilled = false; // Track if billing details are already filled

    // Locate all important fields and buttons on the checkout page
    private final By firstNameField = By.id("billing_first_name");
    private final By lastNameField = By.id("billing_last_name");
    private final By addressField = By.id("billing_address_1");
    private final By cityField = By.id("billing_city");
    private final By postcodeField = By.id("billing_postcode");
    private final By emailField = By.id("billing_email");
    private final By phoneField = By.id("billing_phone");
    private final By paymentCheck = By.id("payment_method_cheque");
    private final By placeOrderButton = By.id("place_order");
    private final By orderNumberLocator = By.cssSelector(".order > strong");

    // Constructor for CheckoutPOM
    public CheckoutPOM(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        super(driver, wait); // Call BasePOM constructor
        this.navPOM = navPOM; // Save reference to NavPOM
    }

    // Fill billing details from a config file
    public boolean fillBillingDetailsFromConfig() {
        if (billingFilled) return true; // Skip if already filled
        try {
            type(firstNameField, ConfigLoader.get("billing.firstname")); // Type first name
            type(lastNameField, ConfigLoader.get("billing.lastname")); // Type last name
            type(addressField, ConfigLoader.get("billing.address")); // Type address
            type(cityField, ConfigLoader.get("billing.city")); // Type city
            type(postcodeField, ConfigLoader.get("billing.postcode")); // Type postcode
            type(emailField, ConfigLoader.get("billing.email")); // Type email
            type(phoneField, ConfigLoader.get("billing.phone")); // Type phone
            billingFilled = true; // Mark as filled
            return true; // Success
        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "BillingError"); // Take screenshot if error
            return false; // Failed to fill billing
        }
    }

    // Select "Check Payments" method
    public void selectCheckPayments() {
        try {
            if (!waitAndFind(paymentCheck).isSelected()) { // Check if already selected
                click(paymentCheck); // Click to select it
            }
        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "PaymentError"); // Screenshot if error
        }
    }

    // Click the "Place Order" button
    public void placeOrder() {
        try {
            click(placeOrderButton); // Click button
        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "PlaceOrderError"); // Screenshot if error
        }
    }

    // Capture and return the order number after placing the order
    public String captureOrderNumber() {
        try {
            WebElement orderNumberElem = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(orderNumberLocator)); // Wait for order number

            String orderNumber = orderNumberElem.getText().trim().replace("#", ""); // Get text and clean it
            System.out.println("Captured Order Number: " + orderNumber); // Print order number

            Helpers.takeScreenshot(driver, "Order Placed - " + orderNumber); // Take screenshot
            return orderNumber; // Return the order number

        } catch (Exception e) {
            System.out.println("Capturing order number failed: " + e.getMessage()); // Print error
            Helpers.takeScreenshot(driver, "OrderNumberError"); // Take screenshot on error
            return ""; // Return empty string if failed
        }
    }
}
