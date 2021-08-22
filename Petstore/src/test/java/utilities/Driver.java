package utilities;

import org.openqa.selenium.WebDriver;

public class Driver {
    private Driver() {}
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null && ConfigReader.getProperty("browser")
                .equalsIgnoreCase("chrome")) {
            driver = ChromeWebDriver.loadChromeDriver ();
            driver.manage ().window ().maximize ();
        }
        return driver;
    }

    public static void closeDriver() {
        try {
            if (driver != null) {
                driver.close ();
                driver.quit ();
                driver = null;
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
