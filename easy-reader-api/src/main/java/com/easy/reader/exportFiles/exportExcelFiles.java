package com.easy.reader.exportFiles;

import com.easy.reader.persistance.dto.WordDto;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        ArrayList<WordDto> wordDtoArrayList = new ArrayList<WordDto>();
        writingExcelFile(wordDtoArrayList);


    }
    public static void createExcel()
    {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
            WritableSheet copySheet = workbook.createSheet("My words", 0);
            Label word = new Label(0, 0 , "word");
            copySheet.addCell(word);
            Label transcription = new Label(1, 0 , "transcription");
            copySheet.addCell(transcription);
            Label translation = new Label(2, 0 , "translation");
            copySheet.addCell(translation);
            Label context = new Label(3, 0 , "context");
            copySheet.addCell(context);
            workbook.write();
            workbook.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
    public static void writingExcelFile(ArrayList<WordDto> wordDtoArrayList) {
        try {
            Workbook wb = Workbook.getWorkbook(new File(fileName));
            WritableWorkbook copy = Workbook.createWorkbook(new File(fileName), wb);
           //for testing system
            WordDto myObj = new WordDto();
            myObj.setWordName("table");
            myObj.setTranslation("стол");
            myObj.setContext(Arrays.asList(
            "Lets sing a song about table.", "Table is the best thing in the world!"));
            myObj.setTranscription("'teɪb(ə)l");
            wordDtoArrayList.add(0, myObj);
            wordDtoArrayList.add(1, myObj);
            //

            WritableSheet copySheet = copy.getSheet(0);
            for (int i = 0 ; i< wordDtoArrayList.size(); i++) {
                Label lWord = new Label(0, i+1, wordDtoArrayList.get(i).getWordName());
                copySheet.addCell(lWord);
                Label lTranscription = new Label(1, i+1, wordDtoArrayList.get(i).getTranscription());
                copySheet.addCell(lTranscription);
                Label lTranslate = new Label(2, i+1, wordDtoArrayList.get(i).getTranslation());
                copySheet.addCell(lTranslate);
                Label lBook = new Label(3, i+1, wordDtoArrayList.get(i).getContext().toString());
                copySheet.addCell(lBook);
            }
            copy.write();
            copy.close();
        }
        catch (Exception e)
        {

            e.printStackTrace();

        }
    }
}
