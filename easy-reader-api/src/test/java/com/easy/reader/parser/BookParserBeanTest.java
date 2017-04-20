package com.easy.reader.parser;

import com.easy.reader.persistance.dao.BookDao;
import com.easy.reader.persistance.entity.Book;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

/**
 * Test for book parser
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
public class BookParserBeanTest {
    @Inject
    BookParserBean bookParserBean;
    
    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
                .addPackage(Book.class.getPackage())
                .addPackage(BookDao.class.getPackage())
                .addPackage(BookParserBean.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource(EmptyAsset.INSTANCE, "xmlParserTest.xml");
        System.out.println(archive.toString(true));
        return archive;
    }
    
    @Test
    public void testParse() {
        InputStream resource = getClass().getResourceAsStream("/xmlParserTest.xml");
        try {
            Set<String> words = bookParserBean.parse(resource);
            Iterator<String> iterator = words.iterator();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            Assert.fail();
        } catch (XMLStreamException streamExceiptiom) {
            streamExceiptiom.printStackTrace();
            Assert.fail();
        }
    }
}
