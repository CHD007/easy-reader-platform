package com.easy.reader.parser;

import com.easy.reader.persistance.dao.BookDao;
import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.entity.Book;
import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.Word;
import com.easy.reader.translator.GlosbeWebServiceClient;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Парсер книги.
 * @author dchernyshov
 */
@Stateless
public class BookParserBean {
    private static final String FB2 = "fb2";
    private static final String TXT = "txt";
    private static final String DOC = "doc";
    
    @Inject
    private WordDao wordDao;
    @Inject
    private BookDao bookDao;
    @Inject
    private BookWordDao bookWordDao;
    private GlosbeWebServiceClient translator = new GlosbeWebServiceClient();
    
    /**
     * Узнает какой формат книги получен. Парсит книгу, используя нужный парсер.
     * @param inputStream книга для парсинга.
     */
    public List<Word> parseBook(InputStream inputStream, String fileName, String fileType) throws BookParseException, IOException {
        BookParser bookParser = new BookParser();
    
        switch (fileType) {
            case FB2:
                bookParser.setParser(new FB2Parser());
                break;
            case TXT:
                throw new BookParseException("Invalid file type");
            case DOC:
                throw new BookParseException("Invalid file type");
            default:
                throw new BookParseException("Invalid file type");
        }
        if (!bookDao.findBookByName(fileName).isPresent()) {
            Book book = new Book();
            book.setBookName(fileName);
            bookDao.save(book);
    
            Map<String, String> parse = bookParser.parse(inputStream);
            final Book parsedBook;
            Optional<Book> bookByName = bookDao.findBookByName(fileName);
            if (bookByName.isPresent()) {
                parsedBook = bookByName.get();
            } else {
                throw new BookParseException("Error: can't found book after saving");
            }
            List<Word> words = parse.keySet().stream()
                    .map(this::processWord)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            words.forEach(word -> makeBookWordByWord(word, parsedBook, parse));
            return words;
        }
        return new ArrayList<>();
    }
    
    /**
     * Проверяет есть ли уже данное слово в базе.
     * Если слово есть, то просто его возвращаем.
     * @param wordName слово, на основе которого создается объект Word
     * @return объект типа {@link Book}, который создан
     */
    private Word processWord(String wordName) {
        Optional<Word> wordByName = wordDao.findWordByName(wordName);
        if (wordByName.isPresent()) {
            return wordByName.get();
        }
        return null;
    }
    
    /**
     * Создает книжные слова
     * @param word слово, на основе которого будет создано книжное слово
     * @param parsedBook книга, для которой создается слово
     * @param wordsWithContext контекст для слова
     */
    private void makeBookWordByWord(Word word, Book parsedBook, Map<String, String> wordsWithContext) {
        BookWord bookWord = new BookWord();
        bookWord.setWordFk(word);
        bookWord.setBookFk(parsedBook);
        bookWord.setContext(wordsWithContext.get(word.getWordName()));
        bookWordDao.save(bookWord);
    }
}
