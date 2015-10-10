package com.company;

import image.Color;
import image.Image;
import image.ImageReader;

public class Main {

    public static void main(String[] args) {

        testImage();

    }

    private static void testImage() {
        //Test image read and store
        ImageReader imageReader = new ImageReader("resources/images/4blob2.bmp");
        Image image = imageReader.getImage();

        for (int i = 0; i < image.getCols(); i++) {
            for (int j = 0; j < image.getRows(); j++) {
                Color color;
                try {
                    color = image.getColorPoint(j, i);
                    if (color.getR() == 0 && color.getG() == 0 && color.getB() == 0) {
                        System.out.print("Black ");
                    }
                    else if (color.getR() == 255 && color.getG() == 255 && color.getB() == 255) {
                        System.out.print("White ");
                    }
                    else if (color.getR() == 237 && color.getG() == 28 && color.getB() == 36) {
                        System.out.print("~Red~ ");
                    }
                    else if (color.getR() == 63 && color.getG() == 72 && color.getB() == 204) {
                        System.out.print("~Blue ");
                    }
                    else if (color.getR() == 34 && color.getG() == 177 && color.getB() == 76) {
                        System.out.print("Green ");
                    }
                    else {
                        System.out.print("Dunno ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.print("\n");
        }
    }
}
