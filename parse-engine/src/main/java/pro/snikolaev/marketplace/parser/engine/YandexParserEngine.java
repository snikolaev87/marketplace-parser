package pro.snikolaev.marketplace.parser.engine;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Парсер данных с яндексмаркета.
 */
public class YandexParserEngine extends SeleniumParserEngine {

    public YandexParserEngine(String url, WebDriver webDriver) {
        super(url, webDriver);
    }

    @Override
    public Map<String, String> parse() {
        webDriver.get(url);
        WebElement priceElement = webDriver.findElement(By.xpath("//*[@data-auto='snippet-price-current']"));
        WebElement nameElement = webDriver.findElement(By.xpath("//*[@data-auto='productCardTitle']"));
        WebElement sellerElement = webDriver.findElement(By.xpath("//div[@data-walter-collection='supplierName']"));
        String name = getTextFromElement(nameElement);
        String price = sanitizePrice(getTextFromElement(priceElement));
        String seller = getTextFromElement(sellerElement);
        webDriver.quit();

        Map<String, String> data = new HashMap<>(3, 1.0F);
        data.put("name", name);
        data.put("price", price);
        data.put("seller", seller);
        return data;
    }


}
