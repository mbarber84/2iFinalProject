package uk.co.twoitesting.twoitesting.steps;

import io.qameta.allure.Step;
import uk.co.twoitesting.twoitesting.pomclasses.OrdersPOM;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OrdersSteps {

    private final OrdersPOM ordersPOM;

    public OrdersSteps(OrdersPOM ordersPOM) {
        this.ordersPOM = ordersPOM;
    }

    @Step("Verify that order '{orderNumber}' exists in My Account")
    public void verifyOrderPresent(String orderNumber) {
        assertThat("Order should appear in My Account -> Orders",
                ordersPOM.isOrderPresent(orderNumber), is(true));
    }
}
