package com.easy.reader.parser;

import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.entity.Word;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Парсер книги.
 * @author dchernyshov
 */
public class BookParser {
    private Parser parser;
    
    public BookParser(Parser parser) {
        this.parser = parser;
    }
    
    public List<Word> parse(InputStream in) throws IOException {
        WordDao wordDao = new WordDao();
        return parser.parse(in)
                .stream()
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
    }
    
    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
