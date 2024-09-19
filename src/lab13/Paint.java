package lab13;

import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Paint extends JPanel {

    @Override
    //    public void paintComponent(Graphics g) {
//        super.paintComponent(g); // вызов метода родителя для корректной отрисовки
//        g.setColor(Color.green);
//        g.fillRect(20, 50, 100, 100);
//    }

//    public void paintComponent(Graphics g) {
//        super.paintComponent(g); // вызов метода родителя для корректной отрисовки
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
//
//        int red = (int)(Math.random() * 255);
//        int green = (int)(Math.random() * 255);
//        int blue = (int)(Math.random() * 255);
//
//        Color randomColor = new Color(red, green, blue);
//        g.setColor(randomColor);
//        g.fillOval(70, 70, 100, 100);
//    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // вызов метода родителя для корректной отрисовки

        // Включение антиалиасинга для более плавной отрисовки
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Генерация двух случайных цветов
        Color randomColor1 = getRandomColor();
        Color randomColor2 = getRandomColor();

        // Создание градиента и рисование круга
        GradientPaint gp = new GradientPaint(70, 70, randomColor1, 150, 150, randomColor2);
        g2d.setPaint(gp);
        g2d.fillOval(70, 70, 100, 100);
    }

    // Метод для генерации случайного цвета
    private Color getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    public static void main(String[] args) {
        // Создание окна
        JFrame frame = new JFrame("Paint Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Добавление панели с рисованием
        frame.add(new Paint());

        // Отображение окна
        frame.setVisible(true);
    }
}
