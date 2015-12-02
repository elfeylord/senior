package com.company;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import files.ReadFaces;
import files.WriteFaces;
import image.Image;
import image.ImageReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;



public class Main {

    /******
     * This function is the basic prompt
     */
    public static void prompt(){
        System.out.println("Welcome to Cole's face reader");
        System.out.println("Would you like to:");
        System.out.println("1: Store a new face?");
        System.out.println("2: Recognize a face?");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if (input.equals("1")) {
            storeFace();
        } else if (input.equals("2")) {
            recognizeFace();
        } else {
            System.out.println("Sorry that is not a valid input.");
        }
    }



    public static void recognizeFace() {
        System.out.println("What is the file path to the face?");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();

        HashMap<String, ArrayList<Float>> values = new HashMap();
        values = ReadFaces.getInstance().readImageValues("resources/files/imagesValues.data");

        HashMap<String, ArrayList<Long>> images = ReadFaces.getInstance().readFaces("resources/files/faces.data");


        ArrayList<Long> meanImage = getMeanMatrix(images);


        ArrayList<Matrix> eigenFaces = new ArrayList();
        eigenFaces = ReadFaces.getInstance().readEigenFaces("resources/files/eigenFaces.data");

        ArrayList<Long> newFace = null;
        try {
            newFace = loadFace(path);
        } catch (Throwable throwable) {
            return;
        }

        HashMap<String, ArrayList<Long>> singleValue = new HashMap();
        singleValue.put("temp123456", newFace);

        singleValue = getMeanImages(singleValue, meanImage);

        newFace = singleValue.get("temp123456");

        double newFaceMatrixArray[][] = new double[newFace.size()][1];

        int counter = 0;
        for (Long value : newFace) {
            newFaceMatrixArray[counter][0] = (double)value;
            counter++;
        }
        Matrix newFaceMatrix = new Matrix(newFaceMatrixArray);

        ArrayList<Float> newFaceValues = new ArrayList();

        for (Matrix eigenFace : eigenFaces) {
            newFaceValues.add((float)(eigenFace.transpose().times(newFaceMatrix).getArray())[0][0]);
        }

        compareFaces(newFaceValues, values);


        System.out.println("All done. Have a nice day.");

    }


    public static void compareFaces(ArrayList<Float> newFaceValues, HashMap<String, ArrayList<Float>> values) {
        String winningFace = "none";
        float totalForWinningFace = (float)999999999;
        Set<String> keys = values.keySet();
        for (String key : keys) {
            float tempValue = 0;
            int i = 0;
            ArrayList<Float> tempValues = values.get(key);
            for (float value : tempValues) {
                tempValue += Math.abs(value - newFaceValues.get(i));
                i++;
            }
            if (tempValue < totalForWinningFace) {
                totalForWinningFace = tempValue;
                winningFace = key;
            }
        }
        System.out.println("The winner is: " + winningFace);
    }

    public static void main(String[] args) throws Exception {

        prompt();

    }

    private static double[][] loadFaces(int MN, int p, int n){
        String inFiles[] = new String[p];
        String inFile = "resources/images/orl_faces/s";
        for (int i = 0; i < p; i++) {
            inFiles[i] = inFile + (i+1) + "/1.bmp";
        }
        String testFile = "resources/images/orl_faces/s9/4.bmp";

        //first column length, second row length
        double[][] myArray = new double[MN][p*n];

        Image image;
        double tempArray[];
        for (int i = 0; i < p*n; i++) {
            image = testImage(inFiles[i]);
            tempArray = image.getGrayArray();
            tempArray = image.resizeImage(tempArray, 512, 512, image.getXSize(), image.getYSize());
            for (int j = 0; j < MN; j++) {
                myArray[j][i] = tempArray[j];
            }
        }
        return myArray;
    }

    private static ArrayList<Long> loadFace(String testFile) throws Throwable {


        Image image;
        double tempArray[];

        image = testImage(testFile);


        boolean throwProblem = false;

        if (image.getYSize() != image.getXSize()) {
            System.out.println("The image was not processed. Please provide an image that is the same width as it is height.");
            throwProblem = true;
        }
        if (image.getXSize() < 512) {
            System.out.println("The image was not processed. It is too small. Please provide an image that is at least 512 pixels.");
            throwProblem = true;
        }
        if(image.getXSize() > 1024) {
           System.out.println("The image was not processed. It is too large. Please provide an image that is at most 1024 pixels.");
            throwProblem = true;
        }

        if (throwProblem) {
            throw new Throwable("Error");
        }


        //first column length, second row length
        int MN = image.getXSize()*image.getYSize();
        tempArray = image.getGrayArray();
        tempArray = image.resizeImage(tempArray, 512, 512, image.getXSize(), image.getYSize());
        MN = 512 * 512;


        ArrayList<Long> myArray = new ArrayList();

        for (int j = 0; j < MN; j++) {
            myArray.add((long)tempArray[j]);
        }

        return myArray;
    }

    private static Image testImage(String inFile) {
        //Test image read and store
        ImageReader imageReader = new ImageReader(inFile);
        Image image = imageReader.getImage();
        return image;
    }

    /***
     * This function will
     * 1. Ask for the face and file
     * 2. Read in all faces (the new one and old ones)
     * 3. Process the faces. (which should save to three files)
     */
    public static void storeFace() {
        System.out.println("What is the name of your face to store?");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();

        System.out.println("What is the file path to the face?");
        String filePath = scanner.next();
        HashMap<String, ArrayList<Long>> images = ReadFaces.getInstance().readFaces("resources/files/faces.data");

        if (images.containsKey(name)) {
            System.out.println("The name '" + name + "' is already taken.");
            return;
        }

        ArrayList<Long> tempArray = null;
        try {
            tempArray = loadFace(filePath);
        } catch (Throwable throwable) {
            return;
        }

        images.put(name, tempArray);

        processEigenVectors(images);

    }


    /***
     * This will store all the three files it needs to
     * @param images
     */
    public static void processEigenVectors(HashMap<String, ArrayList<Long>> images) {
        Set<String> keys = images.keySet();
        int np = keys.size();
        int MN = images.get(keys.iterator().next()).size();

        ArrayList<Long> meanImage = getMeanMatrix(images);

        HashMap<String, ArrayList<Long>> newMeanImages = getMeanImages(images, meanImage);

        Matrix A = getA(newMeanImages);

        ArrayList<String> names = new ArrayList();
        for (String key : keys) {
            names.add(key);
        }

        Matrix facesToWrite = getA(images);
        WriteFaces.getInstance().writeFaces(facesToWrite, names, "resources/files/faces.data");


        Matrix At = A.transpose();
        Matrix C = At.times(A);

        EigenvalueDecomposition eigen = new EigenvalueDecomposition(C);
        Matrix vectorC = eigen.getV();

        Matrix vectorL = A.times(vectorC);

        ArrayList<Matrix> faces = new ArrayList();
        for (int i = 0; i < np; i++) {
            faces.add(vectorL.getMatrix(0, MN - 1, i, i));
        }

        WriteFaces.getInstance().writeEigenFaces(faces, "resources/files/eigenFaces.data");

        ArrayList<Matrix> loadedImages = new ArrayList();
        for (int i = 0; i < np; i++) {
            loadedImages.add(A.getMatrix(0, MN - 1, i, i));
        }

        HashMap<String, ArrayList<Float>> values = new HashMap();
        int iterator = 0;
        for (Matrix loaded : loadedImages) {
            ArrayList<Float> tempArray = new ArrayList();
            for (Matrix face : faces) {
                Matrix theValue = loaded.transpose().times(face);
                double tempValue = (theValue.getArray())[0][0];
                tempArray.add((float) tempValue);
            }
            values.put(names.get(iterator), tempArray);
            iterator++;
        }
        WriteFaces.getInstance().writeImage(values, "resources/files/imagesValues.data");

        System.out.println("Image stored");

    }

    public static HashMap<String, ArrayList<Long>> getMeanImages(HashMap<String, ArrayList<Long>> images, ArrayList<Long> meanImage) {
        HashMap<String, ArrayList<Long>> newMeanImages = new HashMap();

        Set<String> imagesKeys = images.keySet();
        for (String key : imagesKeys) {
            ArrayList<Long> addList = new ArrayList();
            int counter1 = 0;
            for (Long value : images.get(key)) {
                addList.add(value - meanImage.get(counter1));
                counter1++;
            }
            newMeanImages.put(key, addList);
        }

        return newMeanImages;
    }

    public static ArrayList<Long> getMeanMatrix(HashMap<String, ArrayList<Long>> lists) {
        ArrayList<Long> tempArrayList = new ArrayList();

        Set<String> keys = lists.keySet();
        for (String key : keys) {
            ArrayList<Long> face = lists.get(key);
            ArrayList<Long> temp = tempArrayList;
            tempArrayList = new ArrayList();
            if (temp.size() == 0) {
                for (Long realTemp : face) {
                    temp.add((long)0);
                }
            }
            for (int i = 0; i < face.size(); i++) {
                tempArrayList.add(face.get(i) + temp.get(i));
            }
        }

        ArrayList<Long> sendList = new ArrayList();
        for (Long value : tempArrayList) {
            sendList.add(value/lists.size());
        }

        return sendList;
    }

    public static HashMap<String, Matrix> getEachMatrix(HashMap<String, ArrayList<Long>> lists) {
        HashMap<String, Matrix> sendMap = new HashMap();

        Set<String> keys = lists.keySet();
        for (String key : keys) {
            ArrayList<Long> tempArray = lists.get(key);
            double face[][] = new double[tempArray.size()][1];
            int i = 0;
            for (long value : tempArray) {
                face[i][0] = value;
                i++;
            }
            sendMap.put(key, new Matrix(face));
        }

        return sendMap;
    }



    public static Matrix getA(HashMap<String, ArrayList<Long>> images) {
        Set<String> keys = images.keySet();
        int np = keys.size();
        int MN = images.get(keys.iterator().next()).size();

        double[][] myArray = new double[MN][np];

        int i = 0;
        for (String key : keys) {
            ArrayList<Long> tempList = images.get(key);
            int j = 0;
            for (long value : tempList) {
                myArray[j][i] = value;
                j++;
            }
            i++;
        }
        Matrix A = new Matrix(myArray);
        return A;
    }
}
