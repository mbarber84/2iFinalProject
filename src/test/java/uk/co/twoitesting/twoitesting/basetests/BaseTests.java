package uk.co.twoitesting.twoitesting.basetests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
        String headless = System.getProperty("headless", "false").toLowerCase();

        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless.equals("true")) {
                    System.out.println(">>> Running Firefox in HEADLESS mode <<<");
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless.equals("true")) {
                    System.out.println(">>> Running Chrome in HEADLESS mode <<<");
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--start-maximized");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        helpers = new Helpers();



        popUpPOM = new PopUpPOM(driver, wait);
        navPOM = new NavPOM(driver, wait);
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
