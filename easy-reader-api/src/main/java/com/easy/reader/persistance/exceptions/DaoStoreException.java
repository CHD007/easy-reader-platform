package com.easy.reader.persistance.exceptions;

/**
 * Кастомное исключение для DAO-объектов. Создано для
 * удобства.
 * @author dchernyshov
 */
public class DaoStoreException extends Exception {
    public DaoStoreException() {
        super();
    }

    public DaoStoreException(String message) {
        super(message);
    }

    public DaoStoreException(Throwable cause) {
        super(cause);
    }

    public DaoStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
