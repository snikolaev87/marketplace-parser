package pro.snikolaev.marketplace.parser.server.handlers;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import io.undertow.io.Receiver;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.commons.text.StringEscapeUtils;

import pro.snikolaev.marketplace.parser.engine.ParserEngineFactory;

public class ParseHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Map<String, String> info = new HashMap<>();
        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
            @Override
            public void handle(HttpServerExchange httpServerExchange, String s) {
                try {
                    String pageUrl = s.substring(s.indexOf("http"), s.lastIndexOf("\""));
                    info.putAll(ParserEngineFactory.getParserEngine(pageUrl).parse());
                } catch (Exception e) {
                    info.put("error", e.getMessage());
                }
            }
        }, StandardCharsets.UTF_8);

        exchange.getResponseHeaders().put(
                Headers.CONTENT_TYPE, "application/json");
        String response;
        if (info.get("error") == null) {
            response = String.format("{\"name\":\"%s\",\"price\":\"%s\",\"seller\":\"%s\"}",
                    info.get("name"),
                    info.get("price"),
                    info.get("seller"));
        } else {
            response = String.format("{\"error\":\"%s\"}", StringEscapeUtils.escapeJava(info.get("error")));
        }
        exchange.getResponseSender().send(response);
    }
}
