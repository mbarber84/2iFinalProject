// This is the folder (package) where this test class is stored
package uk.co.twoitesting.twoitesting.test;

// Import Allure annotations and tools for reporting
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

// Import JUnit tools for testing
import org.junit.jupiter.api.Assertions; // For checking if things are correct
import org.junit.jupiter.api.Tag; // For tagging tests
import org.junit.jupiter.params.ParameterizedTest; // For running the same test with multiple inputs
import org.junit.jupiter.params.provider.MethodSource; // To provide test data

// Import base test class and POMs
import uk.co.twoitesting.twoitesting.basetests.BaseTests;
import uk.co.twoitesting.twoitesting.utilities.CouponData;
import uk.co.twoitesting.twoitesting.utilities.CsvCouponLoader;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

// Define a test class for testing purchases with discounts
public class CouponTest extends BaseTests { // Inherits setup and teardown from BaseTests

    // This method provides the test data (products + coupons)
    static Stream<TestData> dataProvider() {
        List<String> products = List.of("Polo"); // List of products to test
        List<CouponData> coupons = CsvCouponLoader.loadCoupons("src/test/resources/coupons.csv"); // Load coupons from CSV

        // Combine each coupon with each product to create test cases
        return coupons.stream()
                .flatMap(coupon -> products.stream()
                        .map(product -> new TestData(coupon, product))
                );
    }

    // Define the test for purchasing a product with a discount
    @Epic("Shop Tests") // Allure Epic for high-level categorization
    @Feature("Cart and Discount") // Allure Feature
    @Story("Purchase Items with Discounts") // Allure Story
    @Tag("RunMe") // Tag for JUnit
    @ParameterizedTest(name = "Test {0}") // Use data from dataProvider
    @MethodSource("dataProvider") // Specify where the test data comes from
    void testPurchaseWithDiscount(TestData data) {

        // Step 1: Login and clean the cart
        //Allure.step("Login to site and clean cart", () -> {
            loginPOM.open(); // Open login page
            loginPOM.login(); // Login using credentials from config
            Helpers.takeScreenshot(driver, "Login Success"); // Take screenshot
            Assertions.assertTrue(loginPOM.isUserLoggedIn()); // Check login was successful

            navPOM.goToCart(); // Go to cart
            cartPOM.removeCoupon(data.coupon.code()); // Remove any existing coupon
            cartPOM.removeProduct(); // Remove any existing products
        //});

        // Step 2: Add product and apply discount
        //Allure.step("Add product and apply discount", () -> {
            navPOM.goToShop(); // Go to Shop page
            shopPOM.dismissPopupIfPresent(); // Dismiss any popup
            shopPOM.addProductToCart(data.product); // Add product to cart
            navPOM.goToCart(); // Go to Cart page
            popUpPOM.dismissPopupIfPresent(); // Dismiss popup if it appears

            Helpers.takeScreenshot(driver,
                    "Cart Before Applying " + data.coupon.key() + " for " + data.product); // Take screenshot

            System.out.println("Testing " + data.product +
                    " with discount: " + data.coupon.code() +
                    " (" + (data.coupon.discount() * 100) + "%)");

            cartPOM.applyCoupon(data.coupon.code()); // Apply coupon

            // Fetch cart totals
            BigDecimal subtotal = cartPOM.getSubtotalBD();
            BigDecimal discount = cartPOM.getDiscountBD();
            BigDecimal shipping = cartPOM.getShippingBD();
            BigDecimal total = cartPOM.getTotalBD();

            // Calculate expected discount and total
            BigDecimal expectedDiscount = subtotal
                    .multiply(BigDecimal.valueOf(data.coupon.discount()))
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal expectedTotal = subtotal.subtract(expectedDiscount)
                    .add(shipping)
                    .setScale(2, RoundingMode.HALF_UP);

            // Only check discount if it is greater than 0
            if (expectedDiscount.compareTo(BigDecimal.ZERO) > 0) {
                Assertions.assertEquals(0, expectedDiscount.compareTo(discount),
                        "Discount should match expected"); // Verify discount is correct
            } else {
                System.out.println("Expected discount is 0, actual discount: " + discount);
            }

            // Verify total is correct
            Assertions.assertEquals(0, expectedTotal.compareTo(total), "Total should match expected");

            System.out.printf("Subtotal: £%.2f | Discount: £%.2f | Total: £%.2f%n",
                    subtotal, discount, total);

            // Clean up cart after test
            cartPOM.removeCoupon(data.coupon.code());
            cartPOM.removeProduct();

            Assertions.assertEquals(0, cartPOM.getCartItemCount(), "Cart should be empty after cleanup");
       // });
    }

    // Inner record class to store a combination of product and coupon
    record TestData(CouponData coupon, String product) {
        @Override
        public String toString() {
            return product + " with " + coupon.key(); // How test is displayed in reports
        }
    }
}
