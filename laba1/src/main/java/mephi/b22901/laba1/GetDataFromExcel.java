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
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;



public class GetDataFromExcel {
    public static double[][] readData(File file, int sheetNumber) throws IOException, InvalidFormatException {
        

        try (Workbook workbook = new XSSFWorkbook(file)) { 
            if (sheetNumber >= workbook.getNumberOfSheets()) {
                return null;
            }

            Sheet sheet = workbook.getSheetAt(sheetNumber);
            int rowCount = sheet.getPhysicalNumberOfRows();
            double[][] data = new double[rowCount - 1][];
            if (rowCount < 2) {
                return new double[0][];
            }
            
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            
            for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                int columnCount = row.getPhysicalNumberOfCells();
                double[] values = new double[columnCount];

                for (int colIndex = 0; colIndex < columnCount; colIndex++) {
                    Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                values[colIndex] = cell.getNumericCellValue();
                                break;
                            case FORMULA:
                                values[colIndex] = evaluator.evaluate(cell).getNumberValue();
                                break;
                            default:
                                values[colIndex] = Double.NaN; // Пропускаем текст и пустые ячейки
                        }
                    } else {
                        values[colIndex] = Double.NaN;
                    }
                }

                data[rowIndex - 1] = values;
            }
            return data;
        } catch (IOException e) {
            return null;
        }


    }
}
