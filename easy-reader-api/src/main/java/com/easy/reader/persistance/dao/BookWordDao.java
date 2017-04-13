package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.BookWord;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DAO для книжных слов
 * @author dchernyshov
 */
@Stateless
public class BookWordDao extends GenericDao<BookWord, Long> {
    public BookWordDao() {
        super(BookWord.class);
    }
    
    /**
     * Находит все слова указанной книги.
     * @param bookId id книги, из которой нужно выбрать все слова
     * @return все слова указанной книги
     */
    public List<BookWord> findAllWordsByBookId(Long bookId) {
        return entityManager.createQuery("select w from BookWord w where w.bookFk.id = ?1", BookWord.class)
                .setParameter(1, bookId)
                .getResultList();
    }
}
