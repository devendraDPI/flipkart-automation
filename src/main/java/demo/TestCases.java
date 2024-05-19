package demo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestCases {
    WebDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        System.out.println("Start Tests: TestCases");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    public void endTest() {
        System.out.println("End Tests: TestCases");
        driver.quit();
    }

    public void testCase01() {
        System.out.println("\nTestCase01: START");
        driver.get("https://www.google.com");
        System.out.println("TestCase01: END\n");
    }
}
