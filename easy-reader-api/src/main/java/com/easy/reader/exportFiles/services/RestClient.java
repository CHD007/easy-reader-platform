package com.easy.reader.exportFiles.services;

import com.easy.reader.exportFiles.ejb.ExportBean;
import com.itextpdf.text.DocumentException;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    @Path("/pdf")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPdfFile () throws IOException, DocumentException
    {
//ошибочки обработать и в лог
        log.log(Level.INFO, "hello!");
        File file = bean.exportInPdf();
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
                .build();
    }
    @GET
    @Path("/excel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getExcelFile () throws IOException, DocumentException
    {

        File file = bean.exportInExcel();
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
                .build();
    }
}
