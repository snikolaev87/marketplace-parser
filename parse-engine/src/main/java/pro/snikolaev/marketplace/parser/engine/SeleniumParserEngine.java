package pro.snikolaev.marketplace.parser.engine;

import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pro.snikolaev.marketplace.parser.engine.api.ParserEngine;

/**
 * Общий класс для парсеров, использующих Selenium.
 */
public abstract class SeleniumParserEngine implements ParserEngine {

    private static final String DATA_NOT_FOUND = "Данные отсутствуют";

    protected final String url;
    protected final WebDriver webDriver;

    /**
     * Конструктор.
     *
     * @param url       url-адрес
     * @param webDriver веб-драйвер
     */
    public SeleniumParserEngine(String url, WebDriver webDriver) {
        this.url = url;
        this.webDriver = webDriver;
    }

    protected WebElement findElementByClassNames(String... classNames) {
        for (String className : classNames) {
            try {
                 return webDriver.findElement(By.className(className));
            } catch (NoSuchElementException e) {
                // перебираем дальше
            }
        }
        throw new NoSuchElementException("Элемент не найден");
    }

    protected String getTextFromElement(WebElement element) {
        String text = element.getText();
        if (text == null || text.trim().isEmpty()) {
            text = element.getAttribute("innerHTML");
            if (text != null && !text.trim().isEmpty()) {
                text = StringEscapeUtils.unescapeHtml4(text.trim());
            } else {
                text = DATA_NOT_FOUND;
            }
        }
        return text.replaceAll("<[^>]*>", "");
    }

    protected String sanitizePrice(String price) {
        if (DATA_NOT_FOUND.equals(price)) {
            return price;
        }
        return price.replaceAll("<[^>]*>", "").replaceAll("\\D+", "");
    }
}
