package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Word;

import javax.ejb.Stateless;

/**
 * DAO для слова
 * @author dchernyshov
 */
@Stateless
public class WordDao extends GenericDao<Word, Long> {
    public WordDao() {
        super(Word.class);
    }
}
