package uk.co.twoitesting.twoitesting.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.BasePOM;

public class AccountActions extends BasePOM {

    private final By logoutLink = By.linkText("Logout");

    public AccountActions(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void logout() {
        if (isVisible(logoutLink)) {
            click(logoutLink);
        }
    }
}
