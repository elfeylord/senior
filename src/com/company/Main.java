package com.company;

import detection.Blob;
import detection.BlobProcessing;
import edge.ConvertEdge;
import image.Color;
import image.Image;
import image.ImageReader;
import image.ImageWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO figure out why the writer needs an extra row of pixels.
//TODO figure out why there are two solid black lines on each side, right and left.
public class Main {

    public static void main(String[] args) throws Exception {


        String inFile = "resources/images/colesmall.bmp";

        //Test the image Reader
        Image image = testImage(inFile);

        //Test edge converting
        ConvertEdge convertEdge = new ConvertEdge(image);
        convertEdge.convert(20);

        BlobProcessing myBlobProcessor = new BlobProcessing(convertEdge.getImage());

        //remove some blobs
        List<Blob> myBlobs = new ArrayList<Blob>();
        for (int i = 0; i < myBlobProcessor.getBlobs().size(); i++) {
            if (myBlobs.size() < 10) {
                myBlobs.add(myBlobProcessor.getBlobs().get(i));
            } else {
                boolean replaceOne = false;
                for (Blob blob : myBlobs) {
                    if (myBlobProcessor.getBlobs().get(i).getNumPoints() > blob.getNumPoints()) {
                        replaceOne = true;
                    }
                }
                if (replaceOne) {
                    int smallestBlob = 0;
                    for (int j = 0; j < myBlobs.size(); j++) {
                        if (myBlobs.get(smallestBlob).getNumPoints() > myBlobs.get(j).getNumPoints()) {
                            smallestBlob = j;
                        }
                    }
                    myBlobs.remove(smallestBlob);
                    myBlobs.add(myBlobProcessor.getBlobs().get(i));
                }
            }
        }

        //write all of the blobs to a folder.
        ImageWriter imageWriter;
        for (int i = 0; i < myBlobs.size(); i++) {
            imageWriter = new ImageWriter(myBlobs.get(i));
            try {
                imageWriter.writeImage("resources/images/blobs/out" + i + ".bmp", inFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //Test image writer
        ImageWriter otherimageWriter = new ImageWriter(convertEdge.getImage());

        try {
            otherimageWriter.writeImage("resources/images/out.bmp", inFile);
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
