package com.easy.reader.parser;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Парсер книги.
 * @author dchernyshov
 */
@NoArgsConstructor
public class BookParser {
    private Parser parser;
    
    public BookParser(Parser parser) {
        this.parser = parser;
    }
    
    public Map<String, String> parse(InputStream in) throws IOException {
        return parser.parse(in);
    }
    
    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
