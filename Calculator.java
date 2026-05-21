import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator {
    
    private JFrame frame;
    private JTextField displayField;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().createAndShowGUI());
    }
    
    public void createAndShowGUI() {
        frame = new JFrame("Калькулятор");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(5, 5));
        frame.setResizable(false);
        
        displayField = new JTextField("0");
        displayField.setFont(new Font("Segoe UI", Font.BOLD, 32));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        displayField.setBackground(Color.WHITE);
        displayField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(displayField, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };
        
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Segoe UI", Font.BOLD, 20));
            button.setFocusPainted(false);
            button.setBackground(new Color(240, 240, 240));
            
            if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/")) {
                button.setBackground(new Color(255, 200, 100));
            } else if (text.equals("=")) {
                button.setBackground(new Color(100, 200, 100));
            } else if (text.equals("C")) {
                button.setBackground(new Color(255, 100, 100));
            }
            
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }
        
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String command = source.getText();
            
            // Цифры
            if (command.matches("[0-9]")) {
                if (startNewNumber) {
                    displayField.setText(command);
                    startNewNumber = false;
                } else {
                    String currentText = displayField.getText();
                    if (currentText.equals("0") && !command.equals("0")) {
                        displayField.setText(command);
                    } else if (!currentText.equals("0") || command.equals("0")) {
                        displayField.setText(currentText + command);
                    }
                }
            }
            else if (command.equals("C")) {
                firstNumber = 0;
                secondNumber = 0;
                operator = "";
                displayField.setText("0");
                startNewNumber = true;
            }
            else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
                if (!operator.isEmpty() && !startNewNumber) {
                    calculateResult();
                }
                firstNumber = Double.parseDouble(displayField.getText());
                operator = command;
                startNewNumber = true;
            }
            else if (command.equals("=")) {
                if (!operator.isEmpty() && !startNewNumber) {
                    calculateResult();
                    operator = "";
                    startNewNumber = true;
                }
            }
        }
        
        private void calculateResult() {
            secondNumber = Double.parseDouble(displayField.getText());
            double result = 0;
            
            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber == 0) {
                        JOptionPane.showMessageDialog(frame, 
                            "Ошибка! Деление на ноль невозможно.", 
                            "Математическая ошибка", 
                            JOptionPane.ERROR_MESSAGE);
                        displayField.setText("0");
                        firstNumber = 0;
                        operator = "";
                        startNewNumber = true;
                        return;
                    }
                    result = firstNumber / secondNumber;
                    break;
            }
            
            // Проверка на целое число
            if (result == (long) result) {
                displayField.setText(String.valueOf((long) result));
            } else {
                displayField.setText(String.valueOf(result));
            }
            firstNumber = result;
            startNewNumber = true;
        }
    }
}