package uk.co.twoitesting.twoitesting.steps;

import io.qameta.allure.Step;
import uk.co.twoitesting.twoitesting.pomclasses.CheckoutPOM;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

public class CheckoutSteps {

    private final CheckoutPOM checkoutPOM;

    public CheckoutSteps(CheckoutPOM checkoutPOM) {
        this.checkoutPOM = checkoutPOM;
    }

    @Step("Fill billing details from config")
    public void fillBillingDetails() {
        checkoutPOM.fillBillingDetailsFromConfig();
        Helpers.takeScreenshot(checkoutPOM.getDriver(), "Billing Details Entered");
    }

    @Step("Select check payment method")
    public void selectCheckPayment() {
        checkoutPOM.selectCheckPayments();
    }

    @Step("Place the order")
    public void placeOrder() {
        checkoutPOM.placeOrder();
    }

    @Step("Capture order number")
    public String captureOrderNumber() {
        String orderNumber = checkoutPOM.captureOrderNumber();
        Helpers.takeScreenshot(checkoutPOM.getDriver(), "Order Placed - " + orderNumber);
        return orderNumber;
    }
}
