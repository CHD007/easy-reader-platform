package com.easy.reader.exportFiles;

import com.easy.reader.persistance.dao.BookWordDao;

import com.easy.reader.persistance.entity.BookWord;
import com.itextpdf.text.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.pdf.BaseFont;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Export words in pdf file
 * Created by kisa on 17.04.2017.
 */
@Stateless
public class ExportPdfFiles {
    public static final String DEST = "ResultExportFiles/myWords.pdf";
    public static final String FONT = "FreeSans.ttf";
    private static Logger log = Logger.getLogger(ExportPdfFiles.class.getName());

    @EJB
    private BookWordDao bookWordDao;

    public File exportPdfFiles(Long id, int startWord, int endWord) throws IOException, DocumentException {
        log.log(Level.INFO, "execute exportPdfFiles");
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        writePdf(id, startWord, endWord);
        return file;
    }

    private void writePdf(Long id, int startWord, int endWord) throws IOException, DocumentException {
        log.log(Level.INFO, "execute writePdf");

        List<BookWord> wordDtoArrayList = bookWordDao.findAllWordsByBookId(id);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();

        //Create font for base info and special fonts for transcription and tranclation
        BaseFont bf1 = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font transcription = new Font(bf1, 12);
        BaseFont bf2 = BaseFont.createFont(FONT, "Cp1251", BaseFont.EMBEDDED);
        Font rus = new Font(bf2, 12);

        //create header for pdf file
        float width[] = {19, 19, 19, 45};
        PdfPTable table = new PdfPTable(width);
        PdfPCell cell = new PdfPCell(new Paragraph("My words"));
        cell.setColspan(4);
        cell.setMinimumHeight(20);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.CYAN);
        table.addCell(cell);
        PdfPCell cell2 = new PdfPCell(new Paragraph("Word"));
        cell2.setMinimumHeight(20);
        table.addCell(cell2);
        table.addCell("Transcription");
        table.addCell("Translation");
        table.addCell("Context");

        //fill table with info
        bookWordDao.findAllWordsByBookId(id, startWord, endWord).forEach(word -> {
            table.addCell(word.getWordFk().getWordName());
            table.addCell(new Paragraph(word.getWordFk().getTranscription(), transcription));
            table.addCell(new Paragraph(word.getWordFk().getTranslation(), rus));
            table.addCell(word.getContext());
        });
        document.add(table);

        //Close document
        document.close();
    }


}