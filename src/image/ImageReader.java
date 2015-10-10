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

        for (int i = 0; i < buffer.size(); i++) {
            System.out.println(buffer.get(i));
        }
    }
}
