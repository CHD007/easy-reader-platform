package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Word;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Тесты для BookDaoBean
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
@UsingDataSet("datasets/words.yml")
public class BookDaoBeanTest {
    @Inject
    private BookDaoBean bookDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(Word.class.getPackage())
                .addPackage(WordDaoBean.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
}
