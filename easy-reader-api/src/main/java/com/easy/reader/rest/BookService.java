package com.easy.reader.rest;

import com.easy.reader.persistance.dao.BookDao;
import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.entity.Book;
import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.Word;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Rest api for books
 * @author dchernyshov
 */
@Path("/books")
public class BookService {
    
    @EJB
    private BookDao bookDao;
    @EJB
    private BookWordDao bookWordDao;
    @EJB
    private WordDao wordDao;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }
    
    @GET
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBook(@PathParam("bookId") Long id) {
        return bookDao.findById(id);
    }

    @GET
    @Path("/{bookId}/bookWords")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookWord> getBookWords(@PathParam("bookId") Long bookId) {
        return bookWordDao.findAllWordsByBookId(bookId);
    }
    
    @PostConstruct
    public void initDatabase() {
        if (bookDao.findAll().isEmpty() && bookWordDao.findAll().isEmpty()) {
            Book book = new Book();
            book.setBookName("first book");
            bookDao.save(book);
            Book book1 = new Book();
            book1.setBookName("second book");
            bookDao.save(book1);
    
            IntStream.range(1, 5).forEach(i -> {
                Word word = new Word();
                word.setWordName("word " + i);
                word.setTranslation("transcription " + i);
                word.setTranslation("translation " + i);
                wordDao.save(word);
                BookWord bookWord = new BookWord();
                bookWord.setBookFk(book);
                bookWord.setWordFk(word);
                bookWordDao.save(bookWord);
            });
        }
    }
}
