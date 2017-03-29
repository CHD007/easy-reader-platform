package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Word;

import javax.persistence.EntityManager;

/**
 * Created by dchernyshov on 29.03.17.
 */
public class WordDao extends GenericJpaDao<Word, Long> {
    public WordDao(EntityManager entityManager) {
        super(Word.class, entityManager);
    }


}
