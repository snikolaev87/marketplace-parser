package pro.snikolaev.marketplace.parser.server.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pro.snikolaev.marketplace.parser.engine.ParserEngineFactory;

/**
 * Парсер маркетплейсов.
 */
public class ParsingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBodyStr = req.getReader().lines().collect(Collectors.joining());
        Map<String, String> reqParams = JSON.parseObject(reqBodyStr, Map.class);
        String itemPageUrl = reqParams.get("itemPage");
        if (itemPageUrl == null || itemPageUrl.isEmpty()) {
            throw new IllegalArgumentException("Не передан URL страницы");
        }
        Map<String, String> parsedData = ParserEngineFactory.getParserEngine(itemPageUrl).parse();
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");
        resp.getWriter().write(JSON.toJSONString(parsedData));
        resp.getWriter().close();
    }
}
