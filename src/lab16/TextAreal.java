package lab16;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextAreal implements ActionListener {
    JTextArea text;

    public static void main(String[] args) {
        TextAreal gui = new TextAreal();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton button = new JButton("Click Me");
        button.addActionListener(this);
        text = new JTextArea(10, 20);
        text.setLineWrap(true);

        JScrollPane scroller = new JScrollPane(text);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        panel.add(scroller);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, button);

        frame.setSize(350, 300);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        text.append("Button clicked\n");
    }
}
