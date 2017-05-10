package com.easy.reader.parser;

import com.easy.reader.persistance.dao.BookDao;
import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.entity.Book;
import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.Word;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
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
    private static final String FB2 = "fb2";
    private static final String TXT = "txt";
    private static final String DOC = "doc";
    
    @Inject
    private WordDao wordDao;
    @Inject
    private BookDao bookDao;
    @Inject
    private BookWordDao bookWordDao;
    /**
     * Узнает какой формат книги получен. Парсит книгу, используя нужный парсер.
     * @param inputStream книга для парсинга.
     */
    public List<Word> parseBook(InputStream inputStream, String fileName, String fileType) throws BookParseException, IOException {
        Book book = new Book();
        book.setBookName(fileName);
        bookDao.save(book);
    
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
    
        Set<String> parse = bookParser.parse(inputStream);
        final Book parsedBook;
        Optional<Book> bookByName = bookDao.findBookByName(fileName);
        if (bookByName.isPresent()) {
            parsedBook = bookByName.get();
        } else {
            throw new BookParseException("Error: can't found book after saving");
        }
        List<Word> words = parse.stream()
                .map((String word) -> processWord(word))
                .collect(Collectors.toList());
        words.forEach(word -> makeBookWordByWord(word, parsedBook));
        return words;
    }
    
    /**
     * Проверяет есть ли уже данное слово в базе.
     * Если слово есть, то просто его возвращаем.
     * Если слова еще нет, то создаем его и сохраняем
     * @param wordName слово, на основе которого создается объект Word
     * @return объект типа {@link Book}, который создан
     */
    private Word processWord(String wordName) {
        Optional<Word> wordByName = wordDao.findWordByName(wordName);
        if (wordByName.isPresent()) {
            return wordByName.get();
        } else {
            Word word1 = new Word();
            word1.setWordName(wordName);
            wordDao.save(word1);
            Optional<Word> wordByName1 = wordDao.findWordByName(wordName);
            if (wordByName1.isPresent()) {
                word1 = wordByName1.get();
            }
            return word1;
        }
    }
    
    /**
     * Создает книжные слова
     * @param word слово, на основе которого будет создано книжное слово
     * @param parsedBook книга, для которой создается слово
     */
    private void makeBookWordByWord(Word word, Book parsedBook) {
        BookWord bookWord = new BookWord();
        bookWord.setWordFk(word);
        bookWord.setBookFk(parsedBook);
        bookWordDao.save(bookWord);
    }
}
