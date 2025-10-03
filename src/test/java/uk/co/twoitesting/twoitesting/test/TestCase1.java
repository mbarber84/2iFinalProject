package uk.co.twoitesting.twoitesting.test;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.co.twoitesting.twoitesting.basetests.BaseTests;
import uk.co.twoitesting.twoitesting.pomclasses.*;
import uk.co.twoitesting.twoitesting.utilities.CouponData;
import uk.co.twoitesting.twoitesting.utilities.CsvCouponLoader;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

public class TestCase1 extends BaseTests {

    // Data provider method
    static Stream<TestData> dataProvider() {
        List<String> products = List.of("Polo");
        List<CouponData> coupons = CsvCouponLoader.loadCoupons("src/test/resources/coupons.csv");

        return coupons.stream()
                .flatMap(coupon -> products.stream()
                        .map(product -> new TestData(coupon, product))
                );
    }

    @Epic("Shop Tests")
    @Feature("Cart and Discount")
    @Story("Purchase Items with Discounts")
    @Tag("RunMe")
    @ParameterizedTest(name = "Test {0}")
    @MethodSource("dataProvider")
    void testPurchaseWithDiscount(TestData data) {

        Allure.step("Login to site and clean cart", () -> {
            loginPOM.open();
            loginPOM.login();
            Helpers.takeScreenshot(driver, "Login Success");
            Assertions.assertTrue(loginPOM.isUserLoggedIn());

            navPOM.goToCart();
            cartPOM.removeCoupon(data.coupon.code());
            cartPOM.removeProduct();
        });

        Allure.step("Add product and apply discount", () -> {
            navPOM.goToShop();
            shopPOM.dismissPopupIfPresent();
            shopPOM.addProductToCart(data.product);
            navPOM.goToCart();
            popUpPOM.dismissPopupIfPresent();

            Helpers.takeScreenshot(driver,
                    "Cart Before Applying " + data.coupon.key() + " for " + data.product);

            System.out.println("Testing " + data.product +
                    " with discount: " + data.coupon.code() +
                    " (" + (data.coupon.discount() * 100) + "%)");

            cartPOM.applyCoupon(data.coupon.code());

            // Fetch cart totals
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

            // Only assert discount if expectedDiscount > 0
            if (expectedDiscount.compareTo(BigDecimal.ZERO) > 0) {
                Assertions.assertEquals(0, expectedDiscount.compareTo(discount),
                        "Discount should match expected");
            } else {
                System.out.println("Expected discount is 0, actual discount: " + discount);
            }

            Assertions.assertEquals(0, expectedTotal.compareTo(total), "Total should match expected");

            System.out.printf("Subtotal: £%.2f | Discount: £%.2f | Total: £%.2f%n",
                    subtotal, discount, total);

            // Clean up cart
            cartPOM.removeCoupon(data.coupon.code());
            cartPOM.removeProduct();

            Assertions.assertEquals(0, cartPOM.getCartItemCount(), "Cart should be empty after cleanup");
        });
    }

    // Inner record class to hold test data
    record TestData(CouponData coupon, String product) {
        @Override
        public String toString() {
            return product + " with " + coupon.key();
        }
    }
}
