package lab14;

import javax.swing.*;
import java.awt.*;

public class SimleAnimation {
    int x = 70;
    int y = 70;

    public static void main(String[] args) {
        SimleAnimation gui = new SimleAnimation();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyDrawP draw = new MyDrawP();

        frame.getContentPane().add(draw);
        frame.setSize(300, 300);
        frame.setVisible(true);


        for (int i = 0; i < 130; i++) {
            x++;
            y++;

            draw.repaint();

            try {
                Thread.sleep(50);
            } catch (Exception e) {}
        }
    }

    class MyDrawP extends JPanel {
        public void paint(Graphics g) {
//            g.setColor(Color.white);
//            g.fillOval(0, 0, this.getWidth(), this.getHeight());

            g.setColor(Color.green);
            g.fillOval(x, y, 40, 40);
        }
    }
}
