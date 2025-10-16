package uk.co.twoitesting.twoitesting.steps;

import io.qameta.allure.Step;
import uk.co.twoitesting.twoitesting.pomclasses.LoginPOM;
import uk.co.twoitesting.twoitesting.pomclasses.AccountPOM;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LoginSteps {

    private final LoginPOM loginPOM;
    private final AccountPOM accountPOM;

    public LoginSteps(LoginPOM loginPOM, AccountPOM accountPOM) {
        this.loginPOM = loginPOM;
        this.accountPOM = accountPOM;
    }

    @Step("GIVEN the user logs in")
    public void loginUser() {
        loginPOM.open();
        loginPOM.login();
        Helpers.takeScreenshot(loginPOM.getDriver(), "Login Success");
    }

    @Step("AND the user is logged in")
    public void verifyUserLoggedIn() {
        assertThat("User should be logged in", loginPOM.isUserLoggedIn(), is(true));
    }

    @Step("AND the user logs out")
    public void logoutUser() {
        accountPOM.logout();
        Helpers.takeScreenshot(loginPOM.getDriver(), "Logged Out");
    }
}
