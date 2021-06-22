package client;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ui.BaseTest;

public class CheckShoppingBasket extends BaseTest {

    @Test
    public void testShoppingBasket() {
        driver.navigate().to(baseUrl);

        int quantity = getQuantityInCart(driver);

        while (quantity < 3) {
            // открываем страничку первого товара на странице
            driver.findElements(By.cssSelector(".content [class ^= product] .link")).get(quantity).click();

            // выбираем размер продукта, если есть
            var selects = driver.findElements(By.name("options[Size]"));
            if(selects.size() > 0){
                new Select(selects.get(0)).selectByIndex(1);
            }

            //  кликаем на "добавить в корзину"
            driver.findElement(By.name("add_cart_product")).click();
            quantity += 1;

            // ждем обновления счётчика корзины
            String xpathSelector = String.format("//span[@class='quantity'][text()='%d']", quantity);
            wait.until((WebDriver d) -> d.findElement(By.xpath(xpathSelector)));

            // возврат на главную страницу
            driver.navigate().to(baseUrl);
        }
        assert quantity == getQuantityInCart(driver);

        // переходим в корзину
        driver.findElement(By.cssSelector("[href $= checkout].content")).click();

        while(quantity > 0) {

            var firstCarouselElement = driver.findElements(By.cssSelector("#box-checkout-cart > ul li:first-child > [href]"));
            if(firstCarouselElement.size() > 0) {
                // фиксируем первый элемент в карусели
                firstCarouselElement.get(0).click();
            }

            // получаем имя удаляемого элемента
            var name = driver.findElement(By.cssSelector("li.item:first-child strong")).getText();

            // находим строку в таблице, соответствующую удаляемому элементу
            var row = driver.findElement(
                    By.xpath(String.format("//*[@id='box-checkout-summary']//*[text()='%s']/../td/..", name)));

            var quantityCurrentElement = Integer.parseInt(row.findElement(By.cssSelector("td:first-child")).getText());

            // удаляем элемент
            driver.findElement(By.name("remove_cart_item")).click();
            quantity -= quantityCurrentElement;

            // должна пропасть строка из таблицы
            wait.until(ExpectedConditions.stalenessOf(row));
        }
        wait.until((WebDriver d) -> d.findElement(By.xpath("//em[contains(., 'There are no items in your cart')]")));
    }

    public static int getQuantityInCart(WebDriver driver) {
        return Integer.parseInt(driver.findElement(By.cssSelector("span.quantity")).getText());
    }
}
