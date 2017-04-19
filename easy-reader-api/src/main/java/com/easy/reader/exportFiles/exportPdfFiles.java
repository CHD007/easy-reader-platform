package com.easy.reader.exportFiles;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;

import java.io.File;
import java.io.IOException;



/**
 * Created by kisa on 17.04.2017.
 */
public class exportPdfFiles {
    public static final String DEST = "ResultExportFiles/myWords.pdf";
    public static final String FONT = "easy-reader-api/src/main/resources/FreeSans.ttf";
    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new exportPdfFiles().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        PdfFont font = PdfFontFactory.createFont(FONT, true);

      //  Paragraph p2 = new Paragraph("\\u041e\\u0442\\u043a\\u0443\\u0434").setFont(font);
     //   document.add(p2);

        DanyaClass myObj = new DanyaClass();
        myObj.word = "table";
        myObj.book = "Kitchen";
        myObj.context = "Lets sing a song about table.";
        myObj.transcript = "'teɪb(ə)l";
        myObj.translate = "cmon";

        float width[] = {10, 10, 10, 7, 30};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setMarginTop(0);
        table.setMarginBottom(0);
        // first row
        Cell cell = new Cell(1, 5).add(new Paragraph("My words"));
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setPadding(5);
        cell.setBackgroundColor(new DeviceRgb(140, 221, 8));
        table.addCell(cell);

        table.addCell("Word");
        table.addCell("Transcription");
        table.addCell("Translation");
        table.addCell("Book");
        table.addCell("Context");

        for (int i =0; i<10; i++) {
            table.addCell(myObj.word);
            table.addCell(myObj.transcript);
            table.addCell(myObj.translate);
            table.addCell(myObj.book);
            table.addCell(myObj.context);
        }



        document.add(table);

        //Close document
        document.close();
    }

}