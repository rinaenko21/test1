##Objectives
1.Create an automation project using Python, C#, Java or Kotlin that has both web and API test cases. 

2.The project should be in Github and available to VP interviewers to review before the technical interview.

3.The candidate will use Pet Swagger (https://petstore.swagger.io) and automate the following requirements:

- Create a new pet and add a picture to it
- Search for new pet and similar pets
- Validate the user can expand and collapse the rest method sections that provide endpoint information on the website

##Project structure:

###1. pom.xml to manage project and dependencies

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>Petstore</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>3.141.59</version>
        </dependency>

        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
            <version>4.2.2</version>
        </dependency>

        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>4.3.3</version>
        </dependency>

        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.6</version>
        </dependency>

        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.11.2</version>
        </dependency>

    </dependencies>

</project>
```

###2. resources package for configurations and logger 

Configuratios:

````
browser = chrome
headless = true
url = https://petstore.swagger.io
baseURI = https://petstore.swagger.io/v2
````
Logger:
````
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration status="INFO">
    <Appenders>
        <Console name="FLOW" target="SYSTEM_OUT">
            <PatternLayout pattern="[%level] %d{HH:mm:ss:SSS} - %msg - %l%n"/>
        </Console>

        <File name="PetstoreTest" fileName="logs/petstore_${date:yyyy-MM-dd}.log" append = "true">
            <PatternLayout pattern="[%level] %d{HH:mm:ss:SSS} - %msg - {%c}%n"/>
        </File>
    </Appenders>

    <Loggers>
        <Root level="INFO">
            <AppenderRef ref="FLOW"/>
            <AppenderRef ref="PetstoreTest"/>
        </Root>
    </Loggers>
</Configuration>
````

###3. utilities package with utility classes:

ConfigReader class to read properties:
````
public class ConfigReader {
static Properties properties;
static final String FILE_PATH;

    static {
        FILE_PATH = "src/test/resources/configurations.properties";
        FileInputStream input;

        try {
            input = new FileInputStream(FILE_PATH);
            properties = new Properties();
            properties.load(input);
            input.close();

        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace ();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key).trim();
    }
}
````
ChromeWebDriver class to configure chrome options:
````
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
````

Driver class to initialize chrome driver, created with Singleton design pattern:
````
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
````
###4. pages package to utilize POM design pattern:
````
public class SwaggerPage {
public SwaggerPage() {
PageFactory.initElements (Driver.getDriver (), this);
}

    @FindBy(xpath = "//button[@class='opblock-summary-control']")
    public List<WebElement> apiMethods;
}
````

###5. tests package with ApiTests and UiTests classes:


###6. runner package with Junit ApiRunner and UiRunner classes:

````
@RunWith(Suite.class)
@Suite.SuiteClasses({UiTests.class, ApiTests.class})
public class UiRunner {
}
````

##To run the Petstore project:
###1 Clone project from GitHub:

git clone git@github.com:rinaenko21/testVP.git

###2 Run project from IDE Test runner class;
