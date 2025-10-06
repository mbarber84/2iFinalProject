// Define the folder (package) where this class is stored
package uk.co.twoitesting.twoitesting.pomclasses.componentPOM;

// Import tools from Selenium to find elements and control the browser
import org.openqa.selenium.By; // Used to locate elements on the webpage
import org.openqa.selenium.WebDriver; // Lets us control the browser
import org.openqa.selenium.support.ui.WebDriverWait; // Lets us wait for things to appear

// Import our BasePOM class which has common functions for all pages
import uk.co.twoitesting.twoitesting.pomclasses.BasePOM;

// Define the PopUpPOM class, which is used to handle popup banners on the website
public class PopUpPOM extends BasePOM { // Inherits useful functions from BasePOM

    // Locate the "Dismiss" link on the popup banner
    private final By dismissBanner = By.linkText("Dismiss");

    // Constructor for PopUpPOM. Runs when we create a new PopUpPOM object
    public PopUpPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait); // Call BasePOM constructor to set up driver and wait
    }

    // This method checks if a popup exists, and closes it if it does
    public void dismissPopupIfPresent() {
        if (isVisible(dismissBanner)) { // Check if the dismiss button is visible
            click(dismissBanner); // Click the dismiss button to close the popup
        }
    }
}
