package ui.admin;

import admin.Login;
import org.junit.Test;
import org.openqa.selenium.By;

import ui.BaseTest;

public class RetourPages extends BaseTest {

    /*
     * Задание 7. Сделайте сценарий, проходящий по всем разделам админки
     */

    @Test
    public void TestFollowLink() {
        Login.auth(driver, baseUrl + "/admin", "admin", "admin");
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

}
