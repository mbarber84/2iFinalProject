// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses;

import org.openqa.selenium.WebDriver; // Lets us control the browser
import org.openqa.selenium.support.ui.WebDriverWait; // Waits until a condition is true
import uk.co.twoitesting.twoitesting.actions.CartActions;
import java.math.BigDecimal; // Used to store prices precisely

// Define CartPOM to handle all actions related to the shopping cart
public class CartPOM extends BasePOM {

    private final CartActions cartActions;

    public CartPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.cartActions = new CartActions(driver, wait);
    }

    public void applyCoupon(String couponCode) {
        cartActions.applyCoupon(couponCode);
    }

    public void removeCoupon(String couponName) {
        cartActions.removeCoupon(couponName);
    }

    public void removeProduct() {
        cartActions.removeProduct();
    }

    public int getCartItemCount() {
        return cartActions.getCartItemCount();
    }

    public BigDecimal getSubtotalBD() {
        return cartActions.getSubtotalBD();
    }

    public BigDecimal getDiscountBD() {
        return cartActions.getDiscountBD();
    }

    public BigDecimal getShippingBD() {
        return cartActions.getShippingBD();
    }

    public BigDecimal getTotalBD() {
        return cartActions.getTotalBD();
    }
}