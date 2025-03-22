/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.laba1;

/**
 *
 * @author ivis2
 */
import javax.swing.*;
import java.awt.*;
import java.io.File;


public class GUI {
    private JFrame frame;
    private JButton importButton, calculateButton, exportButton, exitButton;
    private JTextArea resultArea;
    private JTextField sheetNumberField;

    public GUI(MainController controller) {
        frame = new JFrame("Статистический анализ");
        frame.setSize(600, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2));

        importButton = new JButton("Импорт данных");
        calculateButton = new JButton("Рассчитать показатели");
        exportButton = new JButton("Экспорт результатов");
        exitButton = new JButton("Выход");

        importButton.addActionListener(e -> {
            File file = getSelFile();
            int sheetNumber = getSheetNumber();
            controller.importData(file, sheetNumber);
        });

        calculateButton.addActionListener(e -> controller.calculateStatistics());
        
        exportButton.addActionListener(e -> {
            File directory = getSelectedDirectory();
            controller.exportData(directory);
        });

        exitButton.addActionListener(e -> System.exit(0));

        JLabel sheetLabel = new JLabel("Номер листа:");
        sheetNumberField = new JTextField("0");

        buttonPanel.add(sheetLabel);
        buttonPanel.add(sheetNumberField);
        buttonPanel.add(importButton);
        buttonPanel.add(calculateButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(exitButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public File getSelFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public int getSheetNumber() {
        try {
            return Integer.parseInt(sheetNumberField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void displayResults(String results) {
        resultArea.setText(results);
    }

    public File getSelectedDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }
}