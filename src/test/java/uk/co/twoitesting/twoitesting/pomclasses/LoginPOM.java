package uk.co.twoitesting.twoitesting.pomclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.actions.LoginActions;

public class LoginPOM extends BasePOM {

    private final LoginActions loginActions;

    public LoginPOM(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.loginActions = new LoginActions(driver, wait);
    }

    public void open() {
        loginActions.openLoginPage();
    }

    public void login() {
        loginActions.login();
    }

    public boolean isUserLoggedIn() {
        return loginActions.isUserLoggedIn();
    }

    public void logout() {
        loginActions.logout();
    }
}
