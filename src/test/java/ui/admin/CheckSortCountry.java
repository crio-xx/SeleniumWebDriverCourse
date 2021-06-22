package ui.admin;

import admin.Login;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class CheckSortCountry extends BaseTest {

    /*
     * Задание 9. Проверить сортировку стран и геозон в админке
     */

    @Test
    public void TestCheckSortCountries() {
        Login.auth(driver, baseUrl + "/admin", "admin", "admin");

        driver.navigate().to(baseUrl + "/admin/?app=countries&doc=countries");

        var table = driver.findElement(By.cssSelector("#content > [name = countries_form] > table"));
        var rows = table.findElements(By.cssSelector("tr"));

        // проверить, что страны расположены в алфавитном порядке
        System.out.println("Is countries sorted: " + isSorted(rows, 3));

        // для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке
        ArrayList<String> countriesWithZones = getRowsWhereValueNotZero(rows, 5, 3);

        for(String country: countriesWithZones) {
            driver.navigate().to(baseUrl + "/admin/?app=countries&doc=edit_country&country_code=" + country);
            var zonesRows = driver.findElements(By.cssSelector("#table-zones.dataTable tr"));
            System.out.printf("Is zones in %s sorted: %b%n", country, isSorted(zonesRows, 3));
        }
    }

    @Test
    public void TestCheckSortGeoZones() {
        Login.auth(driver, baseUrl + "/admin", "admin", "admin");

        driver.navigate().to(baseUrl + "/admin/?app=geo_zones&doc=geo_zones");

        var table = driver.findElement(By.cssSelector("#content > [name = geo_zones_form] > table"));
        var rows = table.findElements(By.cssSelector("tr"));

        // проверить, что геозоны расположены в алфавитном порядке
        System.out.println("Is geoZones sorted: " + isSorted(rows, 2));

        // для тех геозон, у которых количество зон отлично от нуля --  перейдем на страницу и проверим алфавитный порядок
        var rowsWithZones = getRowsWhereValueNotZero(rows, 4, 2);

        for(String zone: rowsWithZones) {
            driver.navigate().to(baseUrl + "/admin/?app=geo_zones&doc=edit_geo_zone&page=1&geo_zone_id=" + zone);
            var zonesRows = driver.findElements(By.cssSelector("#content > [name = form_geo_zone] > #table-zones tr"));
            System.out.printf("Is zones in %s sorted: %b%n", zone, isSorted(zonesRows, 1));
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

    private ArrayList<String> getRowsWhereValueNotZero(List<WebElement> rows, int keyColumnId, int returnColumnId){
        ArrayList<String> rowWithZones = new ArrayList<>();
        String keySelector = String.format("td:nth-child(%s)", keyColumnId);
        String returnSelector = String.format("td:nth-child(%s)", returnColumnId);


        for(var row: rows) {
            // пропуск "шапки" и "подвала" таблицы
            if(row.getAttribute("className").equals("header") || row.getAttribute("className").equals("footer")) {
                continue;
            }

            // проверка на сортировку строк по возрастанию
            var zones = row.findElement(By.cssSelector(keySelector)).getAttribute("textContent");
            if(!zones.equals("0")){
                rowWithZones.add(row.findElement(By.cssSelector(returnSelector)).getAttribute("textContent"));
            }
        }
        return rowWithZones;
    }
}
