package com.easy.reader.persistance.dao;

import com.easy.reader.persistance.dto.WordDto;
import com.easy.reader.persistance.entity.UserWord;
import com.easy.reader.persistance.entity.Word;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service for getting word's wrapper.
 * @author dchernyshov
 */
@Stateless
public class Service {
    @Inject
    UserWordDao dao;
    
    @Inject
    WordDao wordDao;
    
    public List<WordDto> getAllWords() {
        return dao.findAll().stream().map(en -> en.toWrapper(en)).collect(Collectors.toList());
    }
    
    @PostConstruct
    private void checkDatabase() {
        List<UserWord> userWords = dao.findAll();
        if (userWords.isEmpty()) {
            populateDatabase();
        }
    }
    
    private void populateDatabase() {
        IntStream.range(0, 10).
                forEach(i -> {
                    Word word = new Word();
                    word.setWordName("word " + i);
                    word.setTranslation("transcription " + i);
                    word.setTranslation("перевод " + i);
                    wordDao.save(word);
                    UserWord userWord = new UserWord();
                    userWord.setWordFk(word);
                    dao.save(userWord);
                });
    }
}
