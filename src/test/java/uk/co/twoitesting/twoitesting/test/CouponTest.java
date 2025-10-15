package uk.co.twoitesting.twoitesting.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import uk.co.twoitesting.twoitesting.basetests.BaseTests;
import uk.co.twoitesting.twoitesting.utilities.CouponData;
import uk.co.twoitesting.twoitesting.utilities.CsvCouponLoader;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;



@Epic("Shop Tests")
@Feature("Cart and Discount")
@Story("Purchase Items with Discounts")
public class CouponTest extends BaseTests {

    // Provide test data
    static Stream<TestData> dataProvider() {
        List<String> products = List.of("Polo");
        List<CouponData> coupons = CsvCouponLoader.loadCoupons("src/test/resources/coupons.csv");

        return coupons.stream()
                .flatMap(coupon -> products.stream()
                        .map(product -> new TestData(coupon, product)));
    }

    @Tag("RunMe")
    @ParameterizedTest(name = "{0}")
    @MethodSource("dataProvider")
    void testPurchaseWithDiscount(TestData data) {

        loginAndCleanCart();
        addProductAndApplyDiscount(data);
        verifyCartTotals(data);
        cleanupCart(data);
    }

    //BDD Steps

    @Step("GIVEN the user is logged in and cart is empty")
    protected void loginAndCleanCart() {
        loginPOM.open();
        loginPOM.login();
        Helpers.takeScreenshot(driver, "Login Success");
        Assertions.assertTrue(loginPOM.isUserLoggedIn(), "User should be logged in");

        navPOM.goToCart();
        cartPOM.removeProduct();
    }

    @Step("WHEN the product '{data.product}' is added to the cart with coupon '{data.coupon.code}'")
    protected void addProductAndApplyDiscount(TestData data) {
        navPOM.goToShop();
        shopPOM.dismissPopupIfPresent();
        shopPOM.addProductToCart(data.product);

        navPOM.goToCart();
        popUpPOM.dismissPopupIfPresent();
        Helpers.takeScreenshot(driver,
                "Cart Before Applying " + data.coupon.key() + " for " + data.product);

        cartPOM.applyCoupon(data.coupon.code());
    }

    @Step("THEN the cart totals should reflect the applied discount for '{data.product}'")
    protected void verifyCartTotals(TestData data) {
        BigDecimal subtotal = cartPOM.getSubtotalBD();
        BigDecimal discount = cartPOM.getDiscountBD();
        BigDecimal shipping = cartPOM.getShippingBD();
        BigDecimal total = cartPOM.getTotalBD();

        BigDecimal expectedDiscount = subtotal
                .multiply(BigDecimal.valueOf(data.coupon.discount()))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTotal = subtotal.subtract(expectedDiscount)
                .add(shipping)
                .setScale(2, RoundingMode.HALF_UP);

        if (expectedDiscount.compareTo(BigDecimal.ZERO) > 0) {
            Assertions.assertEquals(0, expectedDiscount.compareTo(discount), "Discount should match expected");
        } else {
            System.out.println("Expected discount is 0, actual discount: " + discount);
        }

        Assertions.assertEquals(0, expectedTotal.compareTo(total), "Total should match expected");

        System.out.printf("Subtotal: £%.2f | Discount: £%.2f | Total: £%.2f%n",
                subtotal, discount, total);
    }

    @Step("AND the cart is cleaned after test")
    protected void cleanupCart(TestData data) {
        cartPOM.removeCoupon(data.coupon.code());
        cartPOM.removeProduct();
        Assertions.assertEquals(0, cartPOM.getCartItemCount(), "Cart should be empty after cleanup");
    }

    // Inner record for test data
    record TestData(CouponData coupon, String product) {
        @Override
        public String toString() {
            return product + " with " + coupon.key();
        }
    }
}
