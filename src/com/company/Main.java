package com.company;

import edge.ConvertEdge;
import image.Color;
import image.Image;
import image.ImageReader;
import image.ImageWriter;

import java.io.IOException;

//TODO figure out why the writer needs an extra row of pixels.
//TODO figure out why there are two solid black lines on each side, right and left.
public class Main {

    public static void main(String[] args) throws Exception {


        String inFile = "resources/images/colesmall.bmp";

        //Test the image Reader
        Image image = testImage(inFile);

        //displayImage(image);
        //Test edge converting
        ConvertEdge convertEdge = new ConvertEdge(image);
        convertEdge.convert(20);
        //displayImage(convertEdge.getImage());

        //Test image writer
        ImageWriter imageWriter = new ImageWriter(convertEdge.getImage());
        //ImageWriter imageWriter = new ImageWriter(image);

        try {
            imageWriter.writeImage("resources/images/out.bmp", inFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void displayImage(Image image) {
        for (int i = 0; i < image.getYSize(); i++) {
            for (int j = 0; j < image.getXSize(); j++) {
                Color color;
                try {
                    color = image.getColorPoint(j, i);
                    if (color.getR() == 0 && color.getG() == 0 && color.getB() == 0) {
                        System.out.print("@ ");
                    }
                    else if (color.getR() == 255 && color.getG() == 255 && color.getB() == 255) {
                        System.out.print("- ");
                    }
                    else if (color.getR() == 237 && color.getG() == 28 && color.getB() == 36) {
                        System.out.print("R ");
                    }
                    else if (color.getR() == 63 && color.getG() == 72 && color.getB() == 204) {
                        System.out.print("B ");
                    }
                    else if (color.getR() == 34 && color.getG() == 177 && color.getB() == 76) {
                        System.out.print("G ");
                    }
                    else {
                        System.out.print("? ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.print("\n");
        }
    }

    private static Image testImage(String inFile) {
        //Test image read and store
        ImageReader imageReader = new ImageReader(inFile);
        Image image = imageReader.getImage();
        return image;
    }
}
