// Define the package where this test class belongs
package uk.co.twoitesting.twoitesting.test;

// Import JUnit assertion and test annotations
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

// Import base test class containing setup and teardown
import uk.co.twoitesting.twoitesting.basetests.BaseTests;

// Import POM classes for page interactions
import uk.co.twoitesting.twoitesting.pomclasses.*;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;

// Import helper utilities for screenshots
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

// Define the TestCase2 class, extending BaseTests for setup/teardown
public class TestCase2 extends BaseTests {

    @Test
    @Tag("RunMe")
    void testCompletePurchase() {
        // LOGIN
        loginPOM.open();
        loginPOM.login();
        Helpers.takeScreenshot(driver, "Login Success");

        // Assertion: user logged in
        Assertions.assertTrue(loginPOM.isUserLoggedIn(),
                "User should be logged in after login");

        // CLEAN CART
        navPOM.goToCart();
        cartPOM.removeProduct();

        // ADD PRODUCT
        navPOM.goToShop();
        shopPOM.dismissPopupIfPresent();
        shopPOM.addProductToCart("Polo");
        navPOM.goToCart();
        Helpers.takeScreenshot(driver, "Cart Ready");

        // CHECKOUT
        navPOM.goToCheckout();
        checkoutPOM.fillBillingDetailsFromConfig();
        Helpers.takeScreenshot(driver, "Billing Details Entered");
        checkoutPOM.selectCheckPayments();
        checkoutPOM.placeOrder();

        // CAPTURE ORDER NUMBER
        String orderNumber = checkoutPOM.captureOrderNumber();
        System.out.println("Captured Order Number: " + orderNumber);
        Helpers.takeScreenshot(driver, "Order Placed - " + orderNumber);

        // VERIFY ORDER
        assertThat("Order " + orderNumber + " should appear in My Account -> Orders",
                ordersPOM.isOrderPresent(orderNumber), is(true));

        // CLEANUP
        cartPOM.removeProduct();
        Helpers.takeScreenshot(driver, "Cart Emptied");

        accountPOM.logout();
        Helpers.takeScreenshot(driver, "Logged Out");
    }
}
