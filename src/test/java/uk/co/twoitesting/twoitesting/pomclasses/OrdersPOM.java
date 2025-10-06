// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses;

// Import Selenium tools for finding elements, interacting with them, and waiting
import org.openqa.selenium.By; // Used to locate elements on the webpage
import org.openqa.selenium.WebDriver; // Lets us control the browser
import org.openqa.selenium.WebElement; // Represents an element on the page
import org.openqa.selenium.support.ui.ExpectedConditions; // Conditions to wait for
import org.openqa.selenium.support.ui.WebDriverWait; // Wait until a condition is true

// Import NavPOM for navigation actions and Helpers for utility functions
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.util.List; // To handle lists of web elements

// Define OrdersPOM class to handle actions on the "My Orders" page
public class OrdersPOM extends BasePOM { // Inherits useful functions from BasePOM

    private final NavPOM navPOM; // Reference to navigation bar POM
    // Locator for the "Orders" link on My Account page
    private final By ordersLink = By.cssSelector(
            "#post-7 > div > div > nav > ul > li.woocommerce-MyAccount-navigation-link--orders > a");
    // Locator for all order numbers in the orders table
    private final By orderNumbersLocator = By.cssSelector("td.woocommerce-orders-table__cell-order-number a");

    // Constructor for OrdersPOM
    public OrdersPOM(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        super(driver, wait); // Call BasePOM constructor
        this.navPOM = navPOM; // Save reference to NavPOM
    }

    // Check if a specific order number exists on the "My Orders" page
    public boolean isOrderPresent(String orderNumber) {
        try {
            // Go to My Account page
            navPOM.goToAccount();

            // Click the "Orders" link to open orders page
            click(ordersLink);

            // Wait until at least one order is visible
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(orderNumbersLocator, 0));

            // Get all the order number elements
            List<WebElement> orders = driver.findElements(orderNumbersLocator);

            // Print all order numbers to the console for debugging
            System.out.println("Orders found on My Orders page:");
            orders.forEach(e -> System.out.println(" - " + e.getText().trim()));

            // Check if the given orderNumber exists in the list
            boolean found = orders.stream()
                    .map(e -> e.getText().trim().replace("#", "")) // Clean up text
                    .anyMatch(text -> text.equals(orderNumber)); // Check if it matches

            // Print result to console
            if (found) {
                System.out.println("Order number " + orderNumber + " successfully found!");
            } else {
                System.out.println("Order number " + orderNumber + " not found in My Orders.");
                Helpers.takeScreenshot(driver, "OrderNotFound"); // Take screenshot if not found
            }

            return found; // Return true if found, false if not

        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "VerifyOrderError"); // Screenshot on any error
            System.out.println("Error verifying order: " + e.getMessage());
            return false; // Return false if there was an error
        }
    }
}
