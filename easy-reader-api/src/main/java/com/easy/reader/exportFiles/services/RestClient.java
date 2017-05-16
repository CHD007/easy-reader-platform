package com.easy.reader.exportFiles.services;

import com.easy.reader.exportFiles.ejb.MyBean;
import com.itextpdf.text.DocumentException;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by kisa on 16.05.2017.
 */
@Path("/lubovRest")
public class RestClient {

    @EJB
    MyBean bean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPdfFile () throws IOException, DocumentException
    {
        bean.exportInPdf();
        return "Get method of restClient";
    }
}
