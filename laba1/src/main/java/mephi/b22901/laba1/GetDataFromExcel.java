/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.laba1;

/**
 *
 * @author ivis2
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GetDataFromExcel {
    public static double[][] readData(File file, int sheetNumber) {
        ArrayList<double[]> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis)) {

                if (sheetNumber >= workbook.getNumberOfSheets()) {
                    return null;  
                }

        Sheet sheet = workbook.getSheetAt(sheetNumber);
        Iterator<Row> rowIterator = sheet.iterator();

        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            double[] values = new double[row.getPhysicalNumberOfCells()];

            for (int i = 0; i < values.length; i++) {
                Cell cell = row.getCell(i);
                if (cell.getCellType() == CellType.NUMERIC) {
                    values[i] = cell.getNumericCellValue();
                } else {
                    values[i] = Double.NaN; 
                }
            }

            dataList.add(values);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }

    return dataList.toArray(new double[0][]);
}
}


