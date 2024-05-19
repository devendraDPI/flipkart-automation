package demo.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionsWrapper {
    /**
     * Sends the specified text to the web element located by the given locator using the provided WebDriver instance. <p>
     * Waits for the element to be visible before sending keys.
     *
     * @param driver The WebDriver instance to use for locating the element and sending keys.
     * @param locator The locator strategy used to find the web element.
     * @param text The text to be sent to the web element.
     */
    public static void sendKeysAW(WebDriver driver, By locator, String text) {
        try {
            FlipkartUtils.logStatus("sendKeysAW", "Sending keys");

            // Setting up WebDriverWait with a timeout of 20 seconds
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Waiting for the element to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // Locating the web element
            WebElement element = driver.findElement(locator);

            // Clearing any existing text in the element
            element.clear();

            // Sending the specified text to the element
            element.sendKeys(text);

            // Sending ENTER key press after sending the text
            element.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            FlipkartUtils.logStatus("sendKeysAW", "Exception while sending keys\n" + e.getMessage());
        }
    }

    /**
     * Clicks on the web element located by the given locator using the provided WebDriver instance. <p>
     * Waits for the element to be visible before clicking.
     *
     * @param driver The WebDriver instance to use for locating the element and clicking.
     * @param locator The locator strategy used to find the web element.
     */
    public static void clickAW(WebDriver driver, By locator) {
        try {
            FlipkartUtils.logStatus("clickAW", "Clicking");

            // Setting up WebDriverWait with a timeout of 20 seconds
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Waiting for the element to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // Locating the web element
            WebElement element = driver.findElement(locator);

            // Clicking the element
            element.click();
        } catch (Exception e) {
            FlipkartUtils.logStatus("clickAW", "Exception while clicking\n" + e.getMessage());
        }
    }
}
