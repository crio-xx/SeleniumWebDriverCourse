package admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {
    public static void auth(WebDriver driver, String url, String login, String password) {
        driver.get(url);
        WebElement username = driver.findElement(By.name("username"));
        username.sendKeys(login);

        WebElement pass = driver.findElement(By.name("password"));
        pass.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.name("login"));
        loginBtn.click();
    }
}
