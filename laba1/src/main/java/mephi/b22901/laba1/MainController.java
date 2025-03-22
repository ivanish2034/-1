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
import java.util.Arrays;
import javax.swing.JFileChooser;


public class MainController {
    private GUI gui;
    private Calculator calculator;
    private double[][] dataset;
    private double[][] results;
    private double[][] covarianceMatrix;

    public MainController() {
        this.calculator = new Calculator();
    }

    public void start() {
        this.gui = new GUI(this);
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
            e.printStackTrace();
        }
    }

    public void calculateStatistics() {
    if (dataset == null) {
        gui.showMessage("Ошибка: сначала импортируйте данные!");
        return;
    }

    results = calculator.calculateAll(dataset);
    covarianceMatrix = calculator.calculateCovariances(dataset);

    StringBuilder resultText = new StringBuilder();
    String[] headers = {"Среднее геом.", "Среднее арифм.", "Станд. отклонение", "Размах",
                        "Дисперсия", "Количество элементов", "Коэф. вариации", 
                        "Нижняя граница доверит. интервала", "Верхняя граница доверит. интервала",
                        "Максимум", "Минимум"};

    for (int i = 0; i < results.length; i++) {
        resultText.append("Набор ").append(i + 1).append(":\n");
        for (int j = 0; j < results[i].length; j++) {
            resultText.append(headers[j]).append(": ")
                      .append(String.format("%.4f", results[i][j])) 
                      .append("\n");
        }
        resultText.append("\n");
    }

    gui.displayResults(resultText.toString());
}

public void exportData(File directory) {
    if (results == null || covarianceMatrix == null) {
        gui.showMessage("Ошибка: сначала рассчитайте статистику!");
        return;
    }

    if (directory != null) {
        try {
            new SaveDataInExcel(directory, results, covarianceMatrix);
            gui.showMessage("Файл 'Ответы.xlsx' успешно сохранён в " + directory.getAbsolutePath());
        } catch (IOException e) {
            gui.showMessage("Ошибка при сохранении файла: " + e.getMessage());
        }
    } else {
        gui.showMessage("Выбор папки отменён.");
    }
}
}