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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaveDataInExcel {
    public static void SaveDataInExcel(File directory, double[][] results, double[][] covarianceMatrix) throws IOException {

        File file = new File(directory, "Ответы.xlsx");
        Workbook workbook;
        boolean fileExists = file.exists();
        
        if (fileExists) {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
            }
        } else {
            workbook = new XSSFWorkbook();
        }

        int sheetIndex = workbook.getSheetIndex("Результаты");
        if (sheetIndex != -1) {
            workbook.removeSheetAt(sheetIndex);
        }
        
        Sheet sheetResults = workbook.createSheet("Результаты");
        String[] headers = {"Среднее геом.", "Среднее арифм.", "Станд. отклонение", "Размах",
                            "Дисперсия", "Количество элементов", "Коэф. вариации", 
                            "Нижняя граница доверит. интервала", "Верхняя граница доверит. интервала",
                            "Максимум", "Минимум"};

        Row headerRow = sheetResults.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        for (int i = 0; i < results.length; i++) {
            Row row = sheetResults.createRow(i + 1);
            for (int j = 0; j < results[i].length; j++) {
                row.createCell(j).setCellValue(results[i][j]); 
            }
        }

        int kovRow = results.length + 6;
        Row titleRow = sheetResults.createRow(kovRow - 1);
        titleRow.createCell(0).setCellValue("Ковариационная матрица:");

        for (int i = 0; i < covarianceMatrix.length; i++) {
            Row row = sheetResults.createRow(kovRow + i);
            for (int j = 0; j < covarianceMatrix[i].length; j++) {
                row.createCell(j).setCellValue(covarianceMatrix[i][j]); // Без String.format
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}

