package uk.co.twoitesting.twoitesting.pomclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.actions.AccountActions;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;

public class AccountPOM extends BasePOM {

    private final AccountActions accountActions;

    public AccountPOM(WebDriver driver, WebDriverWait wait, NavPOM navPOM) {
        super(driver, wait);
        this.accountActions = new AccountActions(driver, wait);
    }

    public void logout() {
        accountActions.logout();
    }
}
