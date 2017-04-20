package com.easy.reader.parser;

import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dchernyshov
 */
@Stateless
public class BookParserBean {

    private static final QName P_TAG = new QName("p");
    private StringBuilder chars;

    private Set<String> words = new HashSet<String>();
    private List<String> splittedWords;
    private XMLInputFactory factory = XMLInputFactory.newInstance();
    
    public BookParserBean() {
        chars = new StringBuilder();
    }
    
    public Set<String> parse(InputStream in) throws IOException, XMLStreamException {
        XMLEventReader eventReader = factory.createXMLEventReader(in);

        while (eventReader.hasNext()) {
            XMLEvent xmlEvent = eventReader.nextEvent();
            switch (xmlEvent.getEventType()) {
                case XMLStreamConstants.CHARACTERS:
                    processParagraph(xmlEvent.asCharacters());
                    break;
            }
        }
        return words;
    }

    private void processParagraph(Characters characters) {
        chars.append(characters.getData());
        splittedWords = Arrays.asList(chars.toString().split(" "));
        for (String word : splittedWords) {
            words.add(word);
        }
    }
}
