package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Word;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by dchernyshov on 29.03.17.
 */
@RunWith(Arquillian.class)
public class WordDaoBeanTest {
    @Inject
    private WordDaoBean wordDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Word.class)
                .addPackage(WordDaoBean.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    public void saveWordTest() {
        Word word = new Word();
        word.setWordName("say");
        word.setTranscription("fasdf");
        word.setTranslation("говорить");
        wordDao.save(word);
    }

    @Test
    public void saveAllWordsTest() {
        List<Word> words = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Word word = new Word();
            word.setWordName("word " + i);
            word.setTranslation("word transcription " + i);
            word.setTranslation("word translation " + i);
            words.add(word);
        }
        wordDao.saveAll(words);
    }
}
