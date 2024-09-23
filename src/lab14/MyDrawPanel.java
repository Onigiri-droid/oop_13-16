package lab14;

import javax.swing.*;
import java.awt.*;

class MyDrawPanel extends JPanel {
    public void paint(Graphics g) {
        super.paintComponent(g); // вызов метода родителя для корректной отрисовки
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        int red = (int)(Math.random() * 255);
        int green = (int)(Math.random() * 255);
        int blue = (int)(Math.random() * 255);

        Color randomColor = new Color(red, green, blue);
        g.setColor(randomColor);
        g.fillOval(70, 70, 100, 100);
    }
}
