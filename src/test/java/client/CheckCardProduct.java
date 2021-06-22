package client;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import ui.BaseTest;
import org.openqa.selenium.support.Color;

import static org.junit.Assert.*;

public class CheckCardProduct extends BaseTest {

    @Test
    public void testCardProduct() {
        driver.navigate().to(baseUrl);

        var firstProductCampaigns= driver.findElement(By.cssSelector("#box-campaigns [class ^= 'product']:nth-child(1)"));
        var productName = firstProductCampaigns.findElement(By.className("name")).getAttribute("textContent");

        var productRegularPrice = firstProductCampaigns.findElement(By.className("regular-price"));
        var productRegularPriceValue = productRegularPrice.getAttribute("textContent");

        var productCampaignPrice = firstProductCampaigns.findElement(By.className("campaign-price"));
        var productCampaignPriceValue = productCampaignPrice.getAttribute("textContent");

        boolean thirdConditionMainPage = isGray(productRegularPrice) && isStrikethroughByTag(productRegularPrice);
        boolean fourthConditionMainPage = isRed(productCampaignPrice) && isThickByTag(productCampaignPrice);
        boolean fifthConditionMainPage = isFirstGreaterSecond(productCampaignPrice.getSize(), productRegularPrice.getSize());

        firstProductCampaigns.click();

        var productCard = driver.findElement(By.cssSelector("#box-product"));
        var productNameCard = productCard.findElement(By.cssSelector("[itemprop = name]")).getText();

        var productRegularPriceCard = driver.findElement(By.className("regular-price"));
        var productRegularPriceCardValue = productRegularPriceCard.getText();

        var productCampaignPriceCard = productCard.findElement(By.className("campaign-price"));
        var productCampaignPriceCardValue = productCampaignPriceCard.getText();


        assertEquals("Первое условие из задания", productName, productNameCard);
        assertEquals("Второе условие из задания (обычная цена)", productRegularPriceValue, productRegularPriceCardValue);
        assertEquals("Второе условие из задания (цена по скидке)", productCampaignPriceValue, productCampaignPriceCardValue);

        assertTrue("Третье условие из задания на главной странице", thirdConditionMainPage);
        assertTrue("Четвертое условие из задания на главной странице", fourthConditionMainPage);
        assertTrue("Пятое условие из задания на главной странице", fifthConditionMainPage);

        assertTrue("Третье условие из задания на странице товара",
                isGray(productRegularPriceCard) && isStrikethroughByTag(productRegularPriceCard)
        );

        assertTrue("Четвертое условие из задания на странице товара",
                isRed(productCampaignPriceCard) && isThickByTag(productCampaignPriceCard)
        );

        assertTrue("Пятое условие из задания на странице товара",
                isFirstGreaterSecond(productCampaignPriceCard.getSize(), productRegularPriceCard.getSize())
        );

    }

    private boolean isRed(WebElement element) {
        Color color = Color.fromString(element.getCssValue("color"));
        return color.getColor().getGreen() == 0 && color.getColor().getBlue() == 0;
    }

    private boolean isGray(WebElement element) {
        Color color = Color.fromString(element.getCssValue("color"));
        return color.getColor().getRed() == color.getColor().getGreen() && color.getColor().getGreen() == color.getColor().getBlue();
    }

    private boolean isStrikethroughByTag(WebElement element){
        return element.getTagName().equals("s");
    }

    private boolean isThickByTag(WebElement element){
        return element.getTagName().equals("strong");
    }

    private boolean isFirstGreaterSecond(Dimension first, Dimension second) {
        return first.getHeight() > second.getHeight() && first.getWidth() > second.getWidth();
    }
}
