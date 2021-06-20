package ui;

import org.example.configuration.ConfigurationUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest {
    public WebDriver driver;
    public String baseUrl = "http://localhost/litecart";
    public WebDriverWait wait;

    @Before
    public void setUpChrome(){
        System.setProperty("webdriver.chrome.driver", ConfigurationUtils.getChromeDriverPath());
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
    }

    @After
    public void closeDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }
}
