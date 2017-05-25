package com.easy.reader.rest;

import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.Status;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Rest api for book's words
 * @author dchernyshov
 */
@Path("/book_words")
public class BookWordService {
    @EJB
    private BookWordDao bookWordDao;
    
    @GET
    @Path("/{bookWordId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookWord(@PathParam("bookWordId") Long id) {
        BookWord bookWord = bookWordDao.findById(id);
        if (bookWord != null) {
            return Response.ok().entity(bookWord.toWrapper(bookWord)).build();
        } else {
            return Response.status(400).build();
        }
    }
    
    @POST
    @Path("/{bookWordId}/update_status")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBookWordStatus(@PathParam("bookWordId") Long id, String status) {
        BookWord bookWord = bookWordDao.findById(id);
        if (bookWord != null) {
            bookWord.setStatus(Status.valueOf(status));
            bookWordDao.update(bookWord);
            return Response.ok().build();
        } else {
            return Response.status(400).build();
        }
        
    }
}
