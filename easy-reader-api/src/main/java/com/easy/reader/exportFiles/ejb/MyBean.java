package com.easy.reader.exportFiles.ejb;

import com.easy.reader.exportFiles.ExportExcelFiles;
import com.easy.reader.exportFiles.ExportPdfFiles;
import com.itextpdf.text.DocumentException;

import javax.ejb.Stateless;
import java.io.IOException;

/**
 * Created by kisa on 15.05.2017.
 */
@Stateless
public class MyBean {
    public void exportInPdf() throws IOException, DocumentException {
        ExportPdfFiles.writePdf(ExportPdfFiles.DEST);
    }
    public void exportInExcel() throws IOException, DocumentException {
        ExportExcelFiles.getExcelExport().exportExcel();
    }
}
