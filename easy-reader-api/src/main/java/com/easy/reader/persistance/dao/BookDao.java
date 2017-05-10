package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Book;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Optional;

/**
 * DAO для книги
 * @author dchernyshov
 */
@Stateless
public class BookDao extends GenericDao<Book, Long> {
    public BookDao() {
        super(Book.class);
    }
    
    /**
     * Check if word already exist in database.
     * @param word word for checking
     * @return true if word exist, otherwise - false
     */
    public Optional<Book> findBookByName(String word) {
        try {
            return Optional.of(entityManager.createQuery("select w from " + getPersistentClass().getSimpleName() + " w where w.bookName = ?1", Book.class)
                    .setParameter(1, word)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
