package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.UserWord;
import com.easy.reader.persistance.entity.Word;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
@UsingDataSet({"datasets/userWords.yml", "datasets/words.yml", "datasets/users.yml"})
public class UserWordDaoTest {
    @Inject
    private UserWordDao userWordDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(Word.class.getPackage())
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
