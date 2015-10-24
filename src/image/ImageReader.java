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

    private void readImage(String fileName) {
        ArrayList<Integer> buffer = getBuffer(fileName);

        //using bytes will cause a problem
        int xSize = calculateWidthOrHieght(buffer.get(18), buffer.get(19), buffer.get(20), buffer.get(21)); //the cols size, or width
        int ySize = calculateWidthOrHieght(buffer.get(22), buffer.get(23), buffer.get(24), buffer.get(25)); //the rows size, or height

        image = new Image(xSize, ySize);

        int numberOfColorsUsed = buffer.get(46);
        int start = 54 + 4 * (numberOfColorsUsed);


        int numberOfColorsSpace = 3;
        //this is so that the image is recorded properly. .BMP files start bottom left.
        int bottom  = ySize;
        //every end of the x axis has 3 bytes extra
        int waster = 0;
        for (int i = 0; i < ySize; i++) {
            bottom--;
            for (int j = 0; j < xSize; j++) {
                Color color;
                int r[] = new int[numberOfColorsSpace];
                for (int k = 0; k < numberOfColorsSpace; k++) {
                    int number = waster + start + (i * xSize * numberOfColorsSpace) + (j * numberOfColorsSpace) + k;
                    r[k] = buffer.get(number);
                }
                //colors come BGR not RGB
                color = new Color(r[2], r[1], r[0]);
                image.setColorPoint(j, bottom, color);
            }
            //waste 3 bytes just because
            waster += 3;
        }
    }

    private ArrayList<Integer> getBuffer(String fileName){
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
        return buffer;
    }

    private int calculateWidthOrHieght(int num1, int num2, int num3, int num4) {

        int number = 0;

        char byte1 = (char)num1;
        char byte2 = (char)num2;
        char byte3 = (char)num3;
        char byte4 = (char)num4;

        number += byte4;
        number = number << 8;
        number += byte3;
        number = number << 8;
        number += byte2;
        number = number << 8;
        number += byte1;

        return number;
    }
}
