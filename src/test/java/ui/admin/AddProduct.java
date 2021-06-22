package ui.admin;

import admin.Login;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import ui.BaseTest;
import utils.StringUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AddProduct extends BaseTest {

    /*
     * Задание 12. Сделайте сценарий добавления товара
     */

    @Test
    public void testAddProduct() {
        Login.auth(driver, baseUrl + "/admin", "admin", "admin");
        driver.findElement(By.cssSelector("#box-apps-menu [href $= catalog]")).click();
        driver.findElement(By.cssSelector("#content [href $= edit_product]")).click();

        // general
        driver.findElement(By.name("status")).click();
        String name = StringUtils.getRandomName(6);
        driver.findElement(By.name("name[en]")).sendKeys(name);
        driver.findElement(By.cssSelector("[data-name = Subcategory]")).click();

        driver.findElement(By.name("quantity")).sendKeys(StringUtils.getRandomNumber(3));
        new Select(driver.findElement(By.name("sold_out_status_id"))).selectByVisibleText("Temporary sold out");

        var image = new File("src/main/resources/new_image.jpg");
        driver.findElement(By.cssSelector("input[name ^=new_image]")).sendKeys(image.getAbsolutePath());

        // information
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("[href $= tab-information]")).click();

        new Select(driver.findElement(By.name("manufacturer_id"))).selectByVisibleText("ACME Corp.");
        driver.findElement(By.name("short_description[en]")).sendKeys(StringUtils.getRandomString(20));

        // prices
        driver.findElement(By.cssSelector("[href $= tab-prices]")).click();
        driver.findElement(By.name("purchase_price")).sendKeys(
                StringUtils.getRandomNumber(3) + "." + StringUtils.getRandomNumber(2));
        driver.findElement(By.name("prices[USD]")).sendKeys(
                StringUtils.getRandomNumber(3) + "." + StringUtils.getRandomNumber(2));

        driver.findElement(By.name("save")).click();

        var products = driver.findElements(By.xpath(String.format("//*[contains(text(), '%s')]", name)));
        assert products.size() == 1;
    }
}
