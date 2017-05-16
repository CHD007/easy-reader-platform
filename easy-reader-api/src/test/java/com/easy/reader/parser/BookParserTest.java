package com.easy.reader.parser;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Test for fb2 book parser
 * @author dchernyshov
 */
public class BookParserTest {
    private static final Logger LOGGER = Logger.getLogger(BookParserTest.class);
    private BookParser bookParser;
    
    @Before
    public void init() {
        bookParser = new BookParser(new FB2Parser());
    }
    
    @Test
    public void testParse() {
        String[] expectedWordsArray = {"the", "martin", "london", "home", "opened", "one", "door"};
        InputStream resource = getClass().getResourceAsStream("/xmlParserTest.xml");
        try {
            Map<String, String> words = bookParser.parse(resource);
            Set<String> expectedWordsSet = new HashSet<>(Arrays.asList(expectedWordsArray));
            Assert.assertEquals(expectedWordsSet, words.keySet());
        } catch (IOException exception) {
            LOGGER.error("Error while parsing book", exception);
            Assert.fail();
        }
    }
    
    @Test
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
            Map<String, String> words = bookParser.parse(resource);
            Assert.assertEquals(expectedWords, words);
        } catch (IOException exception) {
            LOGGER.error("Error while parsing book", exception);
            Assert.fail();
        }
    }
}
