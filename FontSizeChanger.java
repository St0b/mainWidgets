import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class FontSizeChanger {
    
    private JFrame frame;
    private JLabel label;
    private JSlider slider;
    private JLabel sizeLabel;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FontSizeChanger().createAndShowGUI());
    }
    
    public void createAndShowGUI() {
        frame = new JFrame("Изменение размера шрифта");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        
        label = new JLabel("Моя первая надпись!", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.ITALIC, 50));
        label.setForeground(new Color(70, 130, 200));
        
        JPanel sliderPanel = new JPanel(new BorderLayout(10, 10));
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sliderPanel.setBackground(new Color(240, 240, 240));
        
        JLabel sliderLabel = new JLabel("Размер шрифта:", SwingConstants.CENTER);
        sliderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        slider = new JSlider(JSlider.HORIZONTAL, 5, 100, 50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(false);
        
        sizeLabel = new JLabel("Текущий размер: 50 px", SwingConstants.CENTER);
        sizeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int fontSize = slider.getValue();
                updateFontSize(fontSize);
                sizeLabel.setText("Текущий размер: " + fontSize + " px");
            }
        });
        
        sliderPanel.add(sliderLabel, BorderLayout.NORTH);
        sliderPanel.add(slider, BorderLayout.CENTER);
        sliderPanel.add(sizeLabel, BorderLayout.SOUTH);
        
        frame.add(label, BorderLayout.CENTER);
        frame.add(sliderPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
    
    private void updateFontSize(int fontSize) {
        Font currentFont = label.getFont();
        Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), fontSize);
        label.setFont(newFont);
    }
}