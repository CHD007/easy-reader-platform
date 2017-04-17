package com.easy.reader.parser;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by dchernyshov on 17.04.17.
 */
public class BookParserBeanTest {

    @Test
    public void testParse() {
        String file = "xmlParserTest.xml";
        BookParserBean bookParserBean= new BookParserBean();
        try {
            Set<String> words = bookParserBean.parse(new File(file));
            Iterator<String> iterator = words.iterator();
        } catch (IOException ioException) {
            Assert.fail();
        } catch (XMLStreamException streamExceiptiom) {
            Assert.fail();
        }
    }
}
