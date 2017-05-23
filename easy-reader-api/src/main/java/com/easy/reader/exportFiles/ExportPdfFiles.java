package com.easy.reader.exportFiles;

import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.dto.WordDto;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Export words in pdf file
 * Created by kisa on 17.04.2017.
 */
@Stateless
public class ExportPdfFiles {
    public static final String DEST = "ResultExportFiles/myWords.pdf";
    public static final String FONT = "FreeSans.ttf";

    @EJB
    private BookWordDao bookWordDao;
    //  @Inject private Service myService;

    public File exportPdfFiles(Long id) throws IOException, DocumentException {

        File file = new File(DEST);
        file.getParentFile().mkdirs();
        writePdf(id);
        return file;
    }

    public void writePdf(Long id) throws IOException, DocumentException {
        //List<WordDto> wordDtoArrayList = myService.getAllWords();
        List<BookWord> wordDtoArrayList = bookWordDao.findAllWordsByBookId(id);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();

        BaseFont bf1 = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font transcription = new Font(bf1, 12);

        BaseFont bf2 = BaseFont.createFont(FONT, "Cp1251", BaseFont.EMBEDDED);
        Font rus = new Font(bf2, 12);
        float width[] = {19, 19, 19, 45};
        PdfPTable table = new PdfPTable(width);

        //table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //table.setMarginTop(0);
        //table.setMarginBottom(0);
        // first row
        PdfPCell cell = new PdfPCell(new Paragraph("My words"));

        cell.setColspan(4);
        cell.setMinimumHeight(20);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.CYAN);
        table.addCell(cell);
        /*
        cell.add("My words");
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setPadding(4);
        cell.setBackgroundColor(new DeviceRgb(140, 221, 8));
        table.addCell(cell);
*/
        PdfPCell cell2 = new PdfPCell(new Paragraph("Word"));
        cell2.setMinimumHeight(20);

        table.addCell(cell2);

        table.addCell("Transcription");
        table.addCell("Translation");
        table.addCell("Context");
        //fill table with info
        for (int i = 0; i < wordDtoArrayList.size(); i++) {
            PdfPCell cell6 = new PdfPCell(new Paragraph(wordDtoArrayList.get(i).getWordFk().getWordName()));
            cell6.setMinimumHeight(45);
            table.addCell(cell6);

            //table.addCell(wordDtoArrayList.get(i).getWordName());
            table.addCell(new Paragraph(wordDtoArrayList.get(i).getWordFk().getTranscription(), transcription));
            table.addCell(new Paragraph(wordDtoArrayList.get(i).getWordFk().getTranslation(), rus));
            table.addCell(wordDtoArrayList.get(i).getContext());
        }
        document.add(table);
        //Close document
        document.close();
    }


}