package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HistogramService {

    private  int  rMinIndex;
    private int  gMinIndex;
    private int  bMinIndex;
    private int  rMaxIndex;
    private int  gMaxIndex;
    private  int  bMaxIndex;

    private int[] redBits = new int[256];
    private int[] greenBits = new int[256];
    private int[] blueBits = new int[256];
    public BufferedImage getExtendedHistogram(BufferedImage imageJPG) {


        setBitsPropability(imageJPG);
        rMinIndex=getMinIndex(redBits);
          gMinIndex=getMinIndex(greenBits);
          bMinIndex=getMinIndex(blueBits);
         rMaxIndex=getMaxIndex(redBits);
         gMaxIndex=getMaxIndex(greenBits);
          bMaxIndex=getMaxIndex(blueBits);
for(int i =0;i<256;i++)
{
    redBits[i]=0;
    greenBits[i]=0;
    blueBits[i]=0;
}

        return  extendBitArray(imageJPG);
    }

    private BufferedImage extendBitArray(BufferedImage imageJPG) {
        BufferedImage newImage = new BufferedImage(
                imageJPG.getWidth(),imageJPG.getHeight(),BufferedImage.TYPE_INT_BGR);
        for (int y =0; y< imageJPG.getHeight();y++)
        {
            for (int x=0;x< imageJPG.getWidth();x++)
            {
                Color color = new Color(imageJPG.getRGB(x,y));
                float r =((float)(color.getRed()-rMinIndex)/(rMaxIndex-rMinIndex))*255;
                float g =((float)(color.getGreen()-gMinIndex)/(gMaxIndex-gMinIndex))*255;
                float b =((float)(color.getBlue()-bMinIndex)/(bMaxIndex-bMinIndex))*255;

                int c =((int)r << 16) | ((int)g << 8) |(int)b;
                newImage.setRGB(x,y,c);
            }
        }
        return newImage;
    }

    private int getMaxIndex(int[] redBits) {
        for(int i=255;i>0;i--)
        {
            if(redBits[i]!=0) return i;
        }
        return 0;
    }

    private int getMinIndex(int[] redBits) {
        for(int i=0;i<256;i++)
        {
            if(redBits[i]!=0) return i;
        }
        return 0;
    }

    public BufferedImage getEqualizedHistogram(BufferedImage imageJPG) {

        float[] propabilitiesRed = new float[256];
        float[] propabilitiesGreen = new float[256];
        float[] propabilitiesBlue = new float[256];
        float[] cumulateRed = new float[256];
        float[] cumulateGreen = new float[256];
        float[] cumulateBlue = new float[256];

        long size = (long) imageJPG.getHeight() *imageJPG.getWidth();

        setBitsPropability(imageJPG);
        float sumR=0;
        float sumG=0;
        float sumB=0;
        for(int i =0;i<256;i++)
        {
            propabilitiesRed[i]=(float)redBits[i]/size;
            sumR+=propabilitiesRed[i];
            cumulateRed[i]=sumR*255;
            propabilitiesGreen[i]=(float)greenBits[i]/size;
            sumG+=propabilitiesGreen[i];
            cumulateGreen[i]=sumG*255;
            propabilitiesBlue[i]=(float)blueBits[i]/size;
            sumB+=propabilitiesBlue[i];
            cumulateBlue[i]=sumB*255;
        }
        BufferedImage newImage = new BufferedImage(
                imageJPG.getWidth(),imageJPG.getHeight(),BufferedImage.TYPE_INT_BGR);

        for (int y =0; y< imageJPG.getHeight();y++)
        {
            for (int x=0;x< imageJPG.getWidth();x++)
            {
                Color color = new Color(imageJPG.getRGB(x,y));
                int r=(int)cumulateRed[color.getRed()];
                int g=(int)cumulateGreen[color.getGreen()];
                int b=(int)cumulateBlue[color.getBlue()];
                int c =(r << 16) | (g << 8) |b;
                newImage.setRGB(x,y,c);
            }
        }
        return newImage;
    }

    private void setBitsPropability(BufferedImage imageJPG) {
        for (int y = 0; y < imageJPG.getHeight(); y++) {
            for (int x = 0; x < imageJPG.getWidth(); x++) {
                Color color = new Color(imageJPG.getRGB(x, y));
                redBits[color.getRed()]++;
                greenBits[color.getGreen()]++;
                blueBits[color.getBlue()]++;
            }
        }
    }
}
