package uk.co.twoitesting.twoitesting.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.co.twoitesting.twoitesting.basetests.BaseTests;
import uk.co.twoitesting.twoitesting.steps.CouponSteps;
import uk.co.twoitesting.twoitesting.utilities.CouponData;
import uk.co.twoitesting.twoitesting.utilities.CsvCouponLoader;

import java.util.List;
import java.util.stream.Stream;

@Epic("Shop Tests")
@Feature("Cart and Discount")
@Story("Purchase Items with Discounts")
public class CouponTest extends BaseTests {

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

        CouponSteps steps = new CouponSteps(loginPOM, navPOM, shopPOM, cartPOM, popUpPOM);

        steps.loginAndCleanCart();
        steps.addProductAndApplyDiscount(data.product(), data.coupon().code());
        steps.verifyCartTotals(data.product(), data.coupon().discount());
        steps.cleanupCart(data.coupon().code());
    }

    record TestData(CouponData coupon, String product) {
        @Override
        public String toString() {
            return product + " with " + coupon.key();
        }
    }
}
