package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Word;

/**
 * DAO для слова
 * @author dchernyshov
 */
public class WordDao extends GenericJpaDao<Word, Long> {
    public WordDao() {
        super(Word.class);
    }
}
