package demo;

import java.net.MalformedURLException;

public class App {
    public void getGreeting() throws InterruptedException, MalformedURLException {
        // This is to remove unnecessary warnings from your console
        System.setProperty("java.util.logging.config.file", "logging.properties");

        TestCases tests = new TestCases(); // Initialize your test class

        // TODO: call your test case functions one after other here
        // START Tests
        tests.testCase01();

        // END Tests
        tests.endTest(); // End your test by clearing connections and closing browser
    }

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        new App().getGreeting();
    }
}
