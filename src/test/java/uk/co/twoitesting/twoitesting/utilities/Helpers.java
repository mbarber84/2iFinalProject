// This is the folder (package) where this class is stored
package uk.co.twoitesting.twoitesting.utilities;

// Import Allure for attaching screenshots to reports
import io.qameta.allure.Allure;
// Import Selenium classes for browser control and screenshots
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
// Import classes for file handling and converting screenshot to stream
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
// Import classes for working with prices and dates
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

// Define Helpers class with useful utility methods for tests
public class Helpers {

    // Convert a price string like "£12.34" into a BigDecimal number
    public static BigDecimal extractPrice(String priceText) {
        // Remove £ sign, minus sign, and extra spaces
        String cleaned = priceText.replace("£", "").replace("-", "").trim();
        // Convert the cleaned string into a number with 2 decimal places
        return new BigDecimal(cleaned).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    // Take a screenshot, save it locally, and attach it to Allure report
    public static void takeScreenshot(WebDriver driver, String name) {
        try {
            // Take a screenshot from the browser and save it as a temporary file
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Create a folder called "screenshots" if it doesn't exist
            File destDir = new File("target/screenshots");
            if (!destDir.exists()) {
                destDir.mkdir();
            }

            // Create a unique filename with a timestamp so it doesn’t overwrite
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File destFile = new File(destDir, name + "_" + timestamp + ".png");

            // Copy the temporary screenshot file into the screenshots folder
            FileHandler.copy(src, destFile);

            // Print the path of the saved screenshot
            System.out.println(" Screenshot saved: " + destFile.getAbsolutePath());

            // Attach the screenshot to the Allure report for viewing in test results
            Allure.addAttachment(name + "_" + timestamp,
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        } catch (IOException e) {
            // Print an error if saving the screenshot fails
            System.out.println(" Failed to save screenshot: " + e.getMessage());
        }
    }

    // Scroll a web element into the center of the page so it’s visible
    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
    }
}
