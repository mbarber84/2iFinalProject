// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses;

// Import Selenium WebDriver classes for browser interaction

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;
import uk.co.twoitesting.twoitesting.utilities.*;

// Define CheckoutPOM class for interacting with the checkout page
public class CheckoutPOM extends BasePOM {

    private final NavPOM navPOM;
    private boolean billingFilled = false;

    private final By firstNameField = By.id("billing_first_name");
    private final By lastNameField = By.id("billing_last_name");
    private final By addressField = By.id("billing_address_1");
    private final By cityField = By.id("billing_city");
    private final By postcodeField = By.id("billing_postcode");
    private final By emailField = By.id("billing_email");
    private final By phoneField = By.id("billing_phone");
    private final By paymentCheck = By.id("payment_method_cheque");
    private final By placeOrderButton = By.id("place_order");
    private final By orderNumberLocator = By.cssSelector(".order > strong");

    public CheckoutPOM(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        super(driver, wait);
        this.navPOM = navPOM;
    }

    public boolean fillBillingDetailsFromConfig() {
        if (billingFilled) return true;
        try {
            type(firstNameField, ConfigLoader.get("billing.firstname"));
            type(lastNameField, ConfigLoader.get("billing.lastname"));
            type(addressField, ConfigLoader.get("billing.address"));
            type(cityField, ConfigLoader.get("billing.city"));
            type(postcodeField, ConfigLoader.get("billing.postcode"));
            type(emailField, ConfigLoader.get("billing.email"));
            type(phoneField, ConfigLoader.get("billing.phone"));
            billingFilled = true;
            return true;
        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "BillingError");
            return false;
        }
    }

    public void selectCheckPayments() {
        try {
            if (!waitAndFind(paymentCheck).isSelected()) {
                click(paymentCheck);
            }
        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "PaymentError");
        }
    }

    public void placeOrder() {
        try {
            click(placeOrderButton);
        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "PlaceOrderError");
        }
    }

    public String captureOrderNumber() {
        try {
            WebElement orderNumberElem = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(orderNumberLocator));

            String orderNumber = orderNumberElem.getText().trim().replace("#", "");
            System.out.println("Captured Order Number: " + orderNumber);

            Helpers.takeScreenshot(driver, "Order Placed - " + orderNumber);
            return orderNumber;

        } catch (Exception e) {
            System.out.println("Capturing order number failed: " + e.getMessage());
            Helpers.takeScreenshot(driver, "OrderNumberError");
            return "";
        }
    }
}
