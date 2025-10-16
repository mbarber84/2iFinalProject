package uk.co.twoitesting.twoitesting.steps;

import io.qameta.allure.Step;
import uk.co.twoitesting.twoitesting.pomclasses.CartPOM;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

public class CartSteps {

    private final CartPOM cartPOM;
    private final NavPOM navPOM;


    public CartSteps(CartPOM cartPOM, NavPOM navPOM) {
        this.cartPOM = cartPOM;
        this.navPOM = navPOM;
    }

    @Step("Empty the cart")
    public void emptyCart() {
        cartPOM.removeProduct();
        cartPOM.removeCoupon("edgewords");
        cartPOM.removeCoupon("2idiscount");
        Helpers.takeScreenshot(cartPOM.getDriver(), "Cart Emptied");
    }

    @Step("Apply coupon '{couponCode}'")
    public void applyCoupon(String couponCode) {
        cartPOM.applyCoupon(couponCode);
        Helpers.takeScreenshot(cartPOM.getDriver(), "Applied Coupon " + couponCode);
    }

    @Step("Proceed to checkout")
    public void goToCheckout() {
        navPOM.goToCheckout();
        Helpers.takeScreenshot(cartPOM.getDriver(), "Navigated to Checkout");
    }
}
