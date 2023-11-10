package pro.snikolaev.marketplace.parser.engine;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Парсер данных со сбермегамаркета.
 */
public class SberParserEngine extends SeleniumParserEngine {

    public SberParserEngine(String url, WebDriver webDriver) {
        super(url, webDriver);
    }

    @Override
    public Map<String, String> parse() {
        webDriver.get(url);
        WebElement priceElement = webDriver.findElement(By.className("sales-block-offer-price__price-final"));
        WebElement nameElement = webDriver.findElement(By.className("pdp-header__title_only-title"));
        WebElement sellerElement = findElementByClassNames(
                "pdp-offer-block__merchant-link", "pdp-merchant-rating-block__merchant-name");
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
