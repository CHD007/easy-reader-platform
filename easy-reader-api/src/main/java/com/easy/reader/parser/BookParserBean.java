package com.easy.reader.parser;

import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dchernyshov
 */
@Stateless
public class BookParserBean {

    private static final QName P_TAG = new QName("p");
    private Attribute paragraph;

    private Set<String> words = new HashSet<String>();
    private XMLInputFactory factory = XMLInputFactory.newInstance();

    public Set<String> parse(File file) throws IOException, XMLStreamException {
        FileInputStream in = new FileInputStream(file);
        XMLEventReader eventReader = factory.createXMLEventReader(in);

        while (eventReader.hasNext()) {
            XMLEvent xmlEvent = eventReader.nextEvent();
            switch (xmlEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    processStartElement(xmlEvent.asStartElement());
                    break;
            }
        }
        return words;
    }

    private void processStartElement(StartElement element) {
        if(element.getName().equals(P_TAG)) {
            paragraph = element.getAttributeByName(P_TAG);
            String paragraphValue = paragraph.getValue();
            String[] split = paragraphValue.split(" ");
            words.addAll(Arrays.asList(split));
        }
    }
}
