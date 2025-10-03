package uk.co.twoitesting.twoitesting.pomclasses;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Base class for all POMs
public abstract class BasePOM {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePOM(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Helper: wait for element to be visible and return it
    protected WebElement waitAndFind(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Helper: click element after waiting for it to be clickable
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Helper: type text into input
    protected void type(By locator, String text) {
        WebElement element = waitAndFind(locator);
        element.clear();
        element.sendKeys(text);
    }
    protected boolean isVisible(By locator) {
        try {
            waitAndFind(locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
