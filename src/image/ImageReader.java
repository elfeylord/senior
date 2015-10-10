package image;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by elfeylord on 10/9/15.
 */
public class ImageReader {
    private
    public static void readImage()
    {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("resources/images/cole.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferReader = new BufferedReader(fileReader);
        String string;
        try {
            while ((string = bufferReader.readLine()) != null) {
                System.out.println(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
