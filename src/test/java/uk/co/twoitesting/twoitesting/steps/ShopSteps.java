package uk.co.twoitesting.twoitesting.steps;

import io.qameta.allure.Step;
import uk.co.twoitesting.twoitesting.pomclasses.ShopPOM;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.PopUpPOM;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

public class ShopSteps {

    private final ShopPOM shopPOM;
    private final PopUpPOM popUpPOM;

    public ShopSteps(ShopPOM shopPOM, PopUpPOM popUpPOM) {
        this.shopPOM = shopPOM;
        this.popUpPOM = popUpPOM;
    }

    @Step("Open Shop page and dismiss popup")
    public void openShopAndDismissPopup() {
        shopPOM.openShop();
        popUpPOM.dismissPopupIfPresent();
    }

    @Step("Add product '{productName}' to cart and view cart")
    public void addProductToCart(String productName) {
        shopPOM.addProductToCart(productName);
        Helpers.takeScreenshot(shopPOM.getShopActions().getDriver(), "Added " + productName + " to Cart");

        // NEW: explicitly navigate to cart
        shopPOM.viewCart();
        Helpers.takeScreenshot(shopPOM.getShopActions().getDriver(), "Cart Opened");
    }
}
