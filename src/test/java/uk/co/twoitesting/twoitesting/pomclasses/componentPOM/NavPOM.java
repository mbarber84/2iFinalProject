// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses.componentPOM;

// Import Selenium WebDriver classes for browser interaction

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.BasePOM;


// Define the NavPOM class for navigation bar actions
public class NavPOM extends BasePOM {

    private final By shopLink = By.linkText("Shop");
    private final By cartLink = By.linkText("Cart");
    private final By checkoutLink = By.linkText("Checkout");
    private final By accountLink = By.linkText("My account");

    public NavPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void goToShop() {
        click(shopLink);
    }

    public void goToCart() {
        click(cartLink);
    }

    public void goToCheckout() {
        click(checkoutLink);
    }

    public void goToAccount() {
        click(accountLink);
    }
}
