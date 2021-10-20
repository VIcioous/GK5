package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class NewWindow extends JPanel{
    JFrame frame = new JFrame();
    JLabel label = new JLabel();
    BufferedImage img;

    NewWindow(BufferedImage image) {
        this.img = image;
        label.setBounds(0, 0, image.getWidth(), image.getHeight());

        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.setLayout(null);
        frame.setIconImage(img);
        frame.setContentPane(new JLabel(new ImageIcon(img)));
        frame.pack();
        frame.setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
        this.repaint();
    }
}
