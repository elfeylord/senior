package files;

import Jama.Matrix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by elfeylord on 11/16/15.
 */
public class ReadFaces {

    private static ReadFaces instance = null;
    protected ReadFaces() {
        // Exists only to defeat instantiation.
    }
    public static ReadFaces getInstance() {
        if(instance == null) {
            instance = new ReadFaces();
        }
        return instance;
    }

    /***
     *
     *
     *
     * Line 0 - number of images
     * Line 1 - number of values per image
     * Before each value set, the name
     *
     *
     *
     *
     *
     * @param fileName
     * @return
     */
    public HashMap<String, ArrayList<Float>> readImages(String fileName) {

        HashMap<String, ArrayList<Float>> sendList = new HashMap();

        try {

            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int line0 = Integer.parseInt(bufferedReader.readLine());
            int line1 = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < line0; i++){
                String name = bufferedReader.readLine();
                ArrayList<Float> putArray = new ArrayList();
                for (int j = 0; j < line1; j++) {
                    putArray.add(Float.parseFloat(bufferedReader.readLine()));
                }
                sendList.put(name, putArray);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return sendList;
    }

}
