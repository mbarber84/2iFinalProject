// Define the folder (package) where this class is stored
package uk.co.twoitesting.twoitesting.pomclasses;

// Import tools from Selenium to find elements, click them, and wait for things to appear
import org.openqa.selenium.By; // Lets us locate elements on a webpage
import org.openqa.selenium.TimeoutException; // Handles errors if an element doesn't appear in time
import org.openqa.selenium.WebDriver; // Lets us control the browser
import org.openqa.selenium.WebElement; // Represents an element on the page
import org.openqa.selenium.support.ui.ExpectedConditions; // Conditions to wait for (like visible or clickable)
import org.openqa.selenium.support.ui.WebDriverWait; // Lets us wait until something appears

// Base class for all POMs (Page Object Models)
// "abstract" means you cannot create a BasePOM directly, only classes that extend it
public abstract class BasePOM {

    // The browser driver used to control the browser
    protected WebDriver driver;
    // Wait object used to pause until something is ready on the page
    protected WebDriverWait wait;

    // Constructor for BasePOM, runs when a POM object is created
    public BasePOM(WebDriver driver, WebDriverWait wait) {
        this.driver = driver; // Save the browser driver
        this.wait = wait; // Save the wait object
    }

    // Helper method: wait for an element to appear and return it
    protected WebElement waitAndFind(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        // "wait.until(...)" pauses until the element is visible on the page
    }

    // Helper method: click an element after waiting for it to be clickable
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        // Wait until element is clickable, then click it
    }

    // Helper method: type text into an input field
    protected void type(By locator, String text) {
        WebElement element = waitAndFind(locator); // Find the element and wait for it
        element.clear(); // Remove any existing text
        element.sendKeys(text); // Type the new text
    }

    // Helper method: check if an element is visible on the page
    protected boolean isVisible(By locator) {
        try {
            waitAndFind(locator); // Try to find the element
            return true; // If found, return true
        } catch (TimeoutException e) {
            return false; // If not found in time, return false
        }
    }
}
