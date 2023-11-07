package pro.snikolaev.marketplace.parser.server;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.util.Headers;

public class ApplicationServer {

    /*
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        WebDriver driver = new ChromeDriver();
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://www.wildberries.ru/catalog/170647507/detail.aspx?targetUrl=GP");
        WebElement priceElement = driver.findElement(By.className("price-block__final-price"));
        String price = priceElement.getAttribute("innerHTML").trim();
        System.out.println(price);

        driver.quit();
     */
    public static void main(String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8090, "localhost")
                .setHandler(Handlers.path()
                        .addPrefixPath("/", Handlers.resource(new ClassPathResourceManager(ApplicationServer.class.getClassLoader(), "html"))
                                .addWelcomeFiles("index.html"))
                        .addPrefixPath("/js", Handlers.resource(new ClassPathResourceManager(ApplicationServer.class.getClassLoader(), "js")))
                )
                .build();

        server.start();
    }
}
