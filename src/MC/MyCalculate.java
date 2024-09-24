package MC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyCalculate {
    JTextArea textArea;
    JFrame frame;
    JPanel panel, numberPanel, operationPanel;
    String currentExpression = "";
    int maxLines = 5;

    public static void main(String[] args) {
        new MyCalculate().go();
    }

    public void go() {
        frame = new JFrame("Мой простой калькулятор");

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textArea = new JTextArea(10, 20);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(4, 3)); // Панель для цифр 0-9

        operationPanel = new JPanel();
        operationPanel.setLayout(new GridLayout(4, 1)); // Панель для операций

        // Добавляем кнопки цифр 1-9
        for (int i = 1; i < 10; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(new NumberButtonListener());
            numberPanel.add(button);
        }

        // Добавляем кнопки операций в отдельный столбик
        addButtonWithListener("+", operationPanel);
        addButtonWithListener("-", operationPanel);
        addButtonWithListener("*", operationPanel);
        addButtonWithListener("/", operationPanel);

        // Добавляем кнопку "C" для очистки
        JButton clearButton = new JButton("C");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(""); // Очищаем текстовое поле
                currentExpression = ""; // Сбрасываем текущее выражение
            }
        });
        numberPanel.add(clearButton);

        // Добавляем кнопку 0
        JButton zeroButton = new JButton("0");
        zeroButton.addActionListener(new NumberButtonListener());
        numberPanel.add(zeroButton);

        // Добавляем кнопку "="
        JButton equalsButton = new JButton("=");
        equalsButton.addActionListener(new OperationButtonListener());
        numberPanel.add(equalsButton);

        // Добавляем панели в основную панель
        panel.add(BorderLayout.NORTH, textArea);
        panel.add(BorderLayout.CENTER, numberPanel);
        panel.add(BorderLayout.EAST, operationPanel);

        frame.getContentPane().add(panel);
        frame.setSize(400, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Метод для создания кнопок операций
    private void addButtonWithListener(String label, JPanel panel) {
        JButton button = new JButton(label);
        button.addActionListener(new OperationButtonListener());
        panel.add(button);
    }

    // Слушатель для кнопок с цифрами
    class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Если текущее выражение содержит Error, очищаем его
            if (currentExpression.equals("Error")) {
                textArea.setText("");
                currentExpression = "";
            }

            String buttonLabel = e.getActionCommand();
            textArea.append(buttonLabel);
            currentExpression += buttonLabel;
        }
    }

    // Слушатель для кнопок с операциями
    class OperationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonLabel = e.getActionCommand();
            if (buttonLabel.equals("=")) {
                if (currentExpression.isEmpty() || currentExpression.matches(".+[/*+-]$") || currentExpression.equals("Error")) {
                    return; // Пропускаем вычисление при некорректном выражении
                }
                try {
                    int result = evaluateExpression(currentExpression);
                    textArea.append(" = " + result + "\n");
                    currentExpression = String.valueOf(result);
                    limitLines();
                } catch (ArithmeticException ex) {
                    if (ex.getMessage().equals("Division by zero")) {
                        textArea.append(" Error\n");
                    }
                    currentExpression = "Error";
                    limitLines();
                }
            } else {
                // Если текущим выражением является "Error", очищаем его перед добавлением операций
                if (currentExpression.equals("Error")) {
                    textArea.setText("");
                    currentExpression = "";
                }

                if (currentExpression.matches(".+[/*+-]$")) {
                    currentExpression = currentExpression.substring(0, currentExpression.length() - 1) + buttonLabel;
                    replaceLastCharInTextArea(buttonLabel);
                } else if (!currentExpression.isEmpty() || buttonLabel.equals("-")) {
                    currentExpression += buttonLabel;
                    textArea.append(buttonLabel);
                }
            }
        }

        private int evaluateExpression(String expression) {
            String[] tokens = expression.split("([*/+-])");
            if (tokens.length != 2) throw new ArithmeticException();
            int operand1 = Integer.parseInt(tokens[0]);
            int operand2 = Integer.parseInt(tokens[1]);
            char operator = expression.charAt(tokens[0].length());
            return switch (operator) {
                case '+' -> operand1 + operand2;
                case '-' -> operand1 - operand2;
                case '*' -> operand1 * operand2;
                case '/' -> {
                    if (operand2 == 0) throw new ArithmeticException("Division by zero");
                    yield operand1 / operand2;
                }
                default -> throw new ArithmeticException();
            };
        }

        private void replaceLastCharInTextArea(String newChar) {
            String text = textArea.getText();
            textArea.setText(text.substring(0, text.length() - 1) + newChar);
        }

        private void limitLines() {
            String[] lines = textArea.getText().split("\n");
            if (lines.length > maxLines) {
                textArea.setText(String.join("\n", java.util.Arrays.copyOfRange(lines, lines.length - maxLines, lines.length)));
            }
        }
    }
}