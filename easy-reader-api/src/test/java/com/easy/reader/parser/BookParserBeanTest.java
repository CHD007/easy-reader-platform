package com.easy.reader.parser;

import com.easy.reader.persistance.dao.BookDao;
import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.dto.BookDto;
import com.easy.reader.persistance.dto.DataTransferable;
import com.easy.reader.persistance.entity.Book;
import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.Word;
import com.easy.reader.translator.GlosbeWebServiceClient;
import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Test for fb2 book parser
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
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
                .addPackage(BookDto.class.getPackage())
                .addClass(Book.class)
                .addClass(DataTransferable.class)
                .addPackage(GlosbeWebServiceClient.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    
    @After
    public void cleanUp() {
        bookWordDao.findAll().forEach(bw -> bookWordDao.delete(bw));
        bookDao.findAll().forEach(b -> bookDao.delete(b));
        wordDao.findAll().forEach(w -> wordDao.delete(w));
    }
    
    @Test
    @UsingDataSet("datasets/wordsParser.yml")
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
    @UsingDataSet("datasets/wordsParser.yml")
    public void testParseWithContext() {
        HashMap<String, String> expectedWords = new HashMap<>();
        expectedWords.put("the", "The; one");
        expectedWords.put("one", "The; one");
        expectedWords.put("door", " opened, the: door");
        expectedWords.put("opened", " opened, the: door");
        expectedWords.put("london", "London");
        expectedWords.put("martin", "Martin");
        expectedWords.put("home", "\"home\" one");
        InputStream resource = getClass().getResourceAsStream("/xmlParserTest.xml");
        try {
            List<Word> words = bookParserBean.parseBook(resource,"/xmlParserTest.xml", "fb2");
            List<BookWord> allWordsByBookId = bookWordDao.findAllWordsByBookId(bookDao.findBookByName("/xmlParserTest.xml").get().getId());
            Assert.assertEquals(expectedWords.size(), allWordsByBookId.size());
            Map<String, String> actualWords = new HashMap<>();
            allWordsByBookId.stream()
                    .forEach(bookWord -> actualWords.put(bookWord.getWordFk().getWordName(),
                            bookWord.getContext().get(0)));
            Assert.assertEquals(expectedWords, actualWords);
        } catch (IOException | BookParseException exception) {
            LOGGER.error("Error while parsing book", exception);
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
