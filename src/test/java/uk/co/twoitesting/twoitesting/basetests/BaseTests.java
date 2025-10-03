package uk.co.twoitesting.twoitesting.basetests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.twoitesting.twoitesting.pomclasses.*;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.NavPOM;
import uk.co.twoitesting.twoitesting.pomclasses.componentPOM.PopUpPOM;
import uk.co.twoitesting.twoitesting.utilities.Helpers;

import java.time.Duration;

public class BaseTests {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected LoginPOM loginPOM;
    protected ShopPOM shopPOM;
    protected CartPOM cartPOM;
    protected AccountPOM accountPOM;
    protected NavPOM navPOM;
    protected PopUpPOM popUpPOM;
    protected CheckoutPOM checkoutPOM;
    protected OrdersPOM ordersPOM;
    protected Helpers helpers;

    @BeforeEach
    void setUpBase() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "firefox":
                driver = new org.openqa.selenium.firefox.FirefoxDriver();
                break;
            case "chrome":
            default:
                driver = new org.openqa.selenium.chrome.ChromeDriver();
                break;
        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        helpers = new Helpers();

        // Initialize core POMs first
        popUpPOM = new PopUpPOM(driver, wait);
        navPOM = new NavPOM(driver, wait);

        // Initialize dependent POMs
        loginPOM = new LoginPOM(driver, wait);
        shopPOM = new ShopPOM(driver, wait);
        cartPOM = new CartPOM(driver, wait);
        accountPOM = new AccountPOM(driver, wait, navPOM);
        checkoutPOM = new CheckoutPOM(driver, wait, navPOM);
        ordersPOM = new OrdersPOM(driver, wait, navPOM);
    }

    @AfterEach
    void tearDownBase() {
        Helpers.takeScreenshot(driver, "FinalState");
        emptyCart();
        accountPOM.logout();
        driver.quit();
    }

    protected void emptyCart() {
        try {
            driver.get(System.getProperty("base.url", "https://www.edgewordstraining.co.uk/demo-site") + "/cart/");
            cartPOM.removeCoupon("edgewords");
            cartPOM.removeCoupon("2idiscount");
            cartPOM.removeProduct();
            System.out.println("Cart emptied successfully.");
        } catch (Exception e) {
            System.out.println("Cart already empty or error: " + e.getMessage());
        }
    }
}
