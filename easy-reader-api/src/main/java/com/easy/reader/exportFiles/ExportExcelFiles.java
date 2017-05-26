package com.easy.reader.exportFiles;

import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.entity.BookWord;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Export words in excel file
 * Created by kisa on 12.04.2017.
 */
@Stateless
public class ExportExcelFiles {
    private final static String fileName = "MyWords.xls";
    private static Logger log = Logger.getLogger(ExportExcelFiles.class.getName());

    @EJB
    private BookWordDao bookWordDao;

    public File exportExcel(Long id, int startWord, int endWord) throws IOException, WriteException, BiffException {
        log.log(Level.INFO, "execute exportExcel");
        File file = new File(fileName);
        createExcel();
        writingExcelFile(id, startWord, endWord);
        return file;
    }

    //create header for excel file
    private void createExcel() throws IOException, WriteException {
        log.log(Level.INFO, "execute createExcel");
        WritableWorkbook workbook = null;
        workbook = Workbook.createWorkbook(new File(fileName));
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
    }

    public void writingExcelFile(Long id, int startWord, int endWord) throws IOException, BiffException, WriteException {
        log.log(Level.INFO, "execute writingExcelFile");
        Workbook wb = null;
        wb = Workbook.getWorkbook(new File(fileName));
        WritableWorkbook copy = Workbook.createWorkbook(new File(fileName), wb);
        WritableSheet copySheet = copy.getSheet(0);
        //fill file with info

        bookWordDao.findAllWordsByBookId(id, startWord, endWord).forEach(word -> {
            try {
                Label lWord = new Label(0, copySheet.getRows(), word.getWordFk().getWordName());
                copySheet.addCell(lWord);
                Label lTranscription = new Label(1, copySheet.getRows() - 1, word.getWordFk().getTranscription());
                copySheet.addCell(lTranscription);
                Label lTranslate = new Label(2, copySheet.getRows() - 1, word.getWordFk().getTranslation());
                copySheet.addCell(lTranslate);
                if (word.getContext() != null) {
                    Label lBook = new Label(3, copySheet.getRows() - 1, word.getContext());
                    copySheet.addCell(lBook);
                }
            } catch (WriteException e) {
                log.log(Level.SEVERE, "Error in ExportExcelFile lambda expression ", e);
            }
        });
        copy.write();
        copy.close();
    }
}
