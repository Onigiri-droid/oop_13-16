package lab14;

import javax.swing.*;
import java.awt.*;

public class HardAnimation {
    int x = 0;
    int y = 0;
    int diameter = 100; // Начальный диаметр круга

    public static void main(String[] args) {
        HardAnimation gui = new HardAnimation();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyDrawP draw = new MyDrawP();

        frame.getContentPane().add(draw);
        frame.setSize(400, 400); // Размер окна
        frame.setVisible(true);

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int step = 5;  // Шаг перемещения

        // Двигаемся по периметру и смещаемся к центру
        while (diameter > 10) {
            // Верхняя сторона
            while (x < frameWidth - diameter) {
                x += step;
                draw.repaint();
                sleep();
            }

            // Правая сторона
            while (y < frameHeight - diameter) {
                y += step;
                draw.repaint();
                sleep();
            }

            // Нижняя сторона
            while (x > 0) {
                x -= step;
                draw.repaint();
                sleep();
            }

            // Левая сторона
            while (y > 0) {
                y -= step;
                draw.repaint();
                sleep();
            }

            // Уменьшаем диаметр и приближаем к центру
            diameter -= 10;
            x += 5; // Смещаем к центру
            y += 5;

            // Прерываем анимацию, если круг слишком мал
            if (diameter <= 10) break;
        }
    }

    // Метод для небольшого ожидания между кадрами
    private void sleep() {
        try {
            Thread.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyDrawP extends JPanel {
        public void paint(Graphics g) {
            g.setColor(Color.green);
            g.fillOval(x, y, diameter, diameter); // Рисуем круг с текущими координатами и диаметром
        }
    }
}
