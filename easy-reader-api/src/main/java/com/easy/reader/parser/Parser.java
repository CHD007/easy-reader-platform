package com.easy.reader.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * Парсер.
 * @author dchernyshov
 */
public interface Parser {
    Set<String> parse(InputStream in) throws IOException;
}
