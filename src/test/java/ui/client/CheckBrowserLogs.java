package ui.client;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import ui.BaseTest;

public class CheckBrowserLogs extends BaseTest {

    /*
     * Задание 17. Проверьте отсутствие сообщений в логе браузера (additional)
     */

    @Test
    public void TestCheckStickers() {
        driver.navigate().to(baseUrl);
        var logsBefore = driver.manage().logs().get("browser").getAll();

        Select manufactures = new Select(driver.findElement(By.name("manufacturer_id")));
        var countManufactures = manufactures.getOptions().size();

        for (int i = 1; i < countManufactures; i++) {
            new Select(driver.findElement(By.name("manufacturer_id"))).selectByIndex(i);
            var countProducts = driver.findElements(By.cssSelector("[class ^= 'product']")).size();
            for (int j = 1; j <= countProducts; j++) {
                driver.findElement(
                        By.cssSelector(String.format("[class ^= 'product']:nth-child(%d)", j))).click();
                driver.navigate().back();
            }
            driver.navigate().to(baseUrl);
        }

        var logsAfter = driver.manage().logs().get("browser").getAll();
        if(logsBefore.size() > 0 && logsAfter.size() > 0) {
            logsAfter.removeAll(logsBefore);
        }
        assert logsAfter.size() == 0;
    }
}
