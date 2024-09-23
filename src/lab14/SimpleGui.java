package lab14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleGui implements ActionListener {
    JFrame frame;
    JButton button;

    public static void main(String[] args) {
        SimpleGui gui = new SimpleGui();
        gui.go();
    }

    public void go() {
        frame = new JFrame("Simple GUI");  // Создаем окно с заголовком
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("change color");
        button.addActionListener(this);

        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(500, 500);
        frame.setVisible(true);  // Показываем окно
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.repaint();
    }
}