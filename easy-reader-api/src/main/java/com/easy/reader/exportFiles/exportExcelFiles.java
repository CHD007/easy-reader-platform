package com.easy.reader.exportFiles;

import com.easy.reader.persistance.entity.Book;
import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.UserWord;
import com.easy.reader.persistance.entity.Word;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;

/**
 * Created by kisa on 12.04.2017.
 */

public class exportExcelFiles {
    private static String fileName = "MyWords.xls";
    public static void main (String [] args)
    {

        File file = new File(fileName);
        if (!file.exists())
        {
            createExcel();
        }

        writingExcelFile();


    }
    public static void createExcel()
    {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
            WritableSheet copySheet = workbook.createSheet("My words sheet", 0);

            Label word = new Label(0, 0 , "word");
            copySheet.addCell(word);
            Label transcription = new Label(1, 0 , "transcription");
            copySheet.addCell(transcription);
            Label translation = new Label(2, 0 , "translation");
            copySheet.addCell(translation);
            Label book = new Label(3, 0 , "book");
            copySheet.addCell(book);
            Label context = new Label(4, 0 , "context");
            copySheet.addCell(context);
            workbook.write();
            workbook.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
    public static void writingExcelFile() {
        try {
            Workbook wb = Workbook.getWorkbook(new File(fileName));
            WritableWorkbook copy = Workbook.createWorkbook(new File(fileName), wb);
           //for testing system
            DanyaClass myObj = new DanyaClass();
            myObj.word = "table";
            myObj.book = "Kitchen";
            myObj.context = "Lets sing a song about table.";
            myObj.transcript = "'teɪb(ə)l";
            myObj.translate = "стол";

            WritableSheet copySheet = copy.getSheet(0);
            int rows = copySheet.getRows();


            Label lWord = new Label(0, rows , myObj.word);

            copySheet.addCell(lWord);
            Label lTranscr = new Label(1, rows , myObj.transcript);
            copySheet.addCell(lTranscr);
            Label lTranslate = new Label(2, rows , myObj.translate);
            copySheet.addCell(lTranslate);
            Label lBook = new Label(3, rows , myObj.book);
            copySheet.addCell(lBook);
            Label lContext = new Label(4, rows , myObj.context);
            copySheet.addCell(lContext);
            copy.write();
            copy.close();
        }
        catch (Exception e)
        {

            e.printStackTrace();

        }
    }
}
