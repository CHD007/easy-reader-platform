package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Status;
import com.easy.reader.persistance.entity.UserWord;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DAO для пользовательских слов
 * @author dchernyshov
 */
@Stateless
public class UserWordDao extends GenericDao<UserWord, Long> {
    public UserWordDao() {
        super(UserWord.class);
    }
    
    public List<UserWord> findAllWordByUserId(Long userId) {
        return entityManager
                .createQuery("select uw from UserWord uw where uw.userFk.id = ?1", UserWord.class)
                .setParameter(1, userId)
                .getResultList();
    }
    
    /**
     * Находит все слова пользователя из конкретной книги.
     * @param bookId id книги, из которой нужно найти пользовательские слова
     * @return слова пользователя из заданной книги
     */
    public List<UserWord> findAllUserWordsByBookId(Long userId, Long bookId) {
        return entityManager
                .createQuery("select uw from UserWord uw where uw.userFk.id = ?1 and uw.bookWordFk.bookFk.id = ?2", UserWord.class)
                .setParameter(1, userId)
                .setParameter(2, bookId)
                .getResultList();
    }
    
    /**
     * Находит все слова выученные слова пользователя.
     * @param userId id пользователя, для которого находим слова
     * @return слова пользователя
     */
    public List<UserWord> findAllLeanedUserWordsByUserId(Long userId) {
        return entityManager
                .createQuery("select uw from UserWord uw where uw.status = ?1", UserWord.class)
                .setParameter(1, Status.LEARNED)
                .getResultList();
    }
    
    /**
     * Находит все слова невыученные слова пользователя.
     * @param userId id пользователя, для которого находим слова
     * @return слова пользователя
     */
    public List<UserWord> findAllUserWordsInProgressByUserId(Long userId) {
        return entityManager
                .createQuery("select uw from UserWord uw where uw.status = ?1", UserWord.class)
                .setParameter(1, Status.IN_PROGRESS)
                .getResultList();
    }
}
