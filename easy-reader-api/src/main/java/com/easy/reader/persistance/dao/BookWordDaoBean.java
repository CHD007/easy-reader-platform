package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.Word;

import java.util.List;

/**
 * DAO для книжных слов
 * @author dchernyshov
 */
public class BookWordDaoBean extends GenericDaoBean<BookWord, Long>{
    public BookWordDaoBean() {
        super(BookWord.class);
    }
    
    /**
     * Находит все слова указанной книги.
     * @param bookId id книги, из которой нужно выбрать все слова
     * @return все слова указанной книги
     */
    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    public List<Word> getAllWordsForBook(Long bookId) {
        return entityManager
                .createQuery("select w from BookWord w where w.bookFk = ?1")
                .setParameter(1, bookId)
                .getResultList();
    }
}
