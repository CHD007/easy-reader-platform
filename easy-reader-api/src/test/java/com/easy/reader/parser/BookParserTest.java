package com.easy.reader.parser;

import com.easy.reader.persistance.entity.Word;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Test for fb2 book parser
 * @author dchernyshov
 */
public class BookParserTest {
    private static final Logger LOGGER = Logger.getLogger(BookParser.class);
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
            List<Word> words = bookParser.parse(resource);
            String[] actualWords = (String[]) words.stream()
                    .map(word -> word.getTranslation())
                    .toArray();
            Set<String> expectedWordsSet = new HashSet<>(Arrays.asList(expectedWordsArray));
            Assert.assertEquals(expectedWordsSet, actualWords);
        } catch (IOException exception) {
            LOGGER.error("Error while parsing book", exception);
            Assert.fail();
        }
    }
}
