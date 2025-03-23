/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.laba1;

/**
 *
 * @author ivis2
 */
import java.io.File;
import java.io.IOException;


public class MainController {
    private GUI gui;
    private double[][] dataset;
    private double[][] results;
    private double[][] covarianceMatrix;

    public void start() {
        this.gui = new GUI();
        movies();
    }
    
    private void movies() {
        gui.setImButLis(e -> {
            File file = gui.getSelFile();
            int sheetNumber = gui.getSheetNumber();
            importData(file, sheetNumber);
        });

        gui.setCalButLis(e -> calculateStatistics());

        gui.setExportButLis(e -> {
            File directory = gui.getSelectedDirectory();
            exportData(directory);
        });

        gui.setExitButLis(e -> System.exit(0));
    }
    
    public void importData(File file, int sheetNumber) {
        if (file == null || !file.exists()) {
            gui.showMessage("Ошибка: файл не найден!");
            return;
        }

        if (sheetNumber < 0) {
            gui.showMessage("Ошибка: введите корректный номер листа!");
            return;
        }

        try {
            dataset = GetDataFromExcel.readData(file, sheetNumber);
            if (dataset == null || dataset.length == 0) {
                gui.showMessage("Ошибка: не удалось считать данные!");
                return;
            }
            gui.showMessage("Файл успешно загружен!");
        } catch (Exception e) {
            gui.showMessage("Ошибка при загрузке файла: " + e.getMessage());
        }
    }


    public void calculateStatistics() {
        if (dataset == null) {
        gui.showMessage("Ошибка: сначала импортируйте данные!");
        return;
        }
        results = Calculator.calculateAll(dataset);
        covarianceMatrix = Calculator.calculateCovariances(dataset);
        String resultText = "";
        String[] headers = {"Среднее геом.", "Среднее арифм.", "Станд. отклонение", "Размах",
                            "Дисперсия", "Количество элементов", "Коэф. вариации", 
                            "Нижняя граница доверит. интервала", "Верхняя граница доверит. интервала",
                            "Максимум", "Минимум"};

        for (int i = 0; i < results.length; i++) {
            resultText += "Набор " + (i + 1) + ":\n";  // Конкатенация строк
            for (int j = 0; j < results[i].length; j++) {
                resultText += headers[j] + ": " + String.format("%.4f", results[i][j]) + "\n";  // Конкатенация строк
            }
            resultText += "\n";
        }

        gui.displayResults(resultText);
    }


    public void exportData(File directory) {
        if (results == null || covarianceMatrix == null) {
            gui.showMessage("Ошибка: сначала рассчитайте статистику!");
            return;
        }

        if (directory != null) {
            try {
                SaveDataInExcel.SaveDataInExcel(directory, results, covarianceMatrix);
                gui.showMessage("Файл 'Ответы.xlsx' успешно сохранён в " + directory.getAbsolutePath());
            } catch (IOException e) {
                gui.showMessage("Ошибка при сохранении файла: " + e.getMessage());
            }
        } else {
            gui.showMessage("Выбор папки отменён.");
        }
    }
}