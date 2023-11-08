package pro.snikolaev.marketplace.parser.server;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import pro.snikolaev.marketplace.parser.server.handlers.ParseHandler;

public class ApplicationServer {

    public static void main(String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8090, "localhost")
                .setHandler(Handlers.path()
                        .addPrefixPath("/",
                                Handlers.resource(new ClassPathResourceManager(ApplicationServer.class.getClassLoader(), "html")).addWelcomeFiles("index.html"))
                        .addPrefixPath("/js",
                                Handlers.resource(new ClassPathResourceManager(ApplicationServer.class.getClassLoader(), "js")))
                        .addExactPath("/parse",
                                new ParseHandler())
                )
                .build();

        server.start();
    }
}
