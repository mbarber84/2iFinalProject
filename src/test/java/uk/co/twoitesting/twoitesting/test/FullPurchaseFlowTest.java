package uk.co.twoitesting.twoitesting.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uk.co.twoitesting.twoitesting.basetests.BaseTests;
import uk.co.twoitesting.twoitesting.steps.*;

public class FullPurchaseFlowTest extends BaseTests {

    private LoginSteps loginSteps;
    private ShopSteps shopSteps;
    private CartSteps cartSteps;
    private CheckoutSteps checkoutSteps;
    private OrdersSteps ordersSteps;

    @BeforeEach
    void initSteps() {
        // POMs are initialized in BaseTests @BeforeEach already
        loginSteps = new LoginSteps(loginPOM, accountPOM);
        shopSteps = new ShopSteps(shopPOM, popUpPOM);
        cartSteps = new CartSteps(cartPOM, navPOM);
        checkoutSteps = new CheckoutSteps(checkoutPOM);
        ordersSteps = new OrdersSteps(ordersPOM);
    }

    @Test
    @Tag("RunMe")
    void testCompletePurchase() {
        loginSteps.loginUser();
        loginSteps.verifyUserLoggedIn();

        cartSteps.emptyCart();

        shopSteps.openShopAndDismissPopup();
        shopSteps.addProductToCart("Polo");

        cartSteps.goToCheckout();

        checkoutSteps.fillBillingDetails();
        checkoutSteps.selectCheckPayment();
        checkoutSteps.placeOrder();

        String orderNumber = checkoutSteps.captureOrderNumber();

        ordersSteps.verifyOrderPresent(orderNumber);

        cartSteps.emptyCart();
        loginSteps.logoutUser();
    }
}
