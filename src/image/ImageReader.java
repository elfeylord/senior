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

        int xSize = buffer.get(18); //the cols size, or width
        int ySize = buffer.get(22); //the rows size, or height

        image = new Image(xSize, ySize);

        int numberOfColorsUsed = 3;
        int start = 54;


        for (int i = 0; i < buffer.size(); i++)
        {
            System.out.println(i + ": " + buffer.get(i));
        }

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                Color color;
                int r[] = new int[numberOfColorsUsed];
                for (int k = 0; k < numberOfColorsUsed; k++) {
                    int number = start + (i * xSize * numberOfColorsUsed) + (j * numberOfColorsUsed) + k;
                    r[k] = buffer.get(number);
                }
                color = new Color(r[0], r[1], r[2]);
                image.setDataPoint(j, i, color);
            }
        }
        System.out.println("this is taking forever");
    }
}
