package com.easy.reader.exportFiles.services;

import com.easy.reader.exportFiles.ejb.ExportBean;
import com.itextpdf.text.DocumentException;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kisa on 16.05.2017.
 */
@Path("/export")
public class ExportService {

    private static Logger log = Logger.getLogger(ExportService.class.getName());

    @EJB
    private ExportBean bean;

    @GET
    @Path("/pdf/{bookId}")
    public Response getPdfFile(@PathParam("bookId") Long id,
                               @QueryParam("startWord") @DefaultValue("1") int startWord,
                               @QueryParam("endWord") @DefaultValue("100") int endWord) {
        log.log(Level.INFO, "in GET method getPdfFile");
        File file;
        try {
            file = bean.exportInPdf(id, startWord, endWord);
        } catch (IOException | DocumentException e) {
            log.log(Level.SEVERE, "execute getPdfFile, IOException | DocumentException error: ", e);
            //Does it true or wrong user response? Should it be more informative?
            return Response.serverError().entity("Exception in exportPdf").build();
        }

        return Response.ok(file, "application/pdf")
                .build();
    }

    @GET
    @Path("/excel/{bookId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getExcelFile(@PathParam("bookId") Long id,
                                 @QueryParam("startWord") @DefaultValue("1") int startWord,
                                 @QueryParam("endWord") @DefaultValue("100") int endWord) {
        log.log(Level.INFO, "in GET method getExcelFile");
        File file;
        try {
            file = bean.exportInExcel(id, startWord, endWord);
        } catch (IOException | WriteException | BiffException e) {
            log.log(Level.SEVERE, "execute getExcelFile, IOException|WriteException|BiffException error: ", e);
            return Response.serverError().entity("Exception in exportExcel").build();
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") //optional
                .build();
    }


}
