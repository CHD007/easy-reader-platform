package com.easy.reader.parser;

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
import java.util.stream.Collectors;

/**
 * FB2 parser
 * @author dchernyshov
 */
public class FB2Parser implements Parser {
    private Set<String> words = new HashSet<>();
    private XMLInputFactory factory = XMLInputFactory.newInstance();
    
    /**
     * Парсит файл XML.
     * @param in входной stream XML файла
     * @return слова из книги
     * @throws IOException
     * @throws XMLStreamException
     */
    @Override
    public Set<String> parse(InputStream in) throws IOException {
        try {
            XMLEventReader eventReader = factory.createXMLEventReader(in);
            while (eventReader.hasNext()) {
                XMLEvent xmlEvent = eventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    String namespaceURI = xmlEvent.asStartElement().getName().getNamespaceURI();
                    QName bodyTag = new QName(namespaceURI, "body");
                    if (bodyTag.equals(xmlEvent.asStartElement().getName())) {
                        parseBodyTag(xmlEvent, eventReader, bodyTag);
                    }
                }
            }
        } catch (XMLStreamException xmlStreamException) {
            xmlStreamException.printStackTrace();
        }
        return words;
    }
    
    /**
     * Парсит тег body в fb2 документе.
     * @throws IOException
     * @throws XMLStreamException
     */
    private void parseBodyTag(XMLEvent xmlEvent, XMLEventReader eventReader, QName bodyTag) throws IOException, XMLStreamException {
        while (!xmlEvent.isEndElement() || (xmlEvent.isEndElement() && !bodyTag.equals(xmlEvent.asEndElement().getName()))) {
            xmlEvent = eventReader.nextEvent();
            if (xmlEvent.getEventType() == XMLStreamConstants.CHARACTERS) {
                processParagraph(xmlEvent.asCharacters());
            }
        }
    }
    
    /**
     * Парсит параграф. Записывает в words все слова из параграфа.
     * @param characters
     */
    private void processParagraph(Characters characters) {
        StringBuilder chars = new StringBuilder();
        chars.append(characters.getData());
        List<String> strings = Arrays.asList(chars.toString().split("\\P{L}+"));
        words.addAll(strings.stream()
                .filter(str -> !str.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toList()));
    }
}
