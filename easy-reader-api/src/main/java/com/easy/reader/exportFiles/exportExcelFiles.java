package com.easy.reader.exportFiles;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;

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
            workbook.createSheet("firstSheet", 0);
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
            WritableSheet copySheet = copy.getSheet(0);
            Label label1 = new Label(1, 2 , "world!");
            copySheet.addCell(label1);

            copy.write();
            copy.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
}
