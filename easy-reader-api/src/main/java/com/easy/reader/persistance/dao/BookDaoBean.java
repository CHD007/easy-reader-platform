package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Book;

/**
 * DAO для книги
 * @author dchernyshov
 */
public class BookDaoBean extends GenericDaoBean<Book, Long> {
    public BookDaoBean() {
        super(Book.class);
    }
}
