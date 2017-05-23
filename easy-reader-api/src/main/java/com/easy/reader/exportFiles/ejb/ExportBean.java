package com.easy.reader.exportFiles.ejb;

import com.easy.reader.exportFiles.ExportExcelFiles;
import com.easy.reader.exportFiles.ExportPdfFiles;
import com.itextpdf.text.DocumentException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.File;
import java.io.IOException;

/**
 * Created by kisa on 15.05.2017.
 */
@Stateless
public class ExportBean {
    @EJB
    private ExportExcelFiles exportExcelFiles;
    @EJB
    private ExportPdfFiles exportPdfFiles;

    public File exportInPdf(Long id) throws IOException, DocumentException {
        return exportPdfFiles.exportPdfFiles(id);
    }
    public File exportInExcel(Long id) throws IOException, DocumentException {
        return exportExcelFiles.exportExcel(id);
    }
}
