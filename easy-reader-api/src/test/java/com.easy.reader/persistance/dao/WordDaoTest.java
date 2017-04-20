package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * Created by dchernyshov on 29.03.17.
 */
@RunWith(Arquillian.class)
@UsingDataSet("datasets/words.yml")
public class WordDaoTest {
    @Inject
    private WordDao wordDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BookWord.class.getPackage())
                .addPackage(BookWordDao.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    
    @Test
    public void saveWordTest() {
        Word word = new Word();
        word.setId(1);
        word.setWordName("say");
        word.setTranscription("fasdf");
        word.setTranslation("говорить");
        wordDao.save(word);
    
        Word wordDaoById = wordDao.findById(1L);
        Assert.assertEquals(word, wordDaoById);
    }

    @Test
    @ShouldMatchDataSet(value = "expected-words.yml", excludeColumns = { "id" })
    public void saveAllWordsTest() {
        List<Word> words = wordDao.findAll();
        for (Word word : words) {
            wordDao.delete(word);
        }
        wordDao.saveAll(words);
    }

    @Test
    @ShouldMatchDataSet(value = "expected-words.yml", excludeColumns = { "id" })
    public void findAllWordsTest() {
        List<Word> all = wordDao.findAll();
    }
    
    @Test
    public void updateWordTest() {
        String expectedTranslation = "changed";
        Word word = wordDao.findById(1L);
        word.setTranslation("changed");
        wordDao.update(word);
    
        Assert.assertEquals(expectedTranslation, word.getTranslation());
    }
    
    @Test
    public void deleteWordTest() {
        wordDao.delete(wordDao.findById(2L));
        Assert.assertEquals(3, wordDao.findAll().size());
    }
}
