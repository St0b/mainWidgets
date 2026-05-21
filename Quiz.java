import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    
    private JFrame frame;
    private JLabel questionLabel;
    private JPanel answersPanel;
    private JLabel questionCounterLabel;
    private JButton nextButton;
    private JButton prevButton;
    private JButton finishButton;
    
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private List<String> userAnswers;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Quiz().createAndShowGUI());
    }
    
    public void createAndShowGUI() {
        createQuestions();
        userAnswers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            userAnswers.add("");
        }
        
        frame = new JFrame("Викторина - Проверь свои знания!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        
        // Верхняя панель с заголовком и счётчиком
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(46, 87, 134));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Викторина", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        questionCounterLabel = new JLabel("Вопрос 1 из " + questions.size(), SwingConstants.CENTER);
        questionCounterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        questionCounterLabel.setForeground(new Color(200, 200, 200));
        topPanel.add(questionCounterLabel, BorderLayout.SOUTH);
        
        // Центральная панель с вопросом и ответами
        JPanel centerPanel = new JPanel(new BorderLayout(10, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        centerPanel.setBackground(new Color(250, 250, 240));
        
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(questionLabel, BorderLayout.NORTH);
        
        answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        answersPanel.setBackground(new Color(250, 250, 240));
        
        JScrollPane scrollPane = new JScrollPane(answersPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(250, 250, 240));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Нижняя панель с кнопками
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setBackground(new Color(240, 240, 240));
        
        prevButton = new JButton("<< Назад");
        prevButton.setFont(new Font("Arial", Font.BOLD, 14));
        prevButton.setBackground(new Color(100, 150, 200));
        prevButton.setForeground(Color.WHITE);
        prevButton.setFocusPainted(false);
        prevButton.setEnabled(false);
        
        nextButton = new JButton("Далее >>");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(100, 150, 200));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        
        finishButton = new JButton("Завершить");
        finishButton.setFont(new Font("Arial", Font.BOLD, 14));
        finishButton.setBackground(new Color(46, 134, 87));
        finishButton.setForeground(Color.WHITE);
        finishButton.setFocusPainted(false);
        finishButton.setVisible(false);
        
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(finishButton);
        
        // Обработчики кнопок
        prevButton.addActionListener(e -> {
            saveCurrentAnswer();
            currentQuestionIndex--;
            loadQuestion();
            updateNavigationButtons();
        });
        
        nextButton.addActionListener(e -> {
            saveCurrentAnswer();
            currentQuestionIndex++;
            loadQuestion();
            updateNavigationButtons();
        });
        
        finishButton.addActionListener(e -> {
            saveCurrentAnswer();
            showResult();
        });
        
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        
        loadQuestion();
        frame.setVisible(true);
    }
    
    private void createQuestions() {
        questions = new ArrayList<>();
        
        // Вопрос 1: Обычный выбор из 4 вариантов
        List<String> answers1 = new ArrayList<>();
        answers1.add("Париж");
        answers1.add("Лондон");
        answers1.add("Берлин");
        answers1.add("Мадрид");
        questions.add(new Question(
            "Столица Франции?",
            "single",
            answers1,
            "Париж"
        ));
        
        // Вопрос 2: Несколько правильных ответов
        List<String> answers2 = new ArrayList<>();
        answers2.add("Java");
        answers2.add("Python");
        answers2.add("HTML");
        answers2.add("C++");
        questions.add(new Question(
            "Какие из этих языков являются языками программирования? (можно выбрать несколько)",
            "multiple",
            answers2,
            "Java;Python;C++"
        ));
        
        // Вопрос 3: Правда/Ложь
        List<String> answers3 = new ArrayList<>();
        answers3.add("Правда");
        answers3.add("Ложь");
        questions.add(new Question(
            "Земля вращается вокруг Солнца.",
            "single",
            answers3,
            "Правда"
        ));
        
        // Вопрос 4: Выбор из списка
        List<String> answers4 = new ArrayList<>();
        answers4.add("А.С. Пушкин");
        answers4.add("Л.Н. Толстой");
        answers4.add("Ф.М. Достоевский");
        answers4.add("Н.В. Гоголь");
        questions.add(new Question(
            "Кто написал роман 'Война и мир'?",
            "single",
            answers4,
            "Л.Н. Толстой"
        ));
        
        // Вопрос 5: Числовой ответ
        List<String> answers5 = new ArrayList<>();
        answers5.add("Текстовое поле");
        questions.add(new Question(
            "Сколько планет в Солнечной системе (включая Плутон)?",
            "number",
            answers5,
            "9"
        ));
        
        // Вопрос 6: Обычный вопрос
        List<String> answers6 = new ArrayList<>();
        answers6.add("Красный");
        answers6.add("Синий");
        answers6.add("Жёлтый");
        answers6.add("Зелёный");
        questions.add(new Question(
            "Какого цвета нет на флаге России?",
            "single",
            answers6,
            "Жёлтый"
        ));
    }
    
    private void loadQuestion() {
        Question q = questions.get(currentQuestionIndex);
        questionLabel.setText((currentQuestionIndex + 1) + ". " + q.getText());
        questionCounterLabel.setText("Вопрос " + (currentQuestionIndex + 1) + " из " + questions.size());
        
        answersPanel.removeAll();
        
        if (q.getType().equals("single")) {
            ButtonGroup group = new ButtonGroup();
            for (String answer : q.getAnswers()) {
                JRadioButton radioButton = new JRadioButton(answer);
                radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                radioButton.setBackground(new Color(250, 250, 240));
                radioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                if (userAnswers.get(currentQuestionIndex).equals(answer)) {
                    radioButton.setSelected(true);
                }
                
                radioButton.addActionListener(e -> {
                    userAnswers.set(currentQuestionIndex, answer);
                });
                
                group.add(radioButton);
                answersPanel.add(radioButton);
                answersPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } else if (q.getType().equals("multiple")) {
            for (String answer : q.getAnswers()) {
                JCheckBox checkBox = new JCheckBox(answer);
                checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                checkBox.setBackground(new Color(250, 250, 240));
                checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                String saved = userAnswers.get(currentQuestionIndex);
                if (saved != null && saved.contains(answer)) {
                    checkBox.setSelected(true);
                }
                
                checkBox.addActionListener(e -> {
                    String currentSaved = userAnswers.get(currentQuestionIndex);
                    if (checkBox.isSelected()) {
                        if (currentSaved.isEmpty()) {
                            userAnswers.set(currentQuestionIndex, answer);
                        } else {
                            userAnswers.set(currentQuestionIndex, currentSaved + ";" + answer);
                        }
                    } else {
                        String newValue = currentSaved.replace(answer, "").replace(";;", ";");
                        if (newValue.startsWith(";")) newValue = newValue.substring(1);
                        if (newValue.endsWith(";")) newValue = newValue.substring(0, newValue.length() - 1);
                        userAnswers.set(currentQuestionIndex, newValue);
                    }
                });
                
                answersPanel.add(checkBox);
                answersPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } else if (q.getType().equals("number")) {
            JTextField textField = new JTextField(10);
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textField.setMaximumSize(new Dimension(200, 30));
            textField.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            String saved = userAnswers.get(currentQuestionIndex);
            if (!saved.isEmpty()) {
                textField.setText(saved);
            }
            
            textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
                public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
                private void update() {
                    userAnswers.set(currentQuestionIndex, textField.getText());
                }
            });
            
            answersPanel.add(textField);
            answersPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        answersPanel.revalidate();
        answersPanel.repaint();
    }
    
    private void saveCurrentAnswer() {
        // Ответ уже сохраняется в реальном времени через слушатели
    }
    
    private void updateNavigationButtons() {
        prevButton.setEnabled(currentQuestionIndex > 0);
        
        if (currentQuestionIndex == questions.size() - 1) {
            nextButton.setVisible(false);
            finishButton.setVisible(true);
        } else {
            nextButton.setVisible(true);
            finishButton.setVisible(false);
        }
    }
    
    private void showResult() {
        calculateScore();
        
        String message = "Ваш результат:\n\n";
        message += "Правильных ответов: " + score + " из " + questions.size() + "\n";
        message += "Процент: " + (score * 100 / questions.size()) + "%\n\n";
        
        if (score == questions.size()) {
            message += "Отлично! Вы настоящий эрудит! \uD83C\uDF89";
        } else if (score >= questions.size() * 0.7) {
            message += "Хороший результат! Вы много знаете! \uD83D\uDC4F";
        } else if (score >= questions.size() * 0.5) {
            message += "Неплохо, но есть куда расти! \uD83D\uDE0A";
        } else {
            message += "Попробуйте ещё раз, удачи в следующий раз! \uD83D\uDCAA";
        }
        
        JOptionPane.showMessageDialog(frame, message, "Результаты викторины", JOptionPane.INFORMATION_MESSAGE);
        
        int retry = JOptionPane.showConfirmDialog(frame, 
            "Хотите пройти викторину заново?", 
            "Новая попытка", 
            JOptionPane.YES_NO_OPTION);
        
        if (retry == JOptionPane.YES_OPTION) {
            resetQuiz();
            loadQuestion();
            updateNavigationButtons();
        } else {
            System.exit(0);
        }
    }
    
    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String userAnswer = userAnswers.get(i);
            String correctAnswer = q.getCorrectAnswer();
            
            if (q.getType().equals("number")) {
                try {
                    if (userAnswer.equals(correctAnswer)) {
                        score++;
                    }
                } catch (Exception e) {
                    // Неправильный ответ
                }
            } else if (q.getType().equals("multiple")) {
                String[] userParts = userAnswer.split(";");
                String[] correctParts = correctAnswer.split(";");
                boolean allCorrect = true;
                for (String part : correctParts) {
                    if (!userAnswer.contains(part)) {
                        allCorrect = false;
                        break;
                    }
                }
                if (allCorrect && userParts.length == correctParts.length) {
                    score++;
                }
            } else {
                if (userAnswer.equals(correctAnswer)) {
                    score++;
                }
            }
        }
    }
    
    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        userAnswers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            userAnswers.add("");
        }
        nextButton.setVisible(true);
        finishButton.setVisible(false);
        prevButton.setEnabled(false);
    }
    
    class Question {
        private String text;
        private String type;
        private List<String> answers;
        private String correctAnswer;
        
        public Question(String text, String type, List<String> answers, String correctAnswer) {
            this.text = text;
            this.type = type;
            this.answers = answers;
            this.correctAnswer = correctAnswer;
        }
        
        public String getText() { return text; }
        public String getType() { return type; }
        public List<String> getAnswers() { return answers; }
        public String getCorrectAnswer() { return correctAnswer; }
    }
}