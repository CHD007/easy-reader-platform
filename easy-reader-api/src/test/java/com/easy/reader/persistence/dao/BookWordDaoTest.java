package com.easy.reader.persistence.dao;

import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.entity.BookWord;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
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
 * Тесты для WordBookDaoBean
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
@UsingDataSet({"datasets/books.yml", "datasets/words.yml", "datasets/users.yml", "datasets/bookWords.yml"})
public class BookWordDaoTest {
    @Inject
    private BookWordDao bookWordDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BookWord.class.getPackage())
                .addPackage(BookWordDao.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    public void findAllWordsByBookIdTest() {
        List<BookWord> allWordsForBook = bookWordDao.findAllWordsByBookId(1L);
        Assert.assertEquals(4, allWordsForBook.size());
    }
    
    @Test
    public void findAllWordsByBookIdAndUserIdTest() {
        List<BookWord> allWordsForBook = bookWordDao.findAllWordsByBookIdAndUserId(1L, 1L);
        Assert.assertEquals(4, allWordsForBook.size());
    }
}
