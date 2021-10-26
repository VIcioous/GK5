package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BinarizationService {

    private final PointTransformationImage pointTransformationImage = new PointTransformationImage();

    public BufferedImage getManualyThresholdedImage(BufferedImage image, int value) {

        BufferedImage newImage = new BufferedImage(
                image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_BGR);
        int c=0;
        for (int y =0; y< image.getHeight();y++)
        {
            for (int x=0;x< image.getWidth();x++)
            {

                int sum = getGrayScale(image.getRGB(x,y));


                if(sum>=value)
                {
                    int r=255;
                    int g=255;
                    int b=255;
                     c =(r << 16) | (g << 8) |b;
                }
                else
                {
                    int r=0;
                    int g=0;
                    int b=0;
                     c =(r << 16) | (g << 8) |b;
                }
                newImage.setRGB(x,y,c);
            }
        }
        return newImage;
    }

    private int  getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;


        int gray = (int)(0.299 * r + 0.587 * g + 0.114 * b);


        return gray;
}

    public BufferedImage getPercentBlackSelection(BufferedImage image, int value) {
         int[] bits = new int[256];
         float v = (float) value/10000;
        int limit = (int)(image.getHeight()*image.getWidth()*v);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int bit = getGrayScale(image.getRGB(x, y));
                bits[bit]++;
            }
        }
        int step =0;
        for(int i=0;i<256;i++)
        {
            if(bits[i]>=limit)
                step++;
        }
        return getManualyThresholdedImage(image,step);
    }

    public BufferedImage getEntropySelectionImage(BufferedImage image)
    {
        int[] bits = new int[256];
        float size = (float) image.getHeight() *image.getWidth();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int bit = getGrayScale(image.getRGB(x, y));
                bits[bit]++;
            }
        }
        float[] percentage = new float[256];

        for(int i =0 ;i<256;i++)
        {
            percentage[i] = ((float)bits[i]/size)*100;

        }
        float entropy =0 ;

        for (int f =1 ;f< percentage.length; f++){
            float log = (float) Math.log(percentage[f]);
            if(percentage[f]==0) log =0;
            entropy+=percentage[f]*log;
        }

        return getManualyThresholdedImage(image,(int)entropy*-1);
    }
}
