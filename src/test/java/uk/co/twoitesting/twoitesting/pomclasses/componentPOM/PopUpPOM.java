// Define the package where this class belongs
package uk.co.twoitesting.twoitesting.pomclasses.componentPOM;

// Import Selenium WebDriver classes for browser interaction

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.BasePOM;


import java.time.Duration;

// Define the PopUpPOM class to handle popup banners on the website
public class PopUpPOM extends BasePOM {

    private final By dismissBanner = By.linkText("Dismiss");

    public PopUpPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void dismissPopupIfPresent() {
        if (isVisible(dismissBanner)) {
            click(dismissBanner);
        }
    }
}
