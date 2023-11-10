package pro.snikolaev.marketplace.parser.engine;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pro.snikolaev.marketplace.parser.engine.api.ParserEngine;

/**
 * Фабрика парсеров.
 */
public class ParserEngineFactory {

    public static final String WILDBERRIES = "wildberries.ru";
    public static final String OZON = "ozon.ru";
    public static final String YANDEX = "market.yandex.ru";
    public static final String SBER = "megamarket.ru";

    /**
     * Возвращает подходящий парсер.
     *
     * @param pageUrl url-адрес
     *
     * @return соответствующий парсер
     */
    public static ParserEngine getParserEngine(String pageUrl) {
        if (pageUrl == null || pageUrl.isEmpty()) {
            throw new IllegalArgumentException("Не передан URL страницы");
        }
        if (pageUrl.contains(WILDBERRIES)) {
            return new WildberriesParserEngine(pageUrl, getChromeDriver());
        } else if (pageUrl.contains(OZON)) {
            return new OzonParserEngine(pageUrl, getChromeDriver());
        } else if (pageUrl.contains(YANDEX)) {
            return new YandexParserEngine(pageUrl, getChromeDriver());
        } else if (pageUrl.contains(SBER)) {
            return new SberParserEngine(pageUrl, getFirefoxDriver());
        } else {
            throw new UnsupportedOperationException("Неизвестный URL");
        }
    }

    private static WebDriver getChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("116");
        options.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        return driver;
    }

    private static WebDriver getFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.setBrowserVersion("117");
        options.addArguments("--headless");
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        return driver;
    }
}
