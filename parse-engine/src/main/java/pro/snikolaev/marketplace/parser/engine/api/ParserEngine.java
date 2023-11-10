package pro.snikolaev.marketplace.parser.engine.api;

import java.util.Map;

/**
 * Интерфейс парсеров данных с маркетплейсов.
 */
public interface ParserEngine {

    /**
     * Собирает интересующие данные с маркетплейса.
     *
     * @return собранные данные
     */
    Map<String, String> parse();

}
