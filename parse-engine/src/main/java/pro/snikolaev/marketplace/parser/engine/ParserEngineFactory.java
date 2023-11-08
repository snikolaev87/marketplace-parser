package pro.snikolaev.marketplace.parser.engine;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pro.snikolaev.marketplace.parser.engine.api.ParserEngine;

public class ParserEngineFactory {

    public static final String WILDBERRIES = "wildberries.ru";
    public static final String OZON = "ozon.ru";

    public static ParserEngine getParserEngine(String pageUrl) {
        if (pageUrl == null || pageUrl.isEmpty()) {
            throw new IllegalArgumentException("Не передан URL страницы");
        }
        if (pageUrl.contains(WILDBERRIES)) {
            return new WildberriesParserEngine(pageUrl, getWebDriver());
        } else if (pageUrl.contains(OZON)) {
            return new OzonParserEngine(pageUrl, getWebDriver());
        } else {
            throw new UnsupportedOperationException("Неизвестный URL");
        }
    }

    private static WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("116");
        options.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        return driver;
    }
}
