package com.easy.reader.rest;

import com.easy.reader.persistance.dao.BookDao;
import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.dao.WordDao;
import com.easy.reader.persistance.dto.BookDto;
import com.easy.reader.persistance.dto.BookWordDto;
import com.easy.reader.persistance.entity.Book;
import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.Status;
import com.easy.reader.persistance.entity.Word;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Rest api for books
 *
 * @author dchernyshov
 */
@Path("/books")
@Singleton
public class BookService {

    @EJB
    private BookDao bookDao;
    @EJB
    private BookWordDao bookWordDao;
    @EJB
    private WordDao wordDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookDto> getAllBooks() {
        return bookDao.findAll().stream()
                .map(en -> en.toShortWrapper(en))
                .map(dto -> {
                    //todo that should be in one query [MARKED]
                    dto.setInProgress(bookWordDao.findNumberOfWordsByStatusInBook(dto.getId(), Status.IN_PROGRESS));
                    dto.setLearnedWords(bookWordDao.findNumberOfWordsByStatusInBook(dto.getId(), Status.LEARNED));
                    return dto.calculateNewWords();
                })
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookDto getBook(@PathParam("bookId") Long id) {
        Book book = bookDao.findById(id);
        return book.toWrapper(book);
    }

    @GET
    @Path("{bookId}/book_words/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookWordDto> getBookWords(@PathParam("bookId") Long bookId, @PathParam("page") int page) {
        return bookWordDao.findAllWordsByBookId(bookId, page).stream().map(en -> en.toWrapper(en)).collect(Collectors.toList());
    }


    @DELETE
    @Path("delete/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("bookId") Long bookId) {
        if (bookId != null) {
            return bookDao.delete(bookId) ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid ID for Book").build();
        }
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
