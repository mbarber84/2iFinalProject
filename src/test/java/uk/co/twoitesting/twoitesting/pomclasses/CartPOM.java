// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses;

// Import Selenium tools to interact with web elements and wait for them
import org.openqa.selenium.By; // Used to find elements on a webpage
import org.openqa.selenium.NoSuchElementException; // Handles errors when elements are not found
import org.openqa.selenium.WebDriver; // Lets us control the browser
import org.openqa.selenium.WebElement; // Represents an element on the page
import org.openqa.selenium.support.ui.ExpectedConditions; // Helps wait for elements to be ready
import org.openqa.selenium.support.ui.WebDriverWait; // Waits until a condition is true

// Import a helper class to extract prices from strings
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.math.BigDecimal; // Used to store prices precisely

// Define CartPOM to handle all actions related to the shopping cart
public class CartPOM extends BasePOM { // Inherits helper methods like click() and isVisible()

    // Locate important elements on the cart page
    private final By couponBox = By.id("coupon_code"); // Coupon input field
    private final By applyCouponButton = By.cssSelector("button.button[name='apply_coupon']"); // Button to apply coupon
    private final By subtotalLocator = By.cssSelector(".cart-subtotal td span.woocommerce-Price-amount"); // Subtotal amount
    private final By discountLocator = By.cssSelector(".cart-discount td span.woocommerce-Price-amount"); // Discount amount
    private final By shippingLocator = By.cssSelector("tr.shipping td span.woocommerce-Price-amount"); // Shipping cost
    private final By totalLocator = By.cssSelector(".order-total td strong span.woocommerce-Price-amount"); // Total cost
    private final By removeProductLink = By.cssSelector("tr.cart_item td.product-remove a"); // Link to remove a product

    // Constructor to initialize driver and wait
    public CartPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait); // Call BasePOM constructor
    }

    /** Apply a coupon code */
    public void applyCoupon(String couponCode) {
        try {
            // Try to reveal coupon field if it's hidden
            try {
                WebElement showCoupon = driver.findElement(By.cssSelector(".showcoupon")); // Find "Show Coupon" link
                if (showCoupon.isDisplayed()) { // If visible
                    showCoupon.click(); // Click it to show the coupon input
                    wait.until(ExpectedConditions.visibilityOfElementLocated(couponBox)); // Wait for input to appear
                }
            } catch (NoSuchElementException ignored) {} // Ignore if "Show Coupon" doesn't exist

            // Type the coupon code
            WebElement couponInput = wait.until(ExpectedConditions.visibilityOfElementLocated(couponBox));
            couponInput.clear(); // Clear any old text
            couponInput.sendKeys(couponCode); // Type the coupon

            // Click the apply button
            WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(applyCouponButton));
            applyBtn.click();

            // Wait for the discount to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(discountLocator));

        } catch (Exception e) {
            System.out.println("Failed to apply coupon: " + e.getMessage()); // Print error if something goes wrong
        }
    }

    /** Remove a specific coupon */
    public void removeCoupon(String couponName) {
        try {
            // Find the coupon row by its name
            By couponLocator = By.cssSelector("tr.cart-discount.coupon-" + couponName.toLowerCase());
            WebElement couponRow = driver.findElement(couponLocator);
            couponRow.findElement(By.cssSelector("td a")).click(); // Click the "remove" link
            wait.until(ExpectedConditions.invisibilityOfElementLocated(couponLocator)); // Wait for it to disappear
            System.out.println("Coupon removed: " + couponName);
        } catch (Exception e) {
            System.out.println("No coupon found: " + couponName); // If coupon doesn't exist, print message
        }
    }

    /** Remove any product from cart */
    public void removeProduct() {
        try {
            WebElement removeBtn = driver.findElement(removeProductLink); // Find remove button
            removeBtn.click(); // Click to remove product
            wait.until(ExpectedConditions.invisibilityOfElementLocated(removeProductLink)); // Wait until removed
            System.out.println("Product removed from cart.");
        } catch (Exception e) {
            System.out.println("No product found in cart."); // If cart empty, print message
        }
    }

    /** Get the number of items in the cart */
    public int getCartItemCount() {
        return driver.findElements(By.cssSelector("tr.cart_item")).size(); // Count all rows for products
    }

    // Fetch subtotal as a BigDecimal (precise number)
    public BigDecimal getSubtotalBD() {
        return Helpers.extractPrice(driver.findElement(subtotalLocator).getText());
    }

    // Fetch discount as a BigDecimal
    public BigDecimal getDiscountBD() {
        return Helpers.extractPrice(driver.findElement(discountLocator).getText());
    }

    // Fetch shipping cost as a BigDecimal
    public BigDecimal getShippingBD() {
        return Helpers.extractPrice(driver.findElement(shippingLocator).getText());
    }

    // Fetch total cost as a BigDecimal
    public BigDecimal getTotalBD() {
        return Helpers.extractPrice(driver.findElement(totalLocator).getText());
    }
}
