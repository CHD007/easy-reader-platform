package com.easy.reader.parser;

import com.easy.reader.persistance.dao.BookDao;
import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.entity.Book;
import com.easy.reader.persistance.entity.Word;
import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Test for fb2 book parser
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
public class BookParserBeanTest {
    private static final Logger LOGGER = Logger.getLogger(BookParserBeanTest.class);
    @Inject
    private BookParserBean bookParserBean;
    @Inject
    private BookWordDao bookWordDao;
    @Inject
    private WordDao wordDao;
    @Inject
    private BookDao bookDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BookParser.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addPackage(BookDao.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    public void testParse() throws IOException {
        String[] expectedWordsArray = {"the", "martin", "london", "home", "opened", "one", "door"};
        InputStream resource = getClass().getResourceAsStream("/xmlParserTest.xml");
        try {
            List<Word> words = bookParserBean.parseBook(resource, "/xmlParserTest.xml", "fb2");
            Set<String> actualWords = words.stream()
                    .map(word -> word.getWordName())
                    .collect(Collectors.toSet());
            Set<String> expectedWordsSet = new HashSet<>(Arrays.asList(expectedWordsArray));
            Assert.assertEquals(expectedWordsSet, actualWords);
            Assert.assertEquals(expectedWordsSet.size(), bookWordDao.findAll().size());
            Assert.assertEquals(expectedWordsSet.size(), wordDao.findAll().size());
            Assert.assertEquals(1, bookDao.findAll().size());
        } catch (BookParseException e) {
            LOGGER.error("Error while parsing book", e);
            Assert.fail();
        }
    }
    
    @Test
    public void testParseWithInvalidFileType() throws IOException {
        InputStream resource = getClass().getResourceAsStream("/xmlParserTest.xml");
        try {
            bookParserBean.parseBook(resource, "/xmlParserTest.xml", "wrongType");
            Assert.fail();
        } catch (BookParseException e) {
            Assert.assertEquals("Invalid file type", e.getMessage());
        }
    }
}
