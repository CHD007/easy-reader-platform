//package com.easy.reader.api;
//
////import com.easy.reader.exportFiles.ExportPdfFiles;
//import com.itextpdf.text.DocumentException;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.ws.rs.Path;
//import java.io.IOException;
//
//@Stateless
//@Path(value = "/export")
//public class ExportApi {
//
//    @Inject
//    private ExportPdfFiles service;
//
//    @Path(value = "/pdf")
//    public String exportInPdf() throws IOException, DocumentException {
//        service.writePdf("myWords.pdf");
//        return "OK";
//    }
//}
