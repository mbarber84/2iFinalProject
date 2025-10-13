package uk.co.twoitesting.twoitesting.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.utilities.ConfigLoader;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

public class CheckoutActions {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private boolean billingFilled = false;

    // Locators
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

    public CheckoutActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
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
            WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(paymentCheck));
            if (!elem.isSelected()) elem.click();
        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "PaymentError");
        }
    }

    public void placeOrder() {
        try {
            WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));
            elem.click();
        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "PlaceOrderError");
        }
    }

    public String captureOrderNumber() {
        try {
            WebElement orderNumberElem = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(orderNumberLocator));
            String orderNumber = orderNumberElem.getText().trim().replace("#", "");
            Helpers.takeScreenshot(driver, "Order Placed - " + orderNumber);
            return orderNumber;
        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "OrderNumberError");
            return "";
        }
    }

    // Helper method to type into fields
    private void type(By locator, String value) {
        WebElement elem = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        elem.clear();
        elem.sendKeys(value);
    }
}
