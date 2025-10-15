package uk.co.twoitesting.twoitesting.pomclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.actions.OrdersActions;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;

public class OrdersPOM extends BasePOM {

    private final OrdersActions ordersActions;

    public OrdersPOM(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        super(driver, wait);
        this.ordersActions = new OrdersActions(driver, wait, navPOM);
    }

    public boolean isOrderPresent(String orderNumber) {
        return ordersActions.isOrderPresent(orderNumber);
    }
}
