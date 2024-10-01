package MC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MyCalculate {
    JTextArea textArea;
    String currentExpression = "";
    boolean lastActionWasOperation = false;
    boolean hasResult = false;  // Флаг для проверки, был ли выведен результат
    int maxLines = 5;
    JFrame shapesFrame; // Окно для отображения фигур
    ShapesPanel shapesPanel; // Панель для рисования фигур

    public static void main(String[] args) {
        new MyCalculate().go();
    }

    public void go() {
        JFrame frame = new JFrame("Simple Calculator");
        JPanel numberPanel = new JPanel(new GridLayout(4, 3));
        JPanel operationPanel = new JPanel(new GridLayout(4, 1));
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);

        String[] operations = {"+", "-", "*", "/"};
        for (String op : operations) {
            addButton(op, operationPanel, true);
        }
        for (int i = 1; i <= 9; i++) {
            addButton(String.valueOf(i), numberPanel, false);
        }
        addButton("0", numberPanel, false);
        addButton("C", numberPanel, true);
        addButton("=", numberPanel, true);

        frame.add(BorderLayout.NORTH, textArea);
        frame.add(BorderLayout.CENTER, numberPanel);
        frame.add(BorderLayout.EAST, operationPanel);
        frame.setSize(400, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Создаем второе окно для отображения фигур
        shapesFrame = new JFrame("Shapes");
        shapesPanel = new ShapesPanel();
        shapesFrame.add(shapesPanel);
        shapesFrame.setSize(800, 600);
        shapesFrame.setVisible(true);
    }

    private void addButton(String label, JPanel panel, boolean isOperation) {
        JButton button = new JButton(label);
        button.addActionListener(e -> {
            handleInput(label, isOperation);
            shapesPanel.repaint(); // Перерисовываем фигуры при нажатии кнопки
        });
        panel.add(button);
    }

    private void handleInput(String input, boolean isOperation) {
        if (input.equals("C")) {
            clear();
        } else if (input.equals("=")) {
            calculateResult();
        } else {
            if (hasResult && !isOperation) {
                clear();
            }
            if (isOperation) {
                handleOperation(input);
            } else {
                appendToExpression(input);
            }
        }
    }

    private void handleOperation(String op) {
        if (currentExpression.isEmpty()) {
            return;
        }

        if (lastActionWasOperation) {
            currentExpression = currentExpression.substring(0, currentExpression.length() - 1) + op;
            replaceLastCharInTextArea(op);
        } else {
            currentExpression += op;
            lastActionWasOperation = true;
            textArea.append(op);
        }
    }

    private void appendToExpression(String value) {
        currentExpression += value;
        lastActionWasOperation = false;
        textArea.append(value);
    }

    private void calculateResult() {
        if (currentExpression.isEmpty() || lastActionWasOperation) {
            return;
        }
        try {
            double result = eval(currentExpression);
            textArea.append(" = " + result + "\n");
            currentExpression = String.valueOf(result);
            hasResult = true;
            limitLines();
        } catch (ArithmeticException ex) {
            textArea.append(" Error\n");
            currentExpression = "";
            limitLines();
        }
    }

    private double eval(String expression) throws ArithmeticException {
        try {
            String[] tokens = expression.split("(?=[-+*/])|(?<=[-+*/])");
            double result = Double.parseDouble(tokens[0]);

            for (int i = 1; i < tokens.length; i += 2) {
                String operator = tokens[i];
                double operand = Double.parseDouble(tokens[i + 1]);

                switch (operator) {
                    case "+" -> result += operand;
                    case "-" -> result -= operand;
                    case "*" -> result *= operand;
                    case "/" -> {
                        if (operand == 0) throw new ArithmeticException("Division by zero");
                        result /= operand;
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new ArithmeticException("Invalid expression");
        }
    }

    private void replaceLastCharInTextArea(String newChar) {
        String text = textArea.getText();
        textArea.setText(text.substring(0, text.length() - 1) + newChar);
    }

    private void clear() {
        textArea.setText("");
        currentExpression = "";
        hasResult = false;
    }

    private void limitLines() {
        String[] lines = textArea.getText().split("\n");
        if (lines.length > maxLines) {
            textArea.setText(String.join("\n", java.util.Arrays.copyOfRange(lines, lines.length - maxLines, lines.length)));
        }
    }

    // Панель для рисования случайных фигур
    class ShapesPanel extends JPanel {
        Random rand = new Random();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Очищаем область перед рисованием новых фигур
            g.clearRect(0, 0, getWidth(), getHeight());

            // Генерация случайных фигур
            for (int i = 0; i < 5; i++) {
                int x = rand.nextInt(getWidth());
                int y = rand.nextInt(getHeight());
                int width = rand.nextInt(50) + 30;
                int height = rand.nextInt(50) + 30;
                Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                g.setColor(randomColor);

                switch (rand.nextInt(4)) { // Теперь 4 варианта: прямоугольник, овал, скругленный прямоугольник, треугольник
                    case 0 -> g.fillRect(x, y, width, height); // Прямоугольник
                    case 1 -> g.fillOval(x, y, width, height); // Овал
                    case 2 -> g.fillRoundRect(x, y, width, height, 20, 20); // Скругленный прямоугольник
                    case 3 -> drawTriangle(g, x, y, width, height); // Треугольник
                }
            }
        }

        // Метод для рисования треугольника
        private void drawTriangle(Graphics g, int x, int y, int width, int height) {
            int[] xPoints = {x, x + width / 2, x + width};
            int[] yPoints = {y + height, y, y + height};
            g.fillPolygon(xPoints, yPoints, 3); // Рисуем треугольник по трем точкам
        }
    }
}