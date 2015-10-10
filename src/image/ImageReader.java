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
        ArrayList<Integer> buffer = getBuffer(fileName);

        int xSize = buffer.get(18); //the cols size, or width
        int ySize = buffer.get(22); //the rows size, or height

        image = new Image(xSize, ySize);

        int numberOfColorsUsed = buffer.get(46);
        int start = 54 + 4 * (numberOfColorsUsed);


        int numberOfColorsSpace = 3;
        //this is so that the image is recorded properly. .BMP files start bottom left.
        int bottom  = ySize;
        for (int i = 0; i < ySize; i++) {
            bottom--;
            for (int j = 0; j < xSize; j++) {
                Color color;
                int r[] = new int[numberOfColorsSpace];
                for (int k = 0; k < numberOfColorsSpace; k++) {
                    int number = start + (i * xSize * numberOfColorsSpace) + (j * numberOfColorsSpace) + k;
                    r[k] = buffer.get(number);
                }
                color = new Color(r[0], r[1], r[2]);
                image.setColorPoint(j, bottom, color);
            }
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
}
