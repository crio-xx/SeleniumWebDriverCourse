package ui.admin;

import configuration.ConfigurationUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.BaseTest;

public class CheckLogin extends BaseTest {

    /*
     * Задание 3. Сделайте сценарий логина
     */

    @Test
    public void TestAdminLogin() {
        driver.get(baseUrl + "/admin");
        WebElement username = driver.findElement(By.name("username"));
        username.sendKeys("admin");

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("admin");

        WebElement loginBtn = driver.findElement(By.name("login"));
        loginBtn.click();
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
