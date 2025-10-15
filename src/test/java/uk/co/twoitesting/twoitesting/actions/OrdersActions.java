package uk.co.twoitesting.twoitesting.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.util.List;

public class OrdersActions {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final NavPOM navPOM;

    // Locators
    private final By ordersLink = By.cssSelector(
            "#post-7 > div > div > nav > ul > li.woocommerce-MyAccount-navigation-link--orders > a");
    private final By orderNumbersLocator = By.cssSelector("td.woocommerce-orders-table__cell-order-number a");

    public OrdersActions(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        this.driver = driver;
        this.wait = wait;
        this.navPOM = navPOM;
    }

    // This method contains the business logic originally in OrdersPOM
    public boolean isOrderPresent(String orderNumber) {
        try {
            // Navigate to My Account
            navPOM.goToAccount();

            // Click Orders link
            WebElement ordersLinkElement = wait.until(ExpectedConditions.elementToBeClickable(ordersLink));
            ordersLinkElement.click();

            // Wait for orders to load
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(orderNumbersLocator, 0));

            // Fetch all order elements
            List<WebElement> orders = driver.findElements(orderNumbersLocator);

            // Print for debug
            System.out.println("Orders found on My Orders page:");
            orders.forEach(e -> System.out.println(" - " + e.getText().trim()));

            // Check if specific order is found
            boolean found = orders.stream()
                    .map(e -> e.getText().trim().replace("#", ""))
                    .anyMatch(text -> text.equals(orderNumber));

            if (found) {
                System.out.println("Order number " + orderNumber + " successfully found!");
            } else {
                System.out.println("Order number " + orderNumber + " not found in My Orders.");
                Helpers.takeScreenshot(driver, "OrderNotFound");
            }

            return found;

        } catch (Exception e) {
            Helpers.takeScreenshot(driver, "VerifyOrderError");
            System.out.println("Error verifying order: " + e.getMessage());
            return false;
        }
    }
}
