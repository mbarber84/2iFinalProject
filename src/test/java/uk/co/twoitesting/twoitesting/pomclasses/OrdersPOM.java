// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.util.List;

// Define OrdersPOM class for interacting with the "My Orders" page
public class OrdersPOM extends BasePOM {

    private final NavPOM navPOM;
    private final By ordersLink = By.cssSelector(
            "#post-7 > div > div > nav > ul > li.woocommerce-MyAccount-navigation-link--orders > a");
    private final By orderNumbersLocator = By.cssSelector("td.woocommerce-orders-table__cell-order-number a");

    public OrdersPOM(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        super(driver, wait);
        this.navPOM = navPOM;
    }

    public boolean isOrderPresent(String orderNumber) {
        try {
            // Navigate to My Account page
            navPOM.goToAccount();

            // Click on "Orders" link
            click(ordersLink);

            // Wait until orders are visible
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(orderNumbersLocator, 0));

            // Get all order numbers
            List<WebElement> orders = driver.findElements(orderNumbersLocator);

            // Print all found orders to console
            System.out.println("Orders found on My Orders page:");
            orders.forEach(e -> System.out.println(" - " + e.getText().trim()));

            // Check if our orderNumber exists
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
