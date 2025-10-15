package uk.co.twoitesting.twoitesting.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uk.co.twoitesting.twoitesting.basetests.BaseTests;
import uk.co.twoitesting.twoitesting.utilities.Helpers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FullPurchaseFlowTest extends BaseTests {

    @Test
    @Tag("RunMe")
    @Epic("Shop Tests")
    @Feature("End-to-End Purchase Flow")
    @Story("User can complete a full purchase and verify order")
    void testCompletePurchase() {

        // GIVEN: User is logged in and cart is empty
        loginUser();
        verifyUserLoggedIn();
        emptyCart();

        // WHEN: User adds product to cart and proceeds to checkout
        addProductToCart("Polo");
        proceedToCheckout();

        // THEN: Order is captured successfully and visible in My Account
        String orderNumber = captureOrderNumber();
        verifyOrderInAccount(orderNumber);

        // CLEANUP
        cleanup(orderNumber);
    }

    // BDD STEPS

    @Step("GIVEN the user logs in")
    void loginUser() {
        loginPOM.open();
        loginPOM.login();
        Helpers.takeScreenshot(driver, "Login Success");
    }

    @Step("AND the user is logged in")
    void verifyUserLoggedIn() {
        assertThat("User should be logged in", loginPOM.isUserLoggedIn(), is(true));
    }

    @Step("AND the cart is emptied")
    protected void emptyCart() {
        navPOM.goToCart();
        cartPOM.removeProduct();
        Helpers.takeScreenshot(driver, "Cart Emptied Before Test");
    }

    @Step("WHEN the user adds '{productName}' to the cart")
    void addProductToCart(String productName) {
        navPOM.goToShop();
        shopPOM.dismissPopupIfPresent();
        shopPOM.addProductToCart(productName);
        navPOM.goToCart();
        Helpers.takeScreenshot(driver, "Cart Ready");
    }

    @Step("AND proceeds to checkout")
    void proceedToCheckout() {
        navPOM.goToCheckout();
        checkoutPOM.fillBillingDetailsFromConfig();
        Helpers.takeScreenshot(driver, "Billing Details Entered");
        checkoutPOM.selectCheckPayments();
        checkoutPOM.placeOrder();
    }

    @Step("THEN capture the order number")
    String captureOrderNumber() {
        String orderNumber = checkoutPOM.captureOrderNumber();
        Helpers.takeScreenshot(driver, "Order Placed - " + orderNumber);
        return orderNumber;
    }

    @Step("AND verify the order '{orderNumber}' is present in My Account")
    void verifyOrderInAccount(String orderNumber) {
        assertThat("Order should appear in My Account -> Orders",
                ordersPOM.isOrderPresent(orderNumber), is(true));
    }

    @Step("CLEANUP: Empty cart and log out user after test")
    void cleanup(String orderNumber) {
        cartPOM.removeProduct();
        Helpers.takeScreenshot(driver, "Cart Emptied After Test");
        accountPOM.logout();
        Helpers.takeScreenshot(driver, "Logged Out");
    }
}
