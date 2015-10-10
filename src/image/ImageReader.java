package image;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by elfeylord on 10/9/15.
 */
public class ImageReader {

    public ImageReader(String fileName) {
        readImage(fileName);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private Image image;

    private void readImage(String fileName)
    {
        FileInputStream in;

        ArrayList<Integer> buffer = new ArrayList<Integer>();

        try {
            in = new FileInputStream(fileName);
            int c;

            while ((c = in.read()) != -1) {
                buffer.add(c);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //int has 4 bytes in it
        char byteArray[] = new char[buffer.size() * 4];
        int byteArraySize = buffer.size() * 4;

        for (int i = 0; i < buffer.size(); i++) {

            for (int j = 0; j < 4; j++) {

                int integer = buffer.get(i);
                //3 - j becuase we need to reverse the order of the bytes.
                byteArray[i * 4 + (3 - j)] = (char)(integer >>> (j * 8));
            }
        }



        int xSize = buffer.get(18); //the cols size, or width
        int ySize = buffer.get(22); //the rows size, or height

        int bitsPerPixel = buffer.get(28);
        int bytePerPixel = bitsPerPixel / 8;

        image = new Image(xSize, ySize);

        int numberOfColorsUsed = buffer.get(46);
        int start = 54 + 4 * (numberOfColorsUsed);


        for (int i = 0; i < buffer.size(); i++)
        {
            System.out.println(i + ": " + buffer.get(i));
        }


        //START TEST convertsBytesToInt
        char num[] = new char[3];

        char num1 = (char)178; // 1011 0010
        char num2 = (char)179; // 1011 0011
        char num3 = (char)255; // 1111 1111

        num[0] = num1;
        num[1] = num2;
        num[2] = num3;
        int results = convertBytesToInt(num, 3); //should be 11711487 which is 10110010 10110011 11111111

        //END TEST


        /* new stuff that wasn't right
        int bottomPlace = ySize;
        for (int i = 0; i < ySize; i++) {
            bottomPlace--;
            for (int j = 0; j < xSize; j++) {
                int raster;
                char bytes[] = new char[bytePerPixel];
                for (int k = 0; k < bytePerPixel; k++) {
                    int number = (start * bytePerPixel) + (i * xSize * bytePerPixel) + (j * bytePerPixel) + k;
                    bytes[k] = byteArray[number];
                }
                raster = convertBytesToInt(bytes, bytePerPixel);
                image.setDataPoint(j, bottomPlace, raster);
            }
        }
        */

        // old stuff
        //this is so that the image is recorded properly. .BMP files start bottom left.
        numberOfColorsUsed = 3;
        int bottom  = ySize;
        for (int i = 0; i < ySize; i++) {
            bottom--;
            for (int j = 0; j < xSize; j++) {
                Color color;
                int r[] = new int[numberOfColorsUsed];
                for (int k = 0; k < numberOfColorsUsed; k++) {
                    int number = start + (i * xSize * numberOfColorsUsed) + (j * numberOfColorsUsed) + k;
                    r[k] = buffer.get(number);
                }
                color = new Color(r[0], r[1], r[2]);
                image.setColorPoint(j, bottom, color);
            }
        }

    }

    private int convertBytesToInt(char num [], int bytesPerPixel) {
        int returnInt = 0;

        for (int i = 0; i < bytesPerPixel; i++) {
            returnInt += ((int)num[i] << 8 * ((bytesPerPixel - 1) - i));
        }
        return returnInt;
    }
}
