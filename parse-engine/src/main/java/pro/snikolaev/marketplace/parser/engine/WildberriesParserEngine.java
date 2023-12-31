package pro.snikolaev.marketplace.parser.engine;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Парсер данных с wildberries.
 */
public class WildberriesParserEngine extends SeleniumParserEngine {

    public WildberriesParserEngine(String url, WebDriver webDriver) {
        super(url, webDriver);
    }

    @Override
    public Map<String, String> parse() {
        webDriver.get(url);
        WebElement itemCardHeader = webDriver.findElement(By.className("product-page__header"));
        WebElement nameElement = itemCardHeader.findElement(By.tagName("h1"));
        WebElement priceElement = webDriver.findElement(By.className("price-block__final-price"));
        WebElement sellerElement = webDriver.findElement(By.className("seller-info__name"));
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
