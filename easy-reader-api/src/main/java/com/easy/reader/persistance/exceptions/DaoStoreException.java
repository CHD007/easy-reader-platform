package com.easy.reader.persistance.exceptions;

import lombok.NoArgsConstructor;

/**
 * Кастомное исключение для DAO-объектов. Создано для
 * удобства.
 * @author dchernyshov
 */
@NoArgsConstructor
public class DaoStoreException extends Exception {
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
