package com.easy.reader.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Парсер.
 * @author dchernyshov
 */
public interface Parser {
    Map<String, String> parse(InputStream in) throws IOException;
}
