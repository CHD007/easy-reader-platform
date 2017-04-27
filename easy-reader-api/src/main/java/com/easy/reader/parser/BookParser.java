package com.easy.reader.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * Парсер книги.
 * @author dchernyshov
 */
public class BookParser {
    private Parser parser;
    
    public BookParser(Parser parser) {
        this.parser = parser;
    }
    
    public Set<String> parse(InputStream in) throws IOException {
        return parser.parse(in);
    }
    
    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
