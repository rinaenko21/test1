package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeWebDriver {
    public static WebDriver loadChromeDriver() {
        WebDriverManager.chromedriver().setup ();

        ChromeOptions options = new ChromeOptions ();
        options.addArguments("--start-maximized");
        options.addArguments("--window-size=1920,1080");

        if (ConfigReader.getProperty("headless").equalsIgnoreCase("true")){
            options.addArguments("--headless");
        }

        WebDriver driver = new ChromeDriver(options);
        return driver;
    }
}
