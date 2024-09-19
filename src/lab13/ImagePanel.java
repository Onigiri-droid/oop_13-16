package lab13;

import java.awt.*;
import javax.swing.*;

public class ImagePanel extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Не забываем вызывать родительский метод
        java.awt.Image image = new ImageIcon("image/ww-img.jpg").getImage();
        g.drawImage(image, 3, 4, this);
    }

    public static void main(String[] args) {
        // Создание окна
        JFrame frame = new JFrame("Paint Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 750);

        // Добавление панели с рисованием в окно
        ImagePanel panel = new ImagePanel();
        frame.add(panel);

        // Отображение окна
        frame.setVisible(true);
    }
}
