package admin;

import org.example.configuration.ConfigurationUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class RetourPages {
    WebDriver driver;
    String baseUrl = "http://localhost/litecart/";

    @Test
    public void TestFollowLink() {
        Login.auth(driver, baseUrl + "admin/", "admin", "admin");
        var listRightMenu = driver.findElement(By.cssSelector("div#box-apps-menu-wrapper"));
        int size = listRightMenu.findElements(By.cssSelector("li#app-")).size();

        for(int i = 1; i <= size; i++){
            // переходим в нужный раздел меню справа
            driver.findElement(By.cssSelector(String.format("ul#box-apps-menu > li#app-:nth-child(%d)", i))).click();

            // подсчёт пунктов меню внутри текущего раздела
            var selectedElement = driver.findElement(By.cssSelector("#box-apps-menu #app-.selected"));
            int sizeSubsection = selectedElement.findElements(By.tagName("li")).size();

            // обход пунктов текущего раздела. С 1, т.к. у раздела Modules первым открывается второй пункт
            for(int j = 1; j <= sizeSubsection; j++){
                // Дополнительное обращение, т.к. после перехода в раздел selectedElements потерял актуальность
                var area = driver.findElement(By.cssSelector("#box-apps-menu #app-.selected"));
                area.findElement(By.cssSelector(String.format("li:nth-child(%d)", j))).click();
            }
        }

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
