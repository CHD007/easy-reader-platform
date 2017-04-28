package com.easy.reader.exportFiles;
import com.easy.reader.persistance.dto.WordDto;

import com.itextpdf.text.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.pdf.BaseFont;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

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
//@Stateless
public class ExportPdfFiles {
    public static final String DEST = "ResultExportFiles/myWords.pdf";
    public static final String FONT = "easy-reader-api/src/main/resources/FreeSans.ttf";

  //  @Inject private Service myService;

    public static void main(String args[]) throws IOException,  DocumentException {

        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ExportPdfFiles().writePdf(DEST);
    }

    public static void writePdf(String dest) throws IOException, DocumentException {
        //List<WordDto> wordDtoArrayList = myService.getAllWords();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        BaseFont bf1 = BaseFont.createFont(FONT, BaseFont.IDENTITY_H , BaseFont.EMBEDDED);
        Font transcription = new Font(bf1, 12);

        BaseFont bf2 = BaseFont.createFont(FONT, "Cp1251", BaseFont.EMBEDDED);
        Font rus = new Font(bf2, 12);

//My test
       // List<WordDto> wordDtoArrayList = myService.getAllWords();


        WordDto myObj = new WordDto();
        myObj.setWordName("table");
        myObj.setTranslation("стол");
        myObj.setContext(Arrays.asList(
                "Put the book on the table!", "The table functions as a desk.", "The book lay open on the table."));
        myObj.setTranscription("ˈteɪbəl");

        List<WordDto> wordDtoArrayList = new ArrayList<>();
        wordDtoArrayList.add(0, myObj);

        WordDto myObj2 = new WordDto();
        myObj2.setWordName("buffet");
        myObj2.setTranslation("буфет");
        myObj2.setContext(Arrays.asList(
                "The dessert buffet tested my willpower.", "He filched a cookie from the buffet table."));
        myObj2.setTranscription("bəˈfeɪ");
        wordDtoArrayList.add(1, myObj2);

        WordDto myObj3 = new WordDto();
        myObj3.setWordName("to cook");
        myObj3.setTranslation("готовить");
        myObj3.setContext(Arrays.asList(
                "My husband doesn't cook.", "She cooked a great meal.", "They helped to cook the meal."));
        myObj3.setTranscription("kuk");
        wordDtoArrayList.add(2, myObj3);

//
        float width[] = {19, 19, 19, 45};
        PdfPTable  table = new PdfPTable (width);

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
        for (int i =0; i<wordDtoArrayList.size(); i++) {
            PdfPCell cell6 = new PdfPCell(new Paragraph(wordDtoArrayList.get(i).getWordName()));
            cell6.setMinimumHeight(45);
            table.addCell(cell6);

            //table.addCell(wordDtoArrayList.get(i).getWordName());
            table.addCell(new Paragraph(wordDtoArrayList.get(i).getTranscription(), transcription));
            table.addCell(new Paragraph(wordDtoArrayList.get(i).getTranslation(), rus));
            table.addCell(wordDtoArrayList.get(i).getContext().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", ";"));
        }
       document.add(table);
        //Close document
        document.close();
    }


}