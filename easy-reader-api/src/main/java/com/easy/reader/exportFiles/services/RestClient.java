package com.easy.reader.exportFiles.services;

import com.easy.reader.exportFiles.ejb.ExportBean;
import com.itextpdf.text.DocumentException;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kisa on 16.05.2017.
 */
@Path("/lubovRest")
public class RestClient {

    private static Logger log = Logger.getLogger(RestClient.class.getName());

    @EJB
    private ExportBean bean;

    @GET
    @Path("/pdf/{bookId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPdfFile (@PathParam("bookId") Long id) throws IOException, DocumentException
    {
        log.log(Level.INFO, "in GET method getPdfFile");
        File file = bean.exportInPdf(id);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
                .build();
    }
    @GET
    @Path("/excel/{bookId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getExcelFile (@PathParam("bookId") Long id) throws IOException, DocumentException
    {

        log.log(Level.INFO, "in GET method getExcelFile");
        File file = bean.exportInExcel(id);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
                .build();
    }


}
