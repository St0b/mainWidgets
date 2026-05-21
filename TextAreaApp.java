import java.awt.*;
import javax.swing.*;

public class TextAreaApp {
    
    private JFrame frame;
    private JTextField textField;
    private JTextArea textArea;
    private JButton addButton;
    private JButton clearButton;
    private JLabel countLabel;
    private int lineCount = 0;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextAreaApp().createAndShowGUI());
    }
    
    public void createAndShowGUI() {
        frame = new JFrame("Запись строк в текстовую область");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        
        // Верхняя панель с заголовком
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(70, 130, 200));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("Ввод строк в текстовую область");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        
        // Панель ввода текста
        JPanel inputPanel = new JPanel(new BorderLayout(10, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        inputPanel.setBackground(new Color(240, 248, 255));
        
        JLabel inputLabel = new JLabel("Введите строку:");
        inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inputPanel.add(inputLabel, BorderLayout.WEST);
        
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.addActionListener(e -> addLineToTextArea());
        inputPanel.add(textField, BorderLayout.CENTER);
        
        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        addButton = new JButton("Записать");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(70, 130, 200));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(120, 35));
        
        clearButton = new JButton("Очистить всё");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(200, 70, 70));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        
        // Объединяем inputPanel и buttonPanel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(240, 248, 255));
        northPanel.add(inputPanel, BorderLayout.NORTH);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Центральная панель с JTextArea и JScrollPane
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        centerPanel.setBackground(new Color(240, 248, 255));
        
        JLabel areaLabel = new JLabel("Сохранённые строки:");
        areaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        centerPanel.add(areaLabel, BorderLayout.NORTH);
        
        textArea = new JTextArea(12, 40);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(255, 255, 245));
        textArea.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 200), 1));
        
        // Добавляем прокрутку
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 200), 1));
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Нижняя панель с информацией
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(230, 240, 250));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        countLabel = new JLabel("Количество строк: 0");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bottomPanel.add(countLabel);
        
        // Добавляем обработчики событий
        addButton.addActionListener(e -> addLineToTextArea());
        
        clearButton.addActionListener(e -> {
            textArea.setText("");
            lineCount = 0;
            updateCountLabel();
            textField.requestFocus();
        });
        
        // Добавляем всё в окно
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
        textField.requestFocus();
    }
    
    private void addLineToTextArea() {
        String inputText = textField.getText().trim();
        
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, 
                "Пожалуйста, введите текст!", 
                "Предупреждение", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Добавляем нумерацию строк
        lineCount++;
        String lineWithNumber = lineCount + ". " + inputText;
        
        // Добавляем новую строку в JTextArea
        if (textArea.getText().isEmpty()) {
            textArea.setText(lineWithNumber);
        } else {
            textArea.append("\n" + lineWithNumber);
        }
        
        // Очищаем поле ввода и устанавливаем фокус
        textField.setText("");
        textField.requestFocus();
        
        // Прокручиваем JTextArea вниз, чтобы показать последнюю строку
        textArea.setCaretPosition(textArea.getDocument().getLength());
        
        updateCountLabel();
        
        // Анимация кнопки
        addButton.setBackground(new Color(50, 110, 180));
        Timer resetColor = new Timer(150, evt -> addButton.setBackground(new Color(70, 130, 200)));
        resetColor.setRepeats(false);
        resetColor.start();
    }
    
    private void updateCountLabel() {
        countLabel.setText("Количество строк: " + lineCount);
    }
}