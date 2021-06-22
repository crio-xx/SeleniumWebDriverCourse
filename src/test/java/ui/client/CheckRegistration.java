package ui.client;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import ui.BaseTest;
import utils.StringUtils;

public class CheckRegistration extends BaseTest {

    /*
     * Задание 11. Сделайте сценарий регистрации пользователя
     */

    @Test
    public void testRegistration() {
        String firstName = StringUtils.getRandomName(5);
        String lastName = StringUtils.getRandomName(7);
        String address = StringUtils.getRandomName(10) + ", " + StringUtils.getRandomName(6) + ", №" + StringUtils.getRandomNumber(3);
        String postcode = StringUtils.getRandomNumber(5);
        String city = StringUtils.getRandomName(6);
        String email = StringUtils.getRandomString(10) + "@" + StringUtils.getRandomString(5) + ".com";
        String password = StringUtils.getRandomString(4) + StringUtils.getRandomNumber(4) + StringUtils.getRandomString(4);
        String phone = "+"+StringUtils.getRandomNumber(11);
        String country = "United States";
        String zone = "California";

        driver.navigate().to(baseUrl + "/create_account");

        // user data entry for registration
        driver.findElement(By.name("firstname")).sendKeys(firstName);
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("address1")).sendKeys(address);
        driver.findElement(By.name("postcode")).sendKeys(postcode);
        driver.findElement(By.name("city")).sendKeys(city);
        driver.findElement(By.name("phone")).sendKeys(phone);
        new Select(driver.findElement(By.name("country_code"))).selectByVisibleText(country);
        new Select(driver.findElement(By.name("zone_code"))).selectByVisibleText(zone);
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);


        // click Register
        driver.findElement(By.name("create_account")).click();

        // logout
        driver.findElement(By.cssSelector("#box-account [href $= logout]")).click();

        // login
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }
}
