package com.easy.reader.persistence.dao;

import com.easy.reader.persistance.dao.Service;
import com.easy.reader.persistance.dao.UserWordDao;
import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.dto.WordDto;
import com.easy.reader.persistance.entity.UserWord;
import org.apache.log4j.Logger;
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
 * Тесты для сервиса получения dto слов.
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
public class ServiceTest {
    private static final Logger LOGGER = Logger.getLogger(ServiceTest.class);
    @Inject
    private Service service;
    @Inject
    private UserWordDao userWordDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UserWord.class.getPackage())
                .addPackage(WordDao.class.getPackage())
                .addPackage(Service.class.getPackage())
                .addPackage(WordDto.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    @UsingDataSet({"datasets/books.yml", "datasets/words.yml", "datasets/users.yml", "datasets/bookWords.yml",
            "datasets/userWords.yml"})
    @Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
    public void findAllWordsByUserIdTest() {
        List<WordDto> allWordsForUser = service.getAllWords();
        Assert.assertEquals(4, allWordsForUser.size());
    }
}
