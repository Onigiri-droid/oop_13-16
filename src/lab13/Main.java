package lab13;

import javax.swing.*;
import java.awt.event.*;

public class Main implements ActionListener {
    JButton button;

    public static void main(String[] args) {
        new Main().go();  // Создаём экземпляр и вызываем метод go в одной строке
    }

    public void go() {
        JFrame frame = new JFrame("Simple GUI");  // Создаем окно с заголовком
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        button = new JButton("Click Me");
        button.addActionListener(this);  // Добавляем обработчик нажатия для кнопки
        frame.add(button);  // Добавляем кнопку в окно

        frame.setVisible(true);  // Показываем окно
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        button.setText("Clicked Me!");  // Меняем текст кнопки по нажатию
    }
}