package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.BaseEntity;
import com.easy.reader.persistance.entity.Word;
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
public class GenericDaoTest {
    @Inject
    private GenericDao genericDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Word.class)
                .addPackage(WordDao.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    public void saveTest() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setId(1);
        genericDao.save(baseEntity);
    
        BaseEntity wordDaoById = genericDao.findById(1L);
        Assert.assertEquals(baseEntity, wordDaoById);
    }

    @Test
    @ShouldMatchDataSet(value = "expected-words.yml", excludeColumns = { "id" })
    public void saveAllWordsTest() {
        List<Word> words = genericDao.findAll();
        for (Word word : words) {
            genericDao.delete(word);
        }
        genericDao.saveAll(words);
    }
    
    @Test
    @ShouldMatchDataSet(value = "expected-words.yml", excludeColumns = { "id" })
    public void findAllTest() {
        List<Word> all = genericDao.findAll();
    }
    
    @Test
    public void updateTest() {
        Long expectedId = 2L;
        BaseEntity baseEntity = genericDao.findById(1L);
        baseEntity.setId(2L);
        genericDao.update(baseEntity);
    
        Assert.assertEquals(expectedId.intValue(), baseEntity.getId());
    }
    
    @Test
    public void deleteTest() {
        genericDao.delete(genericDao.findById(2L));
        Assert.assertEquals(3, genericDao.findAll().size());
    }
}
