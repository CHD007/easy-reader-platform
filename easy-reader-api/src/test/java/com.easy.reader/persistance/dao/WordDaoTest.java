package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.EntityManagerEJB;
import com.easy.reader.persistance.entity.Word;
import com.easy.reader.persistance.exceptions.DaoStoreException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dchernyshov on 29.03.17.
 */
public class WordDaoTest {

    @Test
    public void saveWordTest() {
        Word word = new Word();
        word.setWordName("say");
        word.setTranscription("sā");
        word.setTranslation("говорить");
        WordDao wordDao = new WordDao();
        try {
            Word saved = wordDao.save(word);
            assertNotNull(saved);
        } catch (DaoStoreException daoExc) {
            fail(daoExc.getMessage());
        }
    }
}
