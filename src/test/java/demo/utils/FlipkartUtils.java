package demo.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlipkartUtils {
    /**
     * Logs a status message with the specified method name and message to the console.
     *
     * @param methodName The name of the method associated with the log message.
     * @param message The message to be logged.
     */
    public static void logStatus(String methodName, String message) {
        System.out.println(String.format("%s | %s | %s", getDateTime("yyyy-MM-dd HH:mm:ss"), methodName, message));
    }

    /**
     * Logs a status message with the specified test case ID, test step, test message, and test status to the console.
     *
     * @param testCaseID The ID of the test case associated with the log message.
     * @param testStep The step of the test case associated with the log message.
     * @param testMessage The message related to the test step.
     * @param testStatus The status of the test step (e.g., Pass, Fail).
     */
    public static void logStatus(String testCaseID, String testStep, String testMessage, String testStatus) {
        System.out.println(String.format("%s | %s | %s | %s | %s", getDateTime("yyyy-MM-dd HH:mm:ss"), testCaseID, testStep, testMessage, testStatus));
    }

    /**
     * Retrieves the current date and time in the specified format pattern.
     *
     * @param formatPattern The pattern used to format the date and time (e.g., "yyyy-MM-dd HH:mm:ss").
     * @return A string representing the current date and time formatted according to the specified pattern.
     */
    public static String getDateTime(String formatPattern) {
        LocalDateTime now = LocalDateTime.now();
        String formattedString = now.format(DateTimeFormatter.ofPattern(formatPattern));
        return formattedString;
    }

    /**
     * Closes the login popup window located by the given locator using the provided WebDriver instance. <p>
     * Waits for the popup window to be visible before attempting to close it.
     *
     * @param driver The WebDriver instance to use for locating the login popup window and closing it.
     * @param locator The locator strategy used to find the login popup window.
     */
    public static void closeLoginPopup(WebDriver driver, By locator) {
        try {
            logStatus("closeLoginPopup", "Closing login popup");

            // Setting up WebDriverWait with a timeout of 3 seconds
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            // Waiting for the login popup window to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // Locating the login popup window element
            WebElement element = driver.findElement(locator);

            // Clicking to close it
            element.click();
        } catch (Exception e) {
        }
    }

    /**
     * Retrieves the count of products with the specified rating using the provided WebDriver instance.
     *
     * @param driver The WebDriver instance to use for locating the products and retrieving the count.
     * @param operator The comparison operator used to filter products by rating (e.g., "<", "<=", "=", ">=", ">").
     * @param ratings The rating value to compare against.
     */
    public static void getProductsCountWithRating(WebDriver driver, String operator, int ratings) {
        try {
            logStatus("getProductsCountWithRating", String.format("Getting product count with rating %s %s", operator, ratings));

            // Finding elements matching the rating criteria
            List<WebElement> ratingList = driver.findElements(By.xpath("//span[contains(@id, 'productRating')]/div[text() "+ operator +" "+ ratings +"]"));

            logStatus("getProductsCountWithRating", String.format("Products count with rating %s %s are %d", operator, ratings, ratingList.size()));
        } catch (Exception e) {
            logStatus("getProductsCountWithRating", "Exception");
        }
    }

    /**
     * Retrieves the titles and discount percentages of products matching the specified discount criteria using the provided WebDriver instance.
     *
     * @param driver The WebDriver instance to use for locating the products and retrieving their titles and discount percentages.
     * @param operator The comparison operator used to filter products by discount percentage (e.g., "<", "<=", "=", ">=", ">").
     * @param discount The discount percentage value to compare against.
     */
    public static void getTitleAndDiscountPercent(WebDriver driver, String operator, int discount) {
        try {
            logStatus("getTitleAndDiscountPercent", String.format("Getting products with %s %d%% discount", operator, discount));

            // Finding elements matching the discount criteria
            List<WebElement> products = driver.findElements(By.xpath("//div[contains(@data-tkid, '.SEARCH')]//span[contains(text(), '% off') and number(substring-before(text(), '% off')) "+ operator +" "+ discount +"]"));

            // Iterating over the products and printing their titles and discount percentages
            for (WebElement product : products) {
                WebElement productTitle = product.findElement(By.xpath("./ancestor::a//div[contains(@class, 'col-7-12')]/div[1]"));
                System.out.println("Title: "+ productTitle.getText());
                System.out.println("Discount: " + product.getText());
            }
        } catch (Exception e) {
            logStatus("getTitleAndDiscountPercent", "Exception \n" + e.getMessage());
        }
    }

    /**
     * Retrieves the titles and image URLs of products with the highest number of reviews using the provided WebDriver instance. <p>
     * Prints information for the top 5 products with the highest ratings.
     *
     * @param driver The WebDriver instance to use for locating the products and retrieving their titles and image URLs.
     * @param topReviews The number of top-rated products for which to retrieve information.
     */
    public static void getTitleAndImageUrl(WebDriver driver, int topReviews) {
        try {
            logStatus("getTitleAndImageUrl", "Getting title and image url with highest number of reviews");

            // Creating a map to store product elements and their corresponding ratings
            Map<WebElement, String> map = new HashMap<>();

            // Finding parent elements of products and their associated review elements
            List<WebElement> productParents = driver.findElements(By.xpath("//div[contains(@data-tkid, '.SEARCH')]"));
            List<WebElement> productReviews = driver.findElements(By.xpath(".//span[contains(@id, 'productRating')]/following-sibling::span"));

            // Populating the map with product elements and their ratings
            for (int i = 0; i < productParents.size(); i++) {
                WebElement productParent = productParents.get(i);
                WebElement productReview = productReviews.get(i);
                String ratingText = productReview.getText().replaceAll("[(),]", "");
                map.put(productParent, ratingText);
            }

            // Sorting the map by ratings in descending order
            TreeMap<WebElement, String> sortedMap = new TreeMap<>(new Comparator<WebElement>() {
                @Override
                public int compare(WebElement e1, WebElement e2) {
                    double rating1 = Double.parseDouble(map.get(e1));
                    double rating2 = Double.parseDouble(map.get(e2));
                    return Double.compare(rating2, rating1);
                }
            });
            sortedMap.putAll(map);

            // Iterating over the sorted map and printing information for the specified number of topRated products
            int count = 0;
            for (Map.Entry<WebElement, String> entry : sortedMap.entrySet()) {
                if (count >= topReviews) {
                    break;
                }
                WebElement key = entry.getKey();
                String value = entry.getValue();
                String title = key.findElement(By.xpath("./a[2]")).getText();
                String imageUrl = key.findElement(By.xpath("./a//img")).getAttribute("src");
                System.out.println(String.format("Title: %s\nReviews: %s\nImage url: %s\n", title, value, imageUrl));
                count++;
            }
        } catch (Exception e) {
            logStatus("getTitleAndImageUrl", "Exception \n" + e.getMessage());
        }
    }
}
