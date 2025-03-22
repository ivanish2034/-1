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
import java.awt.event.ActionListener;
import java.io.File;


public class GUI {
    private JFrame frame;
    private JButton importButton, calculateButton, exportButton, exitButton;
    private JTextArea resultArea;
    private JTextField sheetNumberField;

    public GUI(MainController controller) {
        frame = new JFrame("Статистический анализ");
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));

        importButton = new JButton("Импорт данных");
        calculateButton = new JButton("Рассчитать показатели");
        exportButton = new JButton("Экспорт результатов");
        exitButton = new JButton("Выход");

        importButton.addActionListener(e -> {
            File file = getSelectedFile();
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
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public File getSelectedFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        return (result == JFileChooser.APPROVE_OPTION) ? fileChooser.getSelectedFile() : null;
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
        return (result == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
    }
}