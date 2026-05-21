import java.awt.*;
import javax.swing.*;

public class BookSpinner {
    
    private JFrame frame;
    private JSpinner spinner;
    private JLabel answerLabel;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookSpinner().createAndShowGUI());
    }
    
    public void createAndShowGUI() {
        frame = new JFrame("Сколько книг взять в лагерь?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(100, 150, 200));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel questionLabel = new JLabel("Сколько книг вы возьмёте с собой в лагерь?");
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        questionLabel.setForeground(Color.WHITE);
        topPanel.add(questionLabel);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        
        SpinnerNumberModel model = new SpinnerNumberModel(5, 0, 50, 1);
        spinner = new JSpinner(model);
        
        spinner.setFont(new Font("Arial", Font.BOLD, 24));
        spinner.setMaximumSize(new Dimension(120, 50));
        spinner.setPreferredSize(new Dimension(120, 50));
        
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
            defaultEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
            defaultEditor.getTextField().setFont(new Font("Arial", Font.BOLD, 24));
        }
        
        JPanel spinnerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        spinnerWrapper.setOpaque(false);
        spinnerWrapper.add(spinner);
        
        JLabel booksLabel = new JLabel("книг(и)");
        booksLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        booksLabel.setForeground(new Color(70, 130, 200));
        
        JPanel labelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelWrapper.setOpaque(false);
        labelWrapper.add(booksLabel);
        
        centerPanel.add(spinnerWrapper);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(labelWrapper);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        JButton answerButton = new JButton("Ответить");
        answerButton.setFont(new Font("Arial", Font.BOLD, 16));
        answerButton.setBackground(new Color(100, 150, 200));
        answerButton.setForeground(Color.WHITE);
        answerButton.setFocusPainted(false);
        answerButton.setPreferredSize(new Dimension(150, 45));
        answerButton.setMaximumSize(new Dimension(150, 45));
        answerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(answerButton);
        
        centerPanel.add(buttonWrapper);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(230, 240, 250));
        bottomPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 200)),
            "Ответ"
        ));
        
        answerLabel = new JLabel("Не выбрано", SwingConstants.CENTER);
        answerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        answerLabel.setForeground(new Color(70, 130, 200));
        answerLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        bottomPanel.add(answerLabel, BorderLayout.CENTER);
        
        answerButton.addActionListener(e -> {
            int value = (Integer) spinner.getValue();
            String booksWord = getBooksWord(value);
            answerLabel.setText("Вы возьмёте " + value + " " + booksWord);
            answerLabel.setForeground(new Color(50, 150, 50));
        });
        
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
    
    private String getBooksWord(int count) {
        if (count == 1) {
            return "книгу";
        } else if (count >= 2 && count <= 4) {
            return "книги";
        } else {
            return "книг";
        }
    }
}