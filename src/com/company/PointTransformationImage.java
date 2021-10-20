package com.company;

import java.awt.image.BufferedImage;

public class PointTransformationImage {

    public BufferedImage getGrayImage(BufferedImage imageJPG) {
        for (int y =0; y< imageJPG.getHeight();y++)
        {
            for (int x=0;x< imageJPG.getWidth();x++)
            {
                int rgb = imageJPG.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                double rr = Math.pow(r / 255.0, 2.2);
                double gg = Math.pow(g / 255.0, 2.2);
                double bb = Math.pow(b / 255.0, 2.2);


                double lum = 0.2126 * rr + 0.7152 * gg + 0.0722 * bb;

                int grayLevel = (int) (255.0 * Math.pow(lum, 1.0 / 2.2));
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                imageJPG.setRGB(x, y, gray);
            }
        }
        return imageJPG;
    }
}
