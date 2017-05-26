package com.easy.reader.exportFiles.ejb;

import com.easy.reader.exportFiles.ExportExcelFiles;
import com.easy.reader.exportFiles.ExportPdfFiles;
import com.itextpdf.text.DocumentException;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kisa on 15.05.2017.
 */
@Stateless
public class ExportBean {
    @EJB
    private ExportExcelFiles exportExcelFiles;
    @EJB
    private ExportPdfFiles exportPdfFiles;

    private static Logger log = Logger.getLogger(ExportExcelFiles.class.getName());

    public File exportInPdf(Long id, int startWord, int endWord) throws IOException, DocumentException {
        log.log(Level.INFO, "execute exportInPdf");
        return exportPdfFiles.exportPdfFiles(id, startWord, endWord);

    }

    public File exportInExcel(Long id, int startWord, int endWord) throws IOException, WriteException, BiffException {

        log.log(Level.INFO, "execute exportInExcel");
        return exportExcelFiles.exportExcel(id, startWord, endWord);
    }
}
