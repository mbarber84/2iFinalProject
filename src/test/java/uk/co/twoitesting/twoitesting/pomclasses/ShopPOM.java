// This is the folder (package) where this class is stored
package uk.co.twoitesting.twoitesting.pomclasses;

// Import Allure tools for reporting steps and attaching files in test reports
import io.qameta.allure.Allure; // Main Allure reporting class
import io.qameta.allure.Step;   // To annotate steps for reporting

// Import Selenium tools to interact with the web page
import org.openqa.selenium.*; // General Selenium classes (WebDriver, WebElement, etc.)
import org.openqa.selenium.support.ui.ExpectedConditions; // Conditions to wait for
import org.openqa.selenium.support.ui.WebDriverWait; // Wait until condition is true

// Import PopUpPOM for handling popups
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.PopUpPOM;
// Import helpers for scrolling and other utility methods
import uk.co.twoitesting.twoitesting.utilities.Helpers;

// Import classes for reading files
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

// Define ShopPOM class to control actions on the shop page
public class ShopPOM {

    // Browser controller
    private final WebDriver driver;
    // Wait controller to pause until elements are ready
    private final WebDriverWait wait;

    // Find elements on the page
    private final By shopLink = By.linkText("Shop"); // Link to go to the Shop page
    private final By dismissBanner = By.linkText("Dismiss"); // Link to dismiss any popup/banner
    private final By poloAddButton = By.cssSelector("li.product:nth-child(9) > a:nth-child(2)");
    // Specific add-to-cart button for Polo shirt

    // PopUp handler to manage popups on the page
    private final PopUpPOM popupPom;

    // Constructor runs when we create ShopPOM
    public ShopPOM(WebDriver driver, WebDriverWait wait) {
        this.driver = driver; // Save the browser controller
        this.wait = wait;     // Save the wait controller
        this.popupPom = new PopUpPOM(driver, wait); // Initialize PopUpPOM for handling popups
    }

    // Open the Shop page, annotated as a step for Allure reporting
    @Step("Open the Shop page")
    public void openShop() {
        // Wait until the Shop link is clickable
        WebElement shop = wait.until(ExpectedConditions.elementToBeClickable(shopLink));
        // Scroll the Shop link into view
        Helpers.scrollIntoView(driver, shop);
        try {
            shop.click(); // Try to click normally
        } catch (ElementClickInterceptedException e) {
            // If a popup blocks the click, click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", shop);
        }
    }

    // Dismiss popup if it appears
    public void dismissPopupIfPresent() {
        popupPom.dismissPopupIfPresent(); // Let PopUpPOM handle it
    }

    // Add a product to the cart, product name comes from parameter
    @Step("Add {productName} to cart")
    public void addProductToCart(String productName) {
        // Build a dynamic CSS selector for the add-to-cart button
        String ariaLabel = String.format("Add “%s” to your cart", productName);
        By addButton = By.cssSelector("[aria-label='" + ariaLabel + "']");

        // Wait until the button is present in the page
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(addButton));
        // Scroll button into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        // Wait until clickable, then click
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    // Click the "View cart" link
    @Step("View the cart")
    public void viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View cart"))).click();
    }

    // Attach a screenshot to Allure report
    @Step("Attach screenshot: {name}")
    public void attachScreenshot(String name, String path) {
        try (InputStream is = new FileInputStream(path)) { // Open screenshot file
            Allure.addAttachment(name, is); // Attach screenshot to Allure
        } catch (IOException e) { // Handle errors if file not found
            e.printStackTrace(); // Print error for debugging
        }
    }
}
