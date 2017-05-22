package com.easy.reader.exportFiles.ejb;

import com.easy.reader.exportFiles.ExportExcelFiles;
import com.easy.reader.exportFiles.ExportPdfFiles;
import com.itextpdf.text.DocumentException;

import javax.ejb.Stateless;
import java.io.File;
import java.io.IOException;

/**
 * Created by kisa on 15.05.2017.
 */
@Stateless
public class MyBean {
    public File exportInPdf() throws IOException, DocumentException {
        return ExportPdfFiles.exportPdfFiles();
    }
    public File exportInExcel() throws IOException, DocumentException {
        return ExportExcelFiles.exportExcel();
    }
}
