package com.easy.reader.exportFiles;

import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.entity.BookWord;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Export words in excel file
 * Created by kisa on 12.04.2017.
 */
@Stateless
public class ExportExcelFiles {
    public static String fileName = "MyWords.xls";
    private static Logger log = Logger.getLogger(ExportExcelFiles.class.getName());

    @EJB
    private BookWordDao bookWordDao;

    public File exportExcel(Long id){
        log.log(Level.INFO, "execute exportExcel");
        File file = new File(fileName);
        createExcel();
        writingExcelFile(id);
        return file;
    }

    //create header for excel file
    private void createExcel() {
        log.log(Level.INFO, "execute createExcel");
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
            log.log(Level.WARNING, "execute exportExcel, error : " + e.toString());
            e.printStackTrace();
        }
    }

    public void writingExcelFile(Long id) {
        log.log(Level.INFO, "execute writingExcelFile");
        try {
            Workbook wb = Workbook.getWorkbook(new File(fileName));
            WritableWorkbook copy = Workbook.createWorkbook(new File(fileName), wb);
            List<BookWord> wordDtoArrayList = bookWordDao.findAllWordsByBookId(id);
            WritableSheet copySheet = copy.getSheet(0);
            //fill file with info
            for (int i = 0; i < wordDtoArrayList.size(); i++) {
                Label lWord = new Label(0, i + 1, wordDtoArrayList.get(i).getWordFk().getWordName());
                copySheet.addCell(lWord);
                Label lTranscription = new Label(1, i + 1, wordDtoArrayList.get(i).getWordFk().getTranscription());
                copySheet.addCell(lTranscription);
                Label lTranslate = new Label(2, i + 1, wordDtoArrayList.get(i).getWordFk().getTranslation());
                copySheet.addCell(lTranslate);
                if (wordDtoArrayList.get(i).getContext() != null){
                    Label lBook = new Label(3, i + 1, wordDtoArrayList.get(i).getContext());
                    copySheet.addCell(lBook);
                }
            }
            copy.write();
            copy.close();
        } catch (Exception e) {
            log.log(Level.WARNING, "execute writingExcelFile, error: " + e.toString());
            e.printStackTrace();
        }
    }
}
