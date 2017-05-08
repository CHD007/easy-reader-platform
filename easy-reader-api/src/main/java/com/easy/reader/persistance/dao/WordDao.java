package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Word;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Optional;

/**
 * DAO для слова
 * @author dchernyshov
 */
@Stateless
public class WordDao extends GenericDao<Word, Long> {
    private static final Logger LOGGER = Logger.getLogger(WordDao.class);
    public WordDao() {
        super(Word.class);
    }

    /**
     * Check if word already exist in database.
     * @param word word for checking
     * @return true if word exist, otherwise - false
     */
    public Optional<Word> findWordByName(String word) {
        try {
            return Optional.of(entityManager.createQuery("select w from " + getPersistentClass().getSimpleName() + " w where w.wordName = ?1", Word.class)
                    .setParameter(1, word)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
