package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.entity.Book;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Тесты для BookDao
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
@UsingDataSet("datasets/books.yml")
public class BookDaoTest {
    @Inject
    private BookDao bookDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Book.class)
                .addPackage(BookDao.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    public void findAllBooks() {
        bookDao.findAll();
    }
}
