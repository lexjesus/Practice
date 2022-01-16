package sample;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyExcel {
    public File CreateExcel(String filename){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        File out = new File("F:\\" + filename + ".xls");
        workbook.write(out);

        return out;
    }


    public File writeIntoExcel(File filename, int rows, int cells, String value){
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet();

        for(int i = 0; i < rows; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < cells; j++){
                Cell name = row.createCell(j);
                name.setCellValue(value);
            }
        }

        book.write(filename);
        book.close();
        return filename;
    }
}
