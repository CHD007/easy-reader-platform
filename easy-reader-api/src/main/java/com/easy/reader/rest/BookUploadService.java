package com.easy.reader.rest;

import com.easy.reader.parser.BookParseException;
import com.easy.reader.parser.BookParserBean;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * Book upload api
 * @author dchernyshov
 */
@Path("/upload")
public class BookUploadService {
    private static final Logger LOGGER = Logger.getLogger(BookUploadService.class);
    @EJB
    private BookParserBean bookParserBean;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadFile(@FormDataParam("uploadFile") InputStream uploadedInputStream,
                               @FormDataParam("uploadFile") FormDataContentDisposition fileDetail) {
        // check if all form parameters are provided
        if (uploadedInputStream == null || fileDetail == null)
            return Response.status(400).entity("Invalid form data").build();
        else {
            return Response.ok().build();
        }

//        String[] split = fileName.split("\\.");
//        String fileType = split[split.length - 1];
//
//        try {
//            bookParserBean.parseBook(content, fileName, fileType);
//            return Response.ok().build();
//        } catch (BookParseException | IOException e) {
//            LOGGER.error("Error while parsing book", e);
//            return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).entity("Invalid file type").build();
//        }
    }
}
