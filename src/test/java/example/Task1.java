package example;

import org.example.configuration.ConfigurationUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task1 {
    WebDriver driver;

    @Test
    public void browserTestChrome() {
        driver.get("https://google.com/");
    }


    @Before
    public void setUpChrome(){
        System.setProperty("webdriver.chrome.driver", ConfigurationUtils.getChromeDriverPath());
        driver = new ChromeDriver();
    }

    @After
    public void closeDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }

}