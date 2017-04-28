package com.easy.reader.exportFiles;

import com.easy.reader.persistance.dto.WordDto;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Export words in excel file
 * Created by kisa on 12.04.2017.
 */

public class ExportExcelFiles {
    public static String fileName = "MyWords.xls";

    public void exportExcel(){
        File file = new File(fileName);
        if (!file.exists()) {
            createExcel();
        }
        ArrayList<WordDto> wordDtoArrayList = new ArrayList<>();
        writingExcelFile(wordDtoArrayList);
    }

    public static ExportExcelFiles getExcelExport(){
        return new ExportExcelFiles();
    }

    private void createExcel() {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
            WritableSheet copySheet = workbook.createSheet("My words", 0);
            Label word = new Label(0, 0, "word");
            copySheet.addCell(word);
            Label transcription = new Label(1, 0, "transcription");
            copySheet.addCell(transcription);
            Label translation = new Label(2, 0, "translation");
            copySheet.addCell(translation);
            Label context = new Label(3, 0, "context");
            copySheet.addCell(context);
            workbook.write();
            workbook.close();
        } catch (Exception e) {
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
                    "Put the book on the table!", "The table functions as a desk.", "The book lay open on the table."));
            myObj.setTranscription("ˈteɪbəl");

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
            myObj3.setTranscription("kʊk");
            wordDtoArrayList.add(2, myObj3);
            //

            WritableSheet copySheet = copy.getSheet(0);
            for (int i = 0; i < wordDtoArrayList.size(); i++) {
                Label lWord = new Label(0, i + 1, wordDtoArrayList.get(i).getWordName());
                copySheet.addCell(lWord);
                Label lTranscription = new Label(1, i + 1, wordDtoArrayList.get(i).getTranscription());
                copySheet.addCell(lTranscription);
                Label lTranslate = new Label(2, i + 1, wordDtoArrayList.get(i).getTranslation());
                copySheet.addCell(lTranslate);
                Label lBook = new Label(3, i + 1, wordDtoArrayList.get(i).getContext().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", ";"));
                copySheet.addCell(lBook);
            }
            copy.write();
            copy.close();
        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
