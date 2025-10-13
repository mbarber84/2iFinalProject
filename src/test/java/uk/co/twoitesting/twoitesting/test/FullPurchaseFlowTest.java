// This is the folder (package) where this test class is stored
package uk.co.twoitesting.twoitesting.test;

// Import JUnit tools for testing and assertions
import org.junit.jupiter.api.Assertions; // To check if something is true
import org.junit.jupiter.api.Tag; // To tag tests
import org.junit.jupiter.api.Test; // To mark a method as a test
import uk.co.twoitesting.twoitesting.basetests.BaseTests;
import uk.co.twoitesting.twoitesting.utilities.Helpers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

// Define TestCase2 class that extends BaseTests
public class FullPurchaseFlowTest extends BaseTests {

    @Test // Marks this method as a test
    @Tag("RunMe") // Tag the test
    void testCompletePurchase() {

        // LOGIN

        loginPOM.open(); // Open login page
        loginPOM.login(); // Login with credentials from config
        Helpers.takeScreenshot(driver, "Login Success"); // Take a screenshot

        // Verify login was successful
        Assertions.assertTrue(loginPOM.isUserLoggedIn(),
                "User should be logged in after login");

        // CLEAN CART

        navPOM.goToCart(); // Go to cart page
        cartPOM.removeProduct(); // Remove any existing products

        // ADD PRODUCT

        navPOM.goToShop(); // Go to shop page
        shopPOM.dismissPopupIfPresent(); // Close any popup if it appears
        shopPOM.addProductToCart("Polo"); // Add "Polo" shirt to cart
        navPOM.goToCart(); // Go back to cart
        Helpers.takeScreenshot(driver, "Cart Ready"); // Take a screenshot

        // CHECKOUT

        navPOM.goToCheckout(); // Go to checkout page
        checkoutPOM.fillBillingDetailsFromConfig(); // Fill in billing info from config
        Helpers.takeScreenshot(driver, "Billing Details Entered"); // Screenshot
        checkoutPOM.selectCheckPayments(); // Choose "Check" as payment method
        checkoutPOM.placeOrder(); // Place the order

        // CAPTURE ORDER NUMBER

        String orderNumber = checkoutPOM.captureOrderNumber(); // Get order number
        System.out.println("Captured Order Number: " + orderNumber); // Print it
        Helpers.takeScreenshot(driver, "Order Placed - " + orderNumber); // Screenshot

        // VERIFY ORDER

        assertThat("Order " + orderNumber + " should appear in My Account -> Orders",
                ordersPOM.isOrderPresent(orderNumber), is(true)); // Check if order exists

        // CLEANUP

        cartPOM.removeProduct(); // Empty the cart
        Helpers.takeScreenshot(driver, "Cart Emptied"); // Screenshot

        accountPOM.logout(); // Logout user
        Helpers.takeScreenshot(driver, "Logged Out"); // Screenshot
    }
}
