package client;

import org.junit.Test;
import org.openqa.selenium.By;
import ui.BaseTest;

public class CheckSticker extends BaseTest {

    @Test
    public void TestCheckStrickers() {
        driver.navigate().to(baseUrl);
        var mainContent = driver.findElement(By.cssSelector("#main > div.middle > div.content"));

        var listGoods = mainContent.findElements(By.cssSelector("[class ^= 'product']"));
        for(var el : listGoods){
            int count = el.findElements(By.cssSelector("[class ^= sticker]")).size();
            assert count == 1;
        }
    }
}
