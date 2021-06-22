package ui.admin;

import admin.Login;
import org.junit.Test;
import org.openqa.selenium.By;
import ui.BaseTest;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckBrowserLogs extends BaseTest {

    /*
     * Задание 17. Проверьте отсутствие сообщений в логе браузера
     */

    @Test
    public void TestCheckBrowserLogs() {
        HashSet<String> viewedCategory = new HashSet<>();
        HashSet<String> viewedProduct = new HashSet<>();
        Queue<String> categoryIds = new LinkedList<>();

        String catalogUrl = baseUrl + "/admin/?app=catalog&doc=catalog";
        Login.auth(driver, catalogUrl, "admin", "admin");

        // запустим алгоритм поиска с корневого каталога
        categoryIds.add("category_id=0");

        do {

            // извлекаем из очереди элемент, продукты которого будут рассмотрены на этом шаге
            String currentCategory = categoryIds.remove();
            // переходим в рассматриваемую категорию
            driver.navigate().to(catalogUrl + "&" + currentCategory);

            // поиск категорий, которые еще не были просмотрены
            var links = driver.findElements(By.cssSelector("#content table tr td:nth-child(2) [href]"));
            for (var link : links) {
                String categoryId = getCategoryId(link.getAttribute("href"));
                if (categoryId != null && !viewedCategory.contains(categoryId) && !categoryIds.contains(categoryId)) {
                    categoryIds.add(categoryId);
                }
            }

            // поиск продуктов, страницы которых еще не были открыты и проверены на ошибки в консоли
            List<String> productsInCurrentCategory = new LinkedList<>();
            var products = driver.findElements(By.cssSelector(
                    String.format("#content table td:nth-child(2) [href *= '%s&product_id='] ", currentCategory)));

            for(var product : products) {
                productsInCurrentCategory.add(product.getAttribute("href"));
            }

            // проход по карточке каждого товара и проверка консоли. После прохода помечаем продукт просмотренным
            for (var productLink : productsInCurrentCategory) {
                if(!viewedProduct.contains(productLink)) {
                    checkBrowserLog(productLink);
                    viewedProduct.add(productLink);
                }
            }

            // помечаем категорию просмотренной
            viewedCategory.add(currentCategory);

        // повторяем, пока не закончатся непросмотренные категории
        } while (!categoryIds.isEmpty());

    }

    private String getCategoryId(String href) {
        Pattern pattern = Pattern.compile("category_id=\\d$");
        Matcher matcher = pattern.matcher(href);
        if (matcher.find()) {
            return href.substring(matcher.start(), matcher.end());
        }
        return null;
    }

    private void checkBrowserLog(String href) {
        driver.navigate().to(href);
        assert driver.manage().logs().get("browser").getAll().size() == 0;
        driver.navigate().back();
    }
}
