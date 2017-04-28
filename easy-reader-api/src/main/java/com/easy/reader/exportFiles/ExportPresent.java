package com.easy.reader.exportFiles;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

/**
 * Created by kisa on 28.04.2017.
 */
public class ExportPresent {
    public static void main(String[] args) throws IOException, DocumentException {
        ExportPdfFiles.writePdf(ExportPdfFiles.DEST);
        ExportExcelFiles.getExcelExport().exportExcel();
    }

}
