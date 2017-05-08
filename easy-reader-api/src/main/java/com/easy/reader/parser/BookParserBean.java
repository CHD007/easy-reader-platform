package com.easy.reader.parser;

import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.entity.Word;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Парсер книги.
 * @author dchernyshov
 */
@Stateless
public class BookParserBean {
    private static final Logger LOGGER = Logger.getLogger(BookParserBean.class);
    @Inject
    private WordDao wordDao;
    
    /**
     * Узнает какой формат книги получен. Парсит книгу, используя нужный парсер.
     * @param inputStream книга для парсинга.
     */
    public List<Word> parseBook(InputStream inputStream) {
        //тут сначала надо определить в каком формате пришла книга
        BookParser bookParser = new BookParser(new FB2Parser());
        
        try {
            Set<String> parse = bookParser.parse(inputStream);
            return parse.stream()
                    .map((String word) -> {
                        Optional<Word> wordByName = wordDao.findWordByName(word);
                        if (wordByName.isPresent()) {
                            return wordByName.get();
                        } else {
                            Word word1 = new Word();
                            word1.setTranslation(word);
                            wordDao.save(word1);
                            return word1;
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error while parsing book", e);
            return new ArrayList<>();
        }
    }
}
