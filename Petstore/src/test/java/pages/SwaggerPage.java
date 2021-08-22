package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class SwaggerPage {
    public SwaggerPage() {
        PageFactory.initElements (Driver.getDriver (), this);
    }

    @FindBy(xpath = "//button[@class='opblock-summary-control']")
    public List<WebElement> apiMethods;
}
