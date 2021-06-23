package ui;

import configuration.ConfigurationUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;

public class BaseTest {
    public WebDriver driver;
    public String baseUrl = "http://localhost/litecart";
    public WebDriverWait wait;

    public void setUpChrome(){
        System.setProperty("webdriver.chrome.driver", ConfigurationUtils.getChromeDriverPath());
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.INFO);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 5);
    }

    public void setUpFirefox(){
        System.setProperty("webdriver.gecko.driver", ConfigurationUtils.getGeckoDriverPath());

        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 5);
    }

    @Before
    public void setDriver() {
        setUpChrome();
    }

    @After
    public void closeDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }
}
