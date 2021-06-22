package admin;

import org.junit.Test;
import org.openqa.selenium.By;
import ui.BaseTest;
import ui.CustomExpectedConditions;

public class CheckNewWindow extends BaseTest {

    @Test
    public void testOpenLinkInNewWindow() {
        Login.auth(driver, baseUrl + "/admin/?app=countries&doc=countries", "admin", "admin");
        driver.findElement(By.cssSelector("#content [href $= edit_country]")).click();

        // список ссылок, которые будем открывать в новом окне
        var links = driver.findElements(By.cssSelector("#content [href ^= http]"));

        var originalWindow = driver.getWindowHandle();
        var existingWindows = driver.getWindowHandles();
        for(var link:links) {
            link.click();
            var newWindow = wait.until(CustomExpectedConditions.anyWindowOtherThan(existingWindows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }
}
