package com.easy.reader.translator;

import com.easy.reader.persistance.entity.Word;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for translator api
 * @author dchernyshov
 */
public class GlosbeWebServiceClientTest {
    @Test
    public void translateWordTest() {
        GlosbeWebServiceClient glosbeWebServiceClient = new GlosbeWebServiceClient();
        Word word = glosbeWebServiceClient.getWordWithTranslation("home");
        Assert.assertEquals("дом, домой, дома, домашний, родина", word.getTranslation());
    }
}