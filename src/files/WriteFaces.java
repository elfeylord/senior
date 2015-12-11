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
                    bufferedWriter.write("" + String.format("%.12f", value));
                    bufferedWriter.newLine();
                }
            }
            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '" + fileName + "'");
            System.exit(1);
        }
    }

    public void writeFaces(Matrix A, ArrayList<String> names, String fileName) {
        HashMap<String, ArrayList<Long>> images = new HashMap();

        int MN = A.getRowDimension();



        ArrayList<Matrix> matrixes = new ArrayList();

        for (int i = 0; i < A.getColumnDimension(); i++) {
            matrixes.add(A.getMatrix(0, (MN) - 1, i, i));
        }




        int size = MN;

        int i = 0;
        for (Matrix matrix : matrixes) {
            ArrayList<Long> putArray  = new ArrayList();
            //This is where the writing goes
            double array[][] = matrix.getArray();
            for (int j = 0; j < size; j++) {
                putArray.add((long)(array[j][0]));

            }
            images.put(names.get(i), putArray);
            i++;
        }

        writeFaces(images, fileName);
    }

    /***
     *
     * Number of images
     * Length of images
     * name
     * data
     *
     *
     * @param images
     * @param fileName
     */
    public void writeFaces(HashMap<String, ArrayList<Long>> images, String fileName){


        try {
            FileWriter fileWriter = new FileWriter(fileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Set<String> keys = images.keySet();
            int size = keys.size();
            bufferedWriter.write("" + size);
            bufferedWriter.newLine();

            boolean lengthWritten = false;

            for (String key : keys) {

                ArrayList<Long> tempArray = images.get(key);

                if (lengthWritten == false) {
                    bufferedWriter.write("" + tempArray.size());
                    bufferedWriter.newLine();
                    lengthWritten = true;
                }
                bufferedWriter.write(key);
                bufferedWriter.newLine();

                for (long pixel : tempArray) {
                    bufferedWriter.write("" + (pixel == 0 ? "0" : pixel));
                    bufferedWriter.newLine();
                }
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '" + fileName + "'");
            System.exit(1);
        }
    }

    //These all end up calling the same one.
    public void writeEigenFaces(Matrix matrix, String fileName, int np) {
        //WRONG, all of it
        int MN = matrix.getColumnDimension();

        Matrix tempMatrixArray[] = new Matrix[np];

        int count = (np) - 1;
        for (int i = 0; i < np; i++) {
            int value = count - i;
            tempMatrixArray[i] = matrix.getMatrix(0, (np) - 1, value, value);
        }
        writeEigenFaces(tempMatrixArray, fileName, np);
    }

    public void writeEigenFaces(Matrix matrixes[], String fileName, int np) {
        ArrayList<Matrix> sendMatrixes = new ArrayList();

        for (int i = 0; i < np; i++){
            sendMatrixes.add(matrixes[i]);
        }
        writeEigenFaces(sendMatrixes, fileName);

    }

    public void writeEigenFaces(ArrayList<Matrix> matrixes, String fileName){

        try {
            FileWriter fileWriter = new FileWriter(fileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int numberOfFaces = matrixes.size();
            bufferedWriter.write("" + numberOfFaces);
            bufferedWriter.newLine();
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
            System.exit(1);
        }
    }
}
