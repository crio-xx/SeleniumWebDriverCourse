package admin;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class CheckSortCountry extends BaseTest {

    @Test
    public void TestCheckSort() {
        Login.auth(driver, baseUrl + "admin/", "admin", "admin");

        driver.navigate().to(baseUrl + "admin/?app=countries&doc=countries");

        var table = driver.findElement(By.cssSelector("#content > [name = countries_form] > table"));
        var rows = table.findElements(By.cssSelector("tr"));

        // проверить, что страны расположены в алфавитном порядке
        System.out.println("Is countries sorted: " + isSorted(rows, 3));

        // для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке
        ArrayList<String> countriesWithZones = new ArrayList<>();

        for(var row: rows) {
            // пропуск "шапки" и "подвала" таблицы
            if(row.getAttribute("className").equals("header") || row.getAttribute("className").equals("footer")) {
                continue;
            }

            // проверка на сортировку строк по возрастанию
            var zones = row.findElement(By.cssSelector("td:nth-child(5)")).getAttribute("textContent");
            if(!zones.equals("0")){
                countriesWithZones.add(row.findElement(By.cssSelector("td:nth-child(3)")).getAttribute("textContent"));
            }
        }

        for(String country: countriesWithZones) {
            driver.navigate().to(baseUrl + "admin/?app=countries&doc=edit_country&country_code=" + country);
            var zonesRows = driver.findElements(By.cssSelector("#table-zones.dataTable tr"));
            System.out.printf("Is zones in %s sorted: %b%n", country, isSorted(zonesRows, 3));
        }



    }

    private boolean isSorted(List<WebElement> rows, int keyColumnId){
        String prev = null;
        for(var row: rows) {
            // пропуск "шапки" и "подвала" таблицы
            if(row.getAttribute("className").equals("header") || row.getAttribute("className").equals("footer")) {
                continue;
            }

            // проверка на сортировку строк по возрастанию
            var rowCell = row.findElements(By.cssSelector("td"));
            var name = rowCell.get(keyColumnId).getAttribute("textContent");
            if(prev != null && prev.compareTo(name) > 0){
                return false;
            }
            prev = name;
        }
        return true;
    }
}
