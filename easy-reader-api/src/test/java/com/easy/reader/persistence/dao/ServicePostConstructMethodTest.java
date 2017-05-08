package com.easy.reader.persistence.dao;

import com.easy.reader.persistance.dao.Service;
import com.easy.reader.persistance.dao.UserWordDao;
import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.dto.WordDto;
import com.easy.reader.persistance.entity.UserWord;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

/**
 * Тест для сервиса получения dto слов.
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class ServicePostConstructMethodTest {
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
    public void populateDatabaseTest() {
        List<WordDto> allWords = service.getAllWords();
        Assert.assertEquals(10, allWords.size());
    }
}
