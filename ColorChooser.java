import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

public class ColorChooser {
    
    private JFrame frame;
    private JComboBox<String> comboBox;
    private JCheckBox customColorCheckBox;
    private JTextField customColorField;
    private JLabel answerLabel;
    private JButton answerButton;
    
    private String[] colors = {"Красный", "Синий", "Зеленый", "Желтый", "Фиолетовый", "Оранжевый", "Розовый", "Черный", "Белый"};
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColorChooser().createAndShowGUI());
    }
    
    public void createAndShowGUI() {
        frame = new JFrame("Выбор любимого цвета");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(660, 480);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(100, 150, 200));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel(" Выберите свой любимый цвет ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel comboLabel = new JLabel("Выберите цвет из списка:");
        comboLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(comboLabel, gbc);
        
        comboBox = new JComboBox<>(colors);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(comboBox, gbc);
        
        JSeparator separator = new JSeparator();
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(separator, gbc);
        
        JLabel customLabel = new JLabel("Или введите свой цвет:");
        customLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 3;
        centerPanel.add(customLabel, gbc);
        
        JPanel customPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        customPanel.setOpaque(false);
        
        customColorCheckBox = new JCheckBox("Свой цвет");
        customColorCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        customColorCheckBox.setBackground(new Color(245, 245, 245));
        
        customColorField = new JTextField(15);
        customColorField.setFont(new Font("Arial", Font.PLAIN, 14));
        customColorField.setEnabled(false);
        customColorField.setToolTipText("Введите название вашего цвета");
        
        customPanel.add(customColorCheckBox);
        customPanel.add(customColorField);
        
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centerPanel.add(customPanel, gbc);
        
        answerButton = new JButton("Ответить");
        answerButton.setFont(new Font("Arial", Font.BOLD, 16));
        answerButton.setBackground(new Color(100, 150, 200));
        answerButton.setForeground(Color.WHITE);
        answerButton.setFocusPainted(false);
        answerButton.setPreferredSize(new Dimension(150, 45));
        
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(answerButton, gbc);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(230, 240, 250));
        bottomPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 200)),
            "📌 Ваш ответ"
        ));
        
        answerLabel = new JLabel(" Ожидание выбора...", SwingConstants.CENTER);
        answerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        answerLabel.setForeground(new Color(70, 130, 200));
        answerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        bottomPanel.add(answerLabel, BorderLayout.CENTER);
        
        customColorCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);
                customColorField.setEnabled(isSelected);
                comboBox.setEnabled(!isSelected);
                
                if (isSelected) {
                    customColorField.requestFocus();
                }
            }
        });
        
        answerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedColor;
                
                if (customColorCheckBox.isSelected()) {
                    String customColor = customColorField.getText().trim();
                    if (customColor.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, 
                            "Пожалуйста, введите название цвета!", 
                            "Внимание", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    selectedColor = customColor;
                } else {
                    selectedColor = (String) comboBox.getSelectedItem();
                }
                
                answerLabel.setText("Ваш любимый цвет: " + selectedColor + "!");
                answerLabel.setForeground(new Color(50, 150, 50));
                
                changeBottomPanelColor(selectedColor);
                
                answerButton.setBackground(new Color(70, 120, 170));
                Timer resetColor = new Timer(150, evt -> answerButton.setBackground(new Color(100, 150, 200)));
                resetColor.setRepeats(false);
                resetColor.start();
            }
        });
        
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
    
    private void changeBottomPanelColor(String colorName) {
        Color color = getColorFromName(colorName);
        if (color != null) {
            JPanel bottomPanel = (JPanel) answerLabel.getParent();
            bottomPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(color),
                " Ваш ответ"
            ));
            answerLabel.setForeground(color);
        }
    }
    
    private Color getColorFromName(String colorName) {
        String lowerName = colorName.toLowerCase();
        
        switch (lowerName) {
            case "красный":
            case "red":
                return Color.RED;
            case "синий":
            case "blue":
                return Color.BLUE;
            case "зеленый":
            case "зелёный":
            case "green":
                return Color.GREEN;
            case "желтый":
            case "жёлтый":
            case "yellow":
                return Color.YELLOW;
            case "фиолетовый":
            case "purple":
                return new Color(128, 0, 128);
            case "оранжевый":
            case "orange":
                return Color.ORANGE;
            case "розовый":
            case "pink":
                return Color.PINK;
            case "черный":
            case "black":
                return Color.BLACK;
            case "белый":
            case "white":
                return Color.WHITE;
            default:
                return null;
        }
    }
}