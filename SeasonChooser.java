import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SeasonChooser {
    
    private JFrame frame;
    private JRadioButton springButton;
    private JRadioButton summerButton;
    private JRadioButton autumnButton;
    private JRadioButton winterButton;
    private ButtonGroup seasonGroup;
    private JLabel answerLabel;
    private JButton answerButton;
    private JLabel seasonIconLabel;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeasonChooser().createAndShowGUI());
    }
    
    public void createAndShowGUI() {
        frame = new JFrame("Выбор любимого времени года");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        
        // Верхняя панель с заголовком
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(46, 134, 87));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel(" Какое время года вы любите больше всего? ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        
        // Центральная панель с радио-кнопками
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(250, 250, 240));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Создаём радио-кнопки
        springButton = new JRadioButton(" Весна");
        summerButton = new JRadioButton(" Лето");
        autumnButton = new JRadioButton(" Осень");
        winterButton = new JRadioButton(" Зима");
        
        // Настройка шрифта для радио-кнопок
        Font radioFont = new Font("Segoe UI", Font.PLAIN, 16);
        springButton.setFont(radioFont);
        summerButton.setFont(radioFont);
        autumnButton.setFont(radioFont);
        winterButton.setFont(radioFont);
        
        springButton.setBackground(new Color(250, 250, 240));
        summerButton.setBackground(new Color(250, 250, 240));
        autumnButton.setBackground(new Color(250, 250, 240));
        winterButton.setBackground(new Color(250, 250, 240));
        
        // Объединяем радио-кнопки в группу
        seasonGroup = new ButtonGroup();
        seasonGroup.add(springButton);
        seasonGroup.add(summerButton);
        seasonGroup.add(autumnButton);
        seasonGroup.add(winterButton);
        
        // Панель для радио-кнопок (2x2 сетка)
        JPanel radioPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        radioPanel.setOpaque(false);
        radioPanel.add(springButton);
        radioPanel.add(summerButton);
        radioPanel.add(autumnButton);
        radioPanel.add(winterButton);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(radioPanel, gbc);
        
        answerButton = new JButton("Ответить");
        answerButton.setFont(new Font("Arial", Font.BOLD, 16));
        answerButton.setBackground(new Color(46, 134, 87));
        answerButton.setForeground(Color.WHITE);
        answerButton.setFocusPainted(false);
        answerButton.setPreferredSize(new Dimension(150, 45));
        
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(answerButton, gbc);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(235, 245, 235));
        bottomPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(46, 134, 87)),
            "📌 Ваш ответ"
        ));
        
        answerLabel = new JLabel(" Выберите время года...", SwingConstants.CENTER);
        answerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        answerLabel.setForeground(new Color(46, 134, 87));
        answerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        bottomPanel.add(answerLabel, BorderLayout.CENTER);
        
        answerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSeason = getSelectedSeason();
                
                if (selectedSeason == null) {
                    JOptionPane.showMessageDialog(frame, 
                        "Пожалуйста, выберите время года!", 
                        "Внимание", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String message = getSeasonMessage(selectedSeason);
                answerLabel.setText(message);
                answerLabel.setForeground(new Color(200, 70, 50));
                
                answerButton.setBackground(new Color(36, 114, 67));
                Timer resetColor = new Timer(150, evt -> answerButton.setBackground(new Color(46, 134, 87)));
                resetColor.setRepeats(false);
                resetColor.start();
                
                changeBorderColor(selectedSeason);
            }
        });
        
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
    
    private String getSelectedSeason() {
        if (springButton.isSelected()) return "Весна";
        if (summerButton.isSelected()) return "Лето";
        if (autumnButton.isSelected()) return "Осень";
        if (winterButton.isSelected()) return "Зима";
        return null;
    }
    
    private String getSeasonMessage(String season) {
        switch (season) {
            case "Весна":
                return " Весна — время пробуждения природы и цветения! ";
            case "Лето":
                return " Лето — пора отпусков, мороженого и тёплых дней! ";
            case "Осень":
                return " Осень — время золотых листьев и уютных вечеров! ";
            case "Зима":
                return " Зима — время снега, праздников и волшебства! ";
            default:
                return "Ваше любимое время года: " + season + "!";
        }
    }
    
    private void updateSeasonIcon() {
        if (springButton.isSelected()) {
            seasonIconLabel.setText(" ");
            seasonIconLabel.setToolTipText("Весна");
        } else if (summerButton.isSelected()) {
            seasonIconLabel.setText(" ");
            seasonIconLabel.setToolTipText("Лето");
        } else if (autumnButton.isSelected()) {
            seasonIconLabel.setText(" ");
            seasonIconLabel.setToolTipText("Осень");
        } else if (winterButton.isSelected()) {
            seasonIconLabel.setText(" ");
            seasonIconLabel.setToolTipText("Зима");
        } else {
            seasonIconLabel.setText(" ");
            seasonIconLabel.setToolTipText("Не выбрано");
        }
    }
    
    private void changeBorderColor(String season) {
        JPanel bottomPanel = (JPanel) answerLabel.getParent();
        Color seasonColor;
        
        switch (season) {
            case "Весна":
                seasonColor = new Color(255, 182, 193); // Розовый
                break;
            case "Лето":
                seasonColor = new Color(255, 215, 0); // Золотой
                break;
            case "Осень":
                seasonColor = new Color(255, 140, 0); // Оранжевый
                break;
            case "Зима":
                seasonColor = new Color(135, 206, 235); // Небесно-голубой
                break;
            default:
                seasonColor = new Color(46, 134, 87);
        }
        
        bottomPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(seasonColor),
            "📌 Ваш ответ"
        ));
    }
}