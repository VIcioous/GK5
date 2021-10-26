package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canva extends JPanel {
    private final JTextField pathFile = new JTextField();
    private final JButton readButton = new JButton();

    private final JButton extendHistogramButton = new JButton();
    private final JButton equalizeHistogramButton = new JButton();
    private final JButton binary1Button = new JButton();
    private final JButton binary2Button = new JButton();
    private final JButton binary3Button = new JButton();
    private final JSlider binarizationSlider = new JSlider(0,255);
    private final JSlider percentSlider = new JSlider(0,100);
    private BufferedImage imageJPG;

    private final JPGService jpgService;
    private final HistogramService histogramService;
    private final BinarizationService binarizationService;
    
    Canva()
    {
        histogramService = new HistogramService();
        jpgService = new JPGService();
        binarizationService = new BinarizationService();
        setButtons();
    }



    private void binarize3() {
        BufferedImage newImage = binarizationService.getEntropySelectionImage(imageJPG);
        NewWindow binarization = new NewWindow(newImage);
    }

    private void binarize2(BufferedImage image) {
        BufferedImage newImage = binarizationService.getPercentBlackSelection(image,percentSlider.getValue());
        NewWindow binarization = new NewWindow(newImage);
    }

    private void binarize1(BufferedImage image) {
        BufferedImage newImage = binarizationService.getManualyThresholdedImage(image,binarizationSlider.getValue());
        NewWindow binarization = new NewWindow(newImage);
    }

    private void equalizeHistogram(BufferedImage imageJPG) {
        BufferedImage newImage = histogramService.getEqualizedHistogram(imageJPG);
        NewWindow histogram = new NewWindow(newImage);
    }

    private void extendHistogram(BufferedImage imageJPG) {
        BufferedImage newImage = histogramService.getExtendedHistogram(imageJPG);
        NewWindow histogram = new NewWindow(newImage);
    }

    private void readFile(String path) {
        imageJPG = jpgService.readJPG(path);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageJPG, 0, 0, this);
        repaint();
    }
    private void setButtons() {
        readButton.addActionListener(e -> readFile(pathFile.getText()));
        readButton.setText("Read");
        readButton.setBounds(30, 730, 100, 25);
        pathFile.setBounds(30, 700, 100, 25);

        binarizationSlider.setBounds(400,700,250,25);
        percentSlider.setBounds(400,650,250,25);

        extendHistogramButton.setBounds(150, 730, 100, 25);
        extendHistogramButton.addActionListener(e -> extendHistogram(imageJPG));
        equalizeHistogramButton.setBounds(280, 730, 100, 25);
        equalizeHistogramButton.addActionListener(e -> equalizeHistogram(imageJPG));
        binary1Button.setBounds(410, 730, 100, 25);
        binary1Button.addActionListener(e -> binarize1(imageJPG));
        binary2Button.setBounds(530, 730, 100, 25);
        binary2Button.addActionListener(e -> binarize2(imageJPG));
        binary3Button.setBounds(660, 730, 100, 25);
        binary3Button.addActionListener(e -> binarize3());


        extendHistogramButton.setText("extend");
        equalizeHistogramButton.setText("equalize");
        binary1Button.setText("Select");
        binary2Button.setText("PBS");
        binary3Button.setText("Entropy");

        this.setLayout(null);

        this.add(pathFile);
        this.add(readButton);
        this.add(extendHistogramButton);
        this.add(equalizeHistogramButton);
        this.add(binary1Button);
        this.add(binary2Button);
        this.add(binary3Button);
        this.add(binarizationSlider);
        this.add(percentSlider);
    }
}
