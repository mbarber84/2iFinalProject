package uk.co.twoitesting.twoitesting.actions;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.PopUpPOM;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ShopActions {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final PopUpPOM popupPom;

    // Locators
    private final By shopLink = By.linkText("Shop");
    private final By dismissBanner = By.linkText("Dismiss");
    private final By poloAddButton = By.cssSelector("li.product:nth-child(9) > a:nth-child(2)");

    public ShopActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.popupPom = new PopUpPOM(driver, wait);
    }

    @Step("Open the Shop page")
    public void openShop() {
        WebElement shop = wait.until(ExpectedConditions.elementToBeClickable(shopLink));
        Helpers.scrollIntoView(driver, shop);
        try {
            shop.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", shop);
        }
    }

    public void dismissPopupIfPresent() {
        popupPom.dismissPopupIfPresent();
    }

    @Step("Add {productName} to cart")
    public void addProductToCart(String productName) {
        String ariaLabel = String.format("Add “%s” to your cart", productName);
        By addButton = By.cssSelector("[aria-label='" + ariaLabel + "']");

        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(addButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    @Step("View the cart")
    public void viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View cart"))).click();
    }

    @Step("Attach screenshot: {name}")
    public void attachScreenshot(String name, String path) {
        try (InputStream is = new FileInputStream(path)) {
            Allure.addAttachment(name, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver() {
        return this.driver;
    }
}
