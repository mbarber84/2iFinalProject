package uk.co.twoitesting.twoitesting.pomclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.actions.ShopActions;
import uk.co.twoitesting.twoitesting.basetests.BaseTests;

public class ShopPOM extends BasePOM {

    private final ShopActions shopActions;

    public ShopPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.shopActions = new ShopActions(driver, wait);
    }

    public void openShop() {
        shopActions.openShop();
    }

    public void dismissPopupIfPresent() {
        shopActions.dismissPopupIfPresent();
    }

    public void addProductToCart(String productName) {
        shopActions.addProductToCart(productName);
    }

    public void viewCart() {
        shopActions.viewCart();
    }

    public void attachScreenshot(String name, String path) {
        shopActions.attachScreenshot(name, path);
    }

    public ShopActions getShopActions() {
        return shopActions;
    }
}
