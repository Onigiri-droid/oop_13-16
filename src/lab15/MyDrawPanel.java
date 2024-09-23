package lab15;

import javax.sound.midi.ShortMessage;
import javax.swing.*;
import java.awt.*;

public class MyDrawPanel extends JPanel {
    boolean msg = false;

    public void controlChange(ShortMessage event) {
        msg = true;
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if(msg) {
            Graphics2D g2d = (Graphics2D) g;

            int r = (int)(Math.random()*255);
            int gr = (int)(Math.random()*255);
            int b = (int)(Math.random()*255);

            g.setColor(new Color(r,gr,b));

            int ht = (int)(Math.random() * 120 + 10);
            int width = (int)(Math.random() * 120 + 10);
            int x = (int)((Math.random() * 40) + 10);
            int y = (int)((Math.random() * 40) + 10);
            g.fillRect(x,y,width,ht);
            msg = false;
        }
    }
}
