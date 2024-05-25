package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import demo.utils.ActionsWrapper;
import demo.utils.FlipkartUtils;

public class TestCases {
    WebDriver driver;

    @BeforeClass
    public void createDriver() {
        FlipkartUtils.logStatus("createDriver", "Creating driver");

        // Setting up ChromeOptions with desired arguments
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Creating a Chrome WebDriver instance with the specified options
        driver = new ChromeDriver(options);

        FlipkartUtils.logStatus("createDriver", "Done driver creation");
    }

    @Test(priority = 1, description = "Verify the sorting functionality by popularity for search term 'washing machine'. Print the count of items with a rating less than or equal to 4 stars.")
    public void TestCase001() {
        // Launch chrome browser
        // Navigate to https://www.flipkart.com
        driver.get("https://www.flipkart.com");

        // Close login popup
        FlipkartUtils.closeLoginPopup(driver, By.xpath("//p[contains(text(), 'access to your Orders')]/../../../../span"));

        // Search "washing machine" and press enter
        String searchProduct = "washing machine";
        ActionsWrapper.sendKeysAW(driver, By.xpath("//input[contains(@name, 'q')]"), searchProduct);

        // Click on sort by popularity
        String sortBy = "Popularity";
        ActionsWrapper.clickAW(driver, By.xpath("//span[contains(text(), 'Sort By')]/following-sibling::div[contains(text(), '"+ sortBy +"')]"));

        // Store the count of items with rating ≤ 4 stars
        FlipkartUtils.getProductsCountWithRating(driver, "<=", 4);
    }

    @Test(priority = 1, description = "Verify the visibility of titles and discounts for items with more than 17% discount when searching for 'iPhone'. Print the titles and discount percentages.")
    public void TestCase002() {
        // Launch chrome browser
        // Navigate to https://www.flipkart.com
        driver.get("https://www.flipkart.com");

        // Close login popup
        FlipkartUtils.closeLoginPopup(driver, By.xpath("//p[contains(text(), 'access to your Orders')]/../../../../span"));

        // Search "iphone" and press enter
        String searchProduct = "iphone";
        ActionsWrapper.sendKeysAW(driver, By.xpath("//input[contains(@name, 'q')]"), searchProduct);

        // Store title and discount percentage > 17%
        FlipkartUtils.getTitleAndDiscountPercent(driver, ">", 17);
    }

    @Test(priority = 1, description = "Verify the filter functionality for the search term 'coffee mug'. Select 4 stars and above and print the title and image URL of the 5 items with the highest number of reviews.")
    public void TestCase003() {
        // Launch chrome browser
        // Navigate to https://www.flipkart.com
        driver.get("https://www.flipkart.com");

        // Close login popup
        FlipkartUtils.closeLoginPopup(driver, By.xpath("//p[contains(text(), 'access to your Orders')]/../../../../span"));

        // Search "coffee mug" and press enter
        String searchProduct = "coffee mug";
        ActionsWrapper.sendKeysAW(driver, By.xpath("//input[contains(@name, 'q')]"), searchProduct);

        // Click on 4 star and above checkbox
        int stars = 4;
        ActionsWrapper.clickAW(driver, By.xpath("//div[contains(@title, '"+ stars +"') and contains(@title, '& above')]"));

        // Refresh page
        driver.navigate().refresh();

        // Store title and image url of 5 items with highest number of reviews
        FlipkartUtils.getTitleAndImageUrl(driver, 5);
    }

    @AfterClass
    public void quitDriver() {
        FlipkartUtils.logStatus("quitDriver", "Quitting driver");
        // Quit the WebDriver instance
        driver.quit();
        FlipkartUtils.logStatus("quitDriver", "Done driver quit");
    }
}
