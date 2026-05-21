import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Clicker {
    
    private int clickCount = 0;
    private JFrame frame;
    private JLabel counterLabel;
    private JButton clickButton;
    private JButton resetButton;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Clicker().createAndShowGUI());
    }
    
    public void createAndShowGUI() {
        // Создаём главное окно
        frame = new JFrame("Кликер");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        
        // Панель для надписи (центр)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));
        
        // Создаём надпись с счётчиком
        counterLabel = new JLabel("Нажатий: 0", SwingConstants.CENTER);
        counterLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        counterLabel.setForeground(new Color(70, 130, 200));
        centerPanel.add(counterLabel);
        
        // Панель для кнопок (низ)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        // Создаём кнопку для кликов
        clickButton = new JButton("Нажми меня!");
        clickButton.setFont(new Font("Arial", Font.BOLD, 18));
        clickButton.setPreferredSize(new Dimension(150, 50));
        clickButton.setBackground(new Color(100, 150, 200));
        clickButton.setForeground(Color.WHITE);
        clickButton.setFocusPainted(false);
        
        // Создаём кнопку сброса
        resetButton = new JButton("Сброс");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setPreferredSize(new Dimension(100, 40));
        resetButton.setBackground(new Color(200, 100, 100));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        
        // Добавляем обработчики событий
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount++;
                updateCounter();
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount = 0;
                updateCounter();
            }
        });
        
        buttonPanel.add(clickButton);
        buttonPanel.add(resetButton);
        
        // Добавляем всё в окно
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
    
    private void updateCounter() {
        counterLabel.setText("Нажатий: " + clickCount);
    }
}