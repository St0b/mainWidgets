import javax.swing.*;
import java.awt.*;

public class FirstLabel {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Моя первая программа");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null); 
            
            JLabel label = new JLabel("Моя первая надпись!", SwingConstants.CENTER);
            
            Font font = new Font("Segoe UI", Font.ITALIC, 50);
            label.setFont(font);
            
            label.setForeground(new Color(70, 130, 200)); // Синий цвет
            
            frame.add(label, BorderLayout.CENTER);
            
            frame.setVisible(true);
        });
    }
}