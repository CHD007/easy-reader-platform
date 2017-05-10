package com.easy.reader.parser;

/**
 * Exception for parsing book.
 * @author dchernyshov
 */
public class BookParseException extends Exception {
    public BookParseException() {
        super();
    }
    
    public BookParseException(String message) {
        super(message);
    }
    
    public BookParseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BookParseException(Throwable cause) {
        super(cause);
    }
    
    public BookParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
