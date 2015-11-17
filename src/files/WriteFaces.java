package files;

import Jama.Matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by elfeylord on 11/16/15.
 */
public class WriteFaces {


    private static WriteFaces instance = null;
    protected WriteFaces() {
        // Exists only to defeat instantiation.
    }
    public static WriteFaces getInstance() {
        if(instance == null) {
            instance = new WriteFaces();
        }
        return instance;
    }



    /***
     *
     * Line 0 - number of images
     * Line 1 - number of values per image
     * Before each value set, the name
     *
     *
     * @param valuesMap
     * @param fileName
     */
    public void writeImage(HashMap<String, ArrayList<Float>> valuesMap, String fileName){

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Set<String> keys = valuesMap.keySet();
            int size = keys.size();

            bufferedWriter.write("" + size);
            bufferedWriter.newLine();

            boolean wroteNumberOfValues = false;

            for (String key : keys) {
                ArrayList<Float> values = valuesMap.get(key);
                if (wroteNumberOfValues == false) {
                    bufferedWriter.write("" + values.size());
                    bufferedWriter.newLine();
                    wroteNumberOfValues = true;
                }
                bufferedWriter.write(key);
                bufferedWriter.newLine();
                for (float value : values) {
                    bufferedWriter.write("" + value);
                    bufferedWriter.newLine();
                }
            }
            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '" + fileName + "'");
        }
    }

    //These all end up calling the same one.
    public void writeFaces(Matrix matrix, String fileName, int np) {

        Matrix tempMatrixArray[] = new Matrix[np];

        int count = (np) - 1;
        for (int i = 0; i < np; i++) {
            int value = count - i;
            tempMatrixArray[i] = matrix.getMatrix(0, (np) - 1, value, value);
        }
        writeFaces(tempMatrixArray, fileName, np);
    }

    public void writeFaces(Matrix matrixes[], String fileName, int np) {
        ArrayList<Matrix> sendMatrixes = new ArrayList();

        for (int i = 0; i < np; i++){
            sendMatrixes.add(matrixes[i]);
        }
        writeFaces(sendMatrixes, fileName);

    }

    public void writeFaces(ArrayList<Matrix> matrixes, String fileName){

        try {
            FileWriter fileWriter = new FileWriter(fileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int size = matrixes.get(0).getRowDimension();
            bufferedWriter.write("" + size);
            bufferedWriter.newLine();
            for (Matrix matrix : matrixes) {

                //This is where the writing goes
                double array[][] = matrix.getArray();
                for (int j = 0; j < size; j++) {
                    bufferedWriter.write("" + (array[j][0] == 0 ? "0" : array[j][0] ));
                    bufferedWriter.newLine();
                }
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '" + fileName + "'");
        }
    }
}
