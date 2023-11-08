package pro.snikolaev.marketplace.parser.engine;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pro.snikolaev.marketplace.parser.engine.api.ParserEngine;

public class WildberriesParserEngine implements ParserEngine {

    private final String url;
    private final WebDriver webDriver;

    public WildberriesParserEngine(String url, WebDriver webDriver) {
        this.url = url;
        this.webDriver = webDriver;
    }

    @Override
    public Map<String, String> parse() {
        webDriver.get(url);
        WebElement priceElement = webDriver.findElement(By.className("price-block__final-price"));
        WebElement pageHeader = webDriver.findElement(By.className("product-page__header"));
        WebElement nameElement = pageHeader.findElement(By.tagName("h1"));
        WebElement sellerElement = webDriver.findElement(By.className("seller-info__name"));
        String name = nameElement.getText();
        String price = priceElement.getAttribute("innerHTML").trim();
        String seller = sellerElement.getText();
        webDriver.quit();

        Map<String, String> data = new HashMap<>(3, 1.0F);
        data.put("name", StringEscapeUtils.escapeJava(StringEscapeUtils.unescapeHtml4(name)));
        data.put("price", StringEscapeUtils.unescapeHtml4(price));
        data.put("seller", StringEscapeUtils.escapeJava(StringEscapeUtils.unescapeHtml4(seller)));
        return data;
    }
}
