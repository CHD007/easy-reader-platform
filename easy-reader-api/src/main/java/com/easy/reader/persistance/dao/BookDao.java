package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Book;

import javax.ejb.Stateless;

/**
 * DAO для книги
 * @author dchernyshov
 */
@Stateless
public class BookDao extends GenericDao<Book, Long> {
    public BookDao() {
        super(Book.class);
    }
}
