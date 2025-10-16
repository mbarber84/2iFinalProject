package uk.co.twoitesting.twoitesting.steps;

import io.qameta.allure.Step;
import uk.co.twoitesting.twoitesting.pomclasses.*;
import uk.co.twoitesting.twoitesting.utilities.Helpers;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CouponSteps {

    private final LoginPOM loginPOM;
    private final NavPOM navPOM;
    private final ShopPOM shopPOM;
    private final CartPOM cartPOM;
    private final PopUpPOM popUpPOM;

    public CouponSteps(LoginPOM loginPOM, NavPOM navPOM, ShopPOM shopPOM,
                       CartPOM cartPOM, PopUpPOM popUpPOM) {
        this.loginPOM = loginPOM;
        this.navPOM = navPOM;
        this.shopPOM = shopPOM;
        this.cartPOM = cartPOM;
        this.popUpPOM = popUpPOM;
    }

    @Step("GIVEN the user is logged in and cart is empty")
    public void loginAndCleanCart() {
        loginPOM.open();
        loginPOM.login();
        Helpers.takeScreenshot(loginPOM.getDriver(), "Login Success");

        if (!loginPOM.isUserLoggedIn()) {
            throw new RuntimeException("User should be logged in");
        }

        navPOM.goToCart();
        cartPOM.removeProduct();
    }

    @Step("WHEN the product '{productName}' is added to the cart with coupon '{couponCode}'")
    public void addProductAndApplyDiscount(String productName, String couponCode) {
        navPOM.goToShop();
        shopPOM.dismissPopupIfPresent();
        shopPOM.addProductToCart(productName);

        navPOM.goToCart();
        popUpPOM.dismissPopupIfPresent();
        Helpers.takeScreenshot(cartPOM.getDriver(), "Cart Before Applying Coupon: " + couponCode);

        cartPOM.applyCoupon(couponCode);
    }

    @Step("THEN the cart totals should reflect the applied discount")
    public void verifyCartTotals(String productName, double discountPercent) {
        BigDecimal subtotal = cartPOM.getSubtotalBD();
        BigDecimal discount = cartPOM.getDiscountBD();
        BigDecimal shipping = cartPOM.getShippingBD();
        BigDecimal total = cartPOM.getTotalBD();

        BigDecimal expectedDiscount = subtotal
                .multiply(BigDecimal.valueOf(discountPercent))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTotal = subtotal.subtract(expectedDiscount)
                .add(shipping)
                .setScale(2, RoundingMode.HALF_UP);

        if (expectedDiscount.compareTo(BigDecimal.ZERO) > 0) {
            if (expectedDiscount.compareTo(discount) != 0) {
                throw new AssertionError("Discount should match expected");
            }
        }

        if (expectedTotal.compareTo(total) != 0) {
            throw new AssertionError("Total should match expected");
        }

        System.out.printf("Subtotal: £%.2f | Discount: £%.2f | Total: £%.2f%n",
                subtotal, discount, total);
    }

    @Step("THEN the cart is cleaned after test")
    public void cleanupCart(String couponCode) {
        cartPOM.removeCoupon(couponCode);
        cartPOM.removeProduct();

        if (cartPOM.getCartItemCount() != 0) {
            throw new AssertionError("Cart should be empty after cleanup");
        }
    }
}

