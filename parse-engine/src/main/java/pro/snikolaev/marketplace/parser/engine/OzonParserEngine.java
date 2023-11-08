package pro.snikolaev.marketplace.parser.engine;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pro.snikolaev.marketplace.parser.engine.api.ParserEngine;

public class OzonParserEngine implements ParserEngine {

    private final String url;
    private final WebDriver webDriver;

    public OzonParserEngine(String url, WebDriver webDriver) {
        this.url = url;
        this.webDriver = webDriver;
    }

    @Override
    public Map<String, String> parse() {
        webDriver.get(url);
        //WebElement priceElement = driver.findElement(By.xpath("//span[text()='c Ozon Картой']"));
        WebElement priceElement = webDriver.findElement(By.className("l0l"));
        //WebElement noCardPrice = priceElement.findElement(By.xpath("preceding-sibling::*[1]"));
        WebElement nameElement = webDriver.findElement(By.tagName("h1"));
        //WebElement sellerElement = driver.findElement(By.xpath("//a[@title and contains(@href, 'ozon.ru/seller/')]"));
        WebElement sellerElement = webDriver.findElement(By.className("j9p"));
        String name = nameElement.getText();
        String price = priceElement.getText();
        String seller = sellerElement.getAttribute("innerHTML").trim();
        webDriver.quit();

        Map<String, String> data = new HashMap<>(3, 1.0F);
        data.put("name", StringEscapeUtils.escapeJava(StringEscapeUtils.unescapeHtml4(name)));
        data.put("price", StringEscapeUtils.unescapeHtml4(price));
        data.put("seller", StringEscapeUtils.escapeJava(StringEscapeUtils.unescapeHtml4(seller)));
        return data;
    }
}
