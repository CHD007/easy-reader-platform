package com.easy.reader.exportFiles;
import com.easy.reader.persistance.dto.WordDto;
import com.itextpdf.kernel.color.DeviceRgb;

import com.itextpdf.text.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.pdf.BaseFont;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by kisa on 17.04.2017.
 */
public class exportPdfFiles {
    public static final String DEST = "ResultExportFiles/myWords.pdf";
    public static final String FONT = "easy-reader-api/src/main/resources/FreeSans.ttf";
    public static void main(String args[]) throws IOException,  DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
    //    new exportPdfFiles().createPdf(DEST);
        new exportPdfFiles().writePdf(DEST, new ArrayList<>());
    }
    public void writePdf(String dest, ArrayList<WordDto> wordDtoArrayList) throws IOException, DocumentException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        // Font f = new Font(BaseFont.createFont(FONT, "hello", BaseFont.EMBEDDED));
       // Font font = FontFactory.getFont(FONT, "Cp1251", BaseFont.EMBEDDED);

        BaseFont bf1 = BaseFont.createFont(FONT, BaseFont.IDENTITY_H , BaseFont.EMBEDDED);
        Font transcr = new Font(bf1, 12);

        BaseFont bf2 = BaseFont.createFont(FONT, "Cp1251", BaseFont.EMBEDDED);
        Font rus = new Font(bf2, 12);

//My test


        WordDto myObj = new WordDto();
        myObj.setWordName("table");
        myObj.setTranslation("стол");
        myObj.setContext(Arrays.asList(
                "Lets sing a song about table.", "Table is the best thing in the world!"));
        myObj.setTranscription("ˈteɪbəl ɪnˈkoʊdɪŋ");


        wordDtoArrayList.add(0, myObj);

        wordDtoArrayList.add(1, myObj);
//
        float width[] = {15, 15, 15, 30};
        PdfPTable  table = new PdfPTable (width);

        //table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //table.setMarginTop(0);
        //table.setMarginBottom(0);
        // first row
        PdfPCell cell = new PdfPCell(new Paragraph("My words"));

        cell.setColspan(4);
        table.addCell(cell);
        /*
        cell.add("My words");
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setPadding(4);
        cell.setBackgroundColor(new DeviceRgb(140, 221, 8));
        table.addCell(cell);
*/
        table.addCell("Word");
        table.addCell("Transcription");
        table.addCell("Translation");
        table.addCell("Context");
        //fill table with info
        for (int i =0; i<wordDtoArrayList.size(); i++) {
            table.addCell(wordDtoArrayList.get(i).getWordName());
            table.addCell(new Paragraph(wordDtoArrayList.get(i).getTranscription(), transcr));
            table.addCell(new Paragraph(wordDtoArrayList.get(i).getTranslation(), rus));
            table.addCell(wordDtoArrayList.get(i).getContext().toString());
        }
       document.add(table);
        //Close document
        document.close();
    }


}