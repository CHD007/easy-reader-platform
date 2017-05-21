package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.Status;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DAO для книжных слов
 *
 * @author dchernyshov
 */
@Stateless
public class BookWordDao extends GenericDao<BookWord, Long> {
    public BookWordDao() {
        super(BookWord.class);
    }

    /**
     * Находит все слова указанной книги.
     *
     * @param bookId id книги, из которой нужно выбрать все слова
     * @return все слова указанной книги
     */
    public List<BookWord> findAllWordsByBookId(Long bookId) {
        return entityManager.createQuery("select w from " + getPersistentClass().getSimpleName() + " w where w.bookFk.id = ?1", BookWord.class)
                .setParameter(1, bookId)
                .getResultList();
    }

    /**
     * Gets the total number of learned words in book
     * @param bookId - id of the book
     * @return - number of learned words
     */
    public long findNumberOfWordsByStatusInBook(Long bookId, Status status) {
        return entityManager.createQuery("SELECT COUNT(words.id) FROM " + getPersistentClass().getSimpleName() + " words " +
                "WHERE words.status = :status AND words.bookFk.id = :id", Long.class)
                .setParameter("status", status)
                .setParameter("id", bookId)
                .getSingleResult();
    }

    /**
     * Gets the total number of words in the book
     *
     * @param bookId - id of the book to get words from
     * @return - number of words in book
     */
    public long findTotalNumberOfWordsInBook(Long bookId) {
        return entityManager.createQuery("SELECT COUNT(words.id) " + getPersistentClass().getSimpleName() + " words", Long.class)
                .getSingleResult();
    }

    public List<BookWord> findAllWordsByBookIdAndUserId(Long bookId, Long userId) {
        return entityManager.createQuery("select w from " + getPersistentClass().getSimpleName() +
                " w where w.bookFk.id = ?1 and w.userFk.id = ?2", BookWord.class)
                .setParameter(1, bookId)
                .setParameter(2, userId)
                .getResultList();
    }

}
