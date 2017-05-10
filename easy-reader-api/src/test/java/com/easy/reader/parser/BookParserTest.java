package com.easy.reader.parser;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
            Set<String> words = bookParser.parse(resource);
            Set<String> expectedWordsSet = new HashSet<>(Arrays.asList(expectedWordsArray));
            Assert.assertEquals(expectedWordsSet, words);
        } catch (IOException exception) {
            LOGGER.error("Error while parsing book", exception);
            Assert.fail();
        }
    }
}
