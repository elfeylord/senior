package com.company;

import edge.ConvertEdge;
import image.Color;
import image.Image;
import image.ImageReader;

public class Main {

    public static void main(String[] args) {


        Image image = testImage();

        ConvertEdge convertEdge = new ConvertEdge(image);
        convertEdge.convert(20);
        displayImage(convertEdge.getImage());

    }

    private static void displayImage(Image image) {
        for (int i = 0; i < image.getColsSize(); i++) {
            for (int j = 0; j < image.getRowsSize(); j++) {
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

    private static Image testImage() {
        //Test image read and store
        ImageReader imageReader = new ImageReader("resources/images/32onlyredblob.bmp");
        Image image = imageReader.getImage();
        displayImage(image);
        return image;
    }
}
