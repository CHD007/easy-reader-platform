package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.UserWord;
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
 * Тесты для UserBookDaoBean
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
@UsingDataSet({"datasets/books.yml", "datasets/words.yml", "datasets/users.yml", "datasets/bookWords.yml",
        "datasets/userWords.yml"})
public class UserWordDaoTest {
    @Inject
    private UserWordDao userWordDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(UserWord.class)
                .addPackage(WordDao.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    public void findAllWordsByUserIdTest() {
        List<UserWord> allWordsForUser = userWordDao.findAllWordByUserId(1L);
        Assert.assertEquals(4, allWordsForUser.size());
    }
}
