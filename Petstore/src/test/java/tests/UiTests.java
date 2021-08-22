package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.SwaggerPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.List;
import java.util.logging.Logger;

public class UiTests {

    WebDriver driver;
    SwaggerPage swpage = new SwaggerPage ();
    private static final Logger LOGGER = Logger.getLogger (String.valueOf (UiTests.class));


    @Before
    public void SetUp() {
        driver = Driver.getDriver ();
    }

    @After
    public void tearDown() {
        Driver.closeDriver ();
    }

    @Test
    public void titleTest() {
        LOGGER.info ("Navigating to page " + ConfigReader.getProperty ("url"));
        driver.navigate ().to (ConfigReader.getProperty ("url"));
        String title = driver.getTitle ();
        LOGGER.info ("Getting title " + title);
        LOGGER.info("Running test: Verify title contains Swagger");
        Assert.assertTrue ("Title verification failed.", title.trim ().contains ("Swagger"));
    }

    @Test
    public void expandAndCollapseTest(){
        LOGGER.info ("Navigating to page " + ConfigReader.getProperty ("url"));
        driver.navigate ().to (ConfigReader.getProperty ("url"));
        WebDriverWait wait = new WebDriverWait (driver, 3);
        List<WebElement> listOfMethods = swpage.apiMethods;

        LOGGER.info("Iterating over list of API methods");
        for(int i = 0; i < listOfMethods.size (); i++){
            wait.until (ExpectedConditions.elementToBeClickable (listOfMethods.get (i)));
            String methodName = listOfMethods.get (i).getAttribute ("aria-label");
            listOfMethods.get (i).click ();
            LOGGER.info("Expanding and verifying method " + methodName);
            Assert.assertTrue (listOfMethods.get (i).getAttribute ("aria-expanded").contains ("true"));

            listOfMethods.get (i).click ();
            LOGGER.info("Collapsing and verifying method " + methodName);
            Assert.assertTrue (listOfMethods.get (i).getAttribute ("aria-expanded").contains ("false"));
        }
    }
}
