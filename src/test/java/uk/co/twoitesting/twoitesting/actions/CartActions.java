package uk.co.twoitesting.twoitesting.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.math.BigDecimal;

public class CartActions {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By couponBox = By.id("coupon_code");
    private final By applyCouponButton = By.cssSelector("button.button[name='apply_coupon']");
    private final By subtotalLocator = By.cssSelector(".cart-subtotal td span.woocommerce-Price-amount");
    private final By discountLocator = By.cssSelector(".cart-discount td span.woocommerce-Price-amount");
    private final By shippingLocator = By.cssSelector("tr.shipping td span.woocommerce-Price-amount");
    private final By totalLocator = By.cssSelector(".order-total td strong span.woocommerce-Price-amount");
    private final By removeProductLink = By.cssSelector("tr.cart_item td.product-remove a");

    public CartActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /** Apply a coupon code */
    public void applyCoupon(String couponCode) {
        try {
            try {
                WebElement showCoupon = driver.findElement(By.cssSelector(".showcoupon"));
                if (showCoupon.isDisplayed()) {
                    showCoupon.click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(couponBox));
                }
            } catch (NoSuchElementException ignored) {}

            WebElement couponInput = wait.until(ExpectedConditions.visibilityOfElementLocated(couponBox));
            couponInput.clear();
            couponInput.sendKeys(couponCode);

            WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(applyCouponButton));
            applyBtn.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(discountLocator));
        } catch (Exception e) {
            System.out.println("Failed to apply coupon: " + e.getMessage());
        }
    }

    /** Remove a specific coupon */
    public void removeCoupon(String couponName) {
        try {
            By couponLocator = By.cssSelector("tr.cart-discount.coupon-" + couponName.toLowerCase());
            WebElement couponRow = driver.findElement(couponLocator);
            couponRow.findElement(By.cssSelector("td a")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(couponLocator));
            System.out.println("Coupon removed: " + couponName);
        } catch (Exception e) {
            System.out.println("No coupon found: " + couponName);
        }
    }

    /** Remove a product from the cart */
    public void removeProduct() {
        try {
            WebElement removeBtn = driver.findElement(removeProductLink);
            removeBtn.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(removeProductLink));
            System.out.println("Product removed from cart.");
        } catch (Exception e) {
            System.out.println("No product found in cart.");
        }
    }

    /** Get the number of items in the cart */
    public int getCartItemCount() {
        return driver.findElements(By.cssSelector("tr.cart_item")).size();
    }

    /** Get subtotal */
    public BigDecimal getSubtotalBD() {
        return Helpers.extractPrice(driver.findElement(subtotalLocator).getText());
    }

    /** Get discount */
    public BigDecimal getDiscountBD() {
        return Helpers.extractPrice(driver.findElement(discountLocator).getText());
    }

    /** Get shipping cost */
    public BigDecimal getShippingBD() {
        return Helpers.extractPrice(driver.findElement(shippingLocator).getText());
    }

    /** Get total cost */
    public BigDecimal getTotalBD() {
        return Helpers.extractPrice(driver.findElement(totalLocator).getText());
    }
}
