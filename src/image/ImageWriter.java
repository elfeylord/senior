package image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by elfeylord on 10/10/15.
 */
public class ImageWriter {
    private Image image;
    public ImageWriter(Image image) {
        this.image = image;
    }

    public void writeImage(String outFile, String inFile) throws Exception {
        FileInputStream in;
        ArrayList<Integer> buffer = new ArrayList<Integer>();

        try {
            in = new FileInputStream(inFile);
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

        FileOutputStream out = new FileOutputStream(outFile);

        for (int i = 0; i < 54; i++) {
            out.write(buffer.get(i));
        }

        int bottom = image.getYSize();
        for (int i = 0; i < image.getYSize(); i++) {
            bottom--;
            for (int j = 0; j < image.getXSize(); j++) {
                Color color = image.getColorPoint(j, bottom);
                out.write(color.getB());
                out.write(color.getG());
                out.write(color.getR());
            }
            out.write((int)255);
            out.write((int)255);
            out.write((int)255);
        }

        out.close();
    }
}
