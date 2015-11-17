package com.company;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import detection.Blob;
import detection.BlobProcessing;
import edge.ConvertEdge;
import image.Color;
import image.Image;
import image.ImageReader;
import image.ImageWriter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.*;


public class Main {

    public static void main(String[] args) throws Exception {

        double test1[][] = new double[2][3];//
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++)
                test1[i][j] = 9;
        }
        double test2[][] = new double[3][1];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++)
                test1[i][j] = 9;
        }
        Matrix testM1 = new Matrix(test1);
        Matrix testM2 = new Matrix(test2);
        testM1.times(testM2);

        //ANSWER [2][3] IS
        //2 DOWN
        //3 RIGHT

        //| 9 9 9 |
        //| 9 9 9 |

        /****
         * This is the start of the magic. We will process all of them
         */
        System.out.println("This is a test of all the faces.");
        System.out.println("Oh, and there is a much better way of actually doing this....");
        for (int WHATEVER = 1; WHATEVER < 11; WHATEVER++) {


            System.out.println("\n\n\nWe want face number " + (WHATEVER - 1) + " " +
                    "to win.");




            //Eigen vectors
            int MN = 92 * 112;
            int p = 10;
            int n = 1;


            double testFace[][] = loadFace(WHATEVER, MN, 1, 1);


            //STEPS FROM VIDEO https://www.youtube.com/watch?v=LYgBqJorF44&feature=plcp
            //1. load faces.mat
            double myArray[][] = loadFaces(MN, p, n);
            Matrix A = new Matrix(myArray);
            Matrix At = A.transpose();


            //2. C=A'*A
            Matrix C = At.times(A);


            //3. [vectorC,valueC]=eig(C);
            //4. ss=diag(valueC);
            //5. [ss,iii]=sort(-ss);
            //6. vectorC=vectorC(:,iii);
            EigenvalueDecomposition eigen = new EigenvalueDecomposition(C);
            Matrix vectorC = eigen.getV();
            double valueC[] = eigen.getRealEigenvalues();
            double ss[] = new double[n * p];

            Matrix tempMatrixArray[] = new Matrix[n * p];

            int count = (n * p) - 1;
            for (int i = 0; i < n * p; i++) {
                int value = count - i;
                ss[i] = valueC[value];
                tempMatrixArray[i] = vectorC.getMatrix(0, (n * p) - 1, value, value);
            }

            double rejoinMatrixArray[][] = new double[n * p][p * n];

            for (int j = 0; j < n * p; j++) {
                Matrix tempPutMatrix = tempMatrixArray[j];
                double[][] tempPutArray = tempPutMatrix.getArray();
                for (int i = 0; i < n * p; i++) {
                    rejoinMatrixArray[i][j] = tempPutArray[i][0];
                }
            }
            Matrix rejoinMatrix = new Matrix(rejoinMatrixArray);


            //7. vectorL=A*vectorC(:,1:5);

            vectorC = rejoinMatrix.getMatrix(0, (n * p) - 1, 0, 4);

            Matrix vectorL = A.times(rejoinMatrix);


            ArrayList<Matrix> faces = new ArrayList();
            for (int i = 0; i < n * p; i++) {
                faces.add(vectorL.getMatrix(0, MN - 1, i, i));
            }

            ArrayList<Matrix> loadedImages = new ArrayList();
            for (int i = 0; i < n * p; i++) {
                loadedImages.add(A.getMatrix(0, MN - 1, i, i));
            }

            ArrayList<ArrayList<Float>> values = new ArrayList();
            for (Matrix loaded : loadedImages) {
                ArrayList<Float> tempArray = new ArrayList();
                for (Matrix face : faces) {
                    Matrix theValue = loaded.times(face.transpose());
                    double tempValue = (theValue.getArray())[0][0];
                    tempArray.add((float) tempValue);
                }
                values.add(tempArray);
            }


            Matrix testFaceMatrix = new Matrix(testFace);
            ArrayList<Float> testFaceValues = new ArrayList();

            for (Matrix face : faces) {
                testFaceValues.add((float) ((testFaceMatrix.times(face.transpose()).getArray())[0][0]));
            }

            ArrayList<Float> resultValues = new ArrayList();

            double tempValue;
            for (ArrayList<Float> tempValues : values) {

                tempValue = 0;
                for (int i = 0; i < tempValues.size(); i++) {
                    tempValue += Math.abs(tempValues.get(i) - testFaceValues.get(i));
                }
                resultValues.add((float) tempValue);
            }

            for (int i = 0; i < resultValues.size(); i++) {
                System.out.println("Image " + i + ": " + resultValues.get(i));
            }


            System.out.println("WHAT!!");
        }






        /******************/
        //lets try to display these images


        /***
         * This is to display the fun stuff.
         */
        /*ArrayList<Image> images = new ArrayList();
        for (int i = 0; i < n*p; i++) {
            double putter[][] = (vectorL.getMatrix(0, MN - 1, i, i)).getArray();
            double put[] = new double[MN];
            for (int j = 0; j < MN; j++) {
                put[j] = putter[j][0];
            }
            images.add(new Image(put, 92, 112));
        }*/

        /*********************/



        System.out.println("Be kinder");


        //8. Coeff=A'*vectorL; TODO this really isn't the coeff. I need to do this for each of the images to train the just like I would do
        //TODO with the actual picture that I would be comapring.
        //Matrix Coeff = At.times(vectorL);

        System.out.println("I love her, treat her like I do.");

        //9. for i=1:30
        //10         model(i,:)=mean(coeff((5*(i-1)+1):5*i,:));

        /***Matrix people[] = new Matrix[p];
        for (int i = 0; i < n*p; i++) {

            //Here you can do the mean, but we dont have the mean.
            people[i] = Coeff.getMatrix(0,MN - 1,i,i);
        }***/


        //http://www.pages.drexel.edu/~sis26/Eigenface%20Tutorial.htm
        /*ImageWriter imageWriter;
        for (int i = 0; i < images.size(); i++) {
            imageWriter = new ImageWriter(images.get(i));
            try {
                imageWriter.writeImage("resources/images/blobs/out" + i + ".bmp", "resources/images/orl_faces/s1/8.bmp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        //DONE WITH TRAINING
        System.out.println("we are about to start over again baby");


        //~~~~~~~~~~@@@~~~~~~~~~~~~~~~~~~~~~~//
        //~~~~~~~~ START OF THE RECOGNIZING~~//


        //while (1)
            //imagename=input(‘Enter the filename of the image to Recognize(0 stop):’);
            //if (imagename <1)
            //break;
            //end;
            //imageco=A(:,imagename)’*vectorL;
            //disp (‘’);
            //disp (‘The coefficients for this image are:’);
            //mess1=sprintf(‘%.2f %.2f %.2f %.2f %.2f’,
            //imageco(1),imageco(2),imageco(3),imageco(4),
            //        imageco(5));
            //disp(mess1);
            //top=1;
            //for I=2:30
            //if (norm(model(i,:)-imageco,1)<norm(model
            //        (top, : )-imageco,1))
            //top=i
            //end
            //        end
            //mess1=sprintf(‘The image input was a image of person
            //        number %d’,top);
            //disp(mess1);
            //end






/*
//TESTS FOR THE EIGEN VALUES,
//SOLVE DOES NOT WORK BECAUSE IT WILL ALWAYS RETURN 0, BECAUSE IT CAN.

        //eigen value test to make sure for sanity!!!
        double eigenTestArray[][] =
                {
                        {-4, 0, 1234},
                        {1234, 234, 0},
                        {0, 4, 2134}
                };

        Matrix testMatrix = new Matrix(eigenTestArray);
        EigenvalueDecomposition eigenTest = new  EigenvalueDecomposition(testMatrix);
        Matrix testVectors = eigenTest.getV();
        Matrix testValuesVector = eigenTest.getD();
        double testValues[] = eigenTest.getRealEigenvalues();

       // Matrix identity = Matrix.identity(3,3);

        double testValue[][] = {{(testValues[0]), 0, 0}, {0, (testValues[0]), 0}, {0, 0, (testValues[0])}};
        Matrix testValueMatrix = new Matrix(testValue);

        double zeroArray[][] =
                {{0}, {0}, {0}};
        Matrix zeroMatrix = new Matrix(zeroArray);


        Matrix leftSideResult = testMatrix.minus(testValueMatrix);
        Matrix eigenVectorTwo = leftSideResult.solve(zeroMatrix);


        double test1[][] = {{2,3,4},{1,0,3},{3,1,0}};
        double test2[][] = {{4},{3},{2}};
        double test3[][] = {{25},{10},{15}};

        Matrix matrix1 = new Matrix(test1);
        Matrix matrix2 = new Matrix(test2);
        Matrix matrix3 = new Matrix(test3);

        Matrix results = matrix1.solve(matrix3);

        */




        System.out.println("This is a test, we want to treat it with total respect.");




/*
        //test for eigen values

        int matrixSize = 276;
        double testArray[][] = new double[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                int arrayIndex = (j + matrixSize * i) % 92 * 112;
                testArray[i][j] = tempArray[arrayIndex];
            }
        }

        Matrix testMatrix = new Matrix(testArray);

        Date date1 = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        EigenvalueDecomposition eigen = new  EigenvalueDecomposition(testMatrix);

        Date date2 = new Date();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);


        System.out.println("To create Eigen: " + (calendar2.getTimeInMillis() - calendar1.getTimeInMillis()));

        double values[] = eigen.getRealEigenvalues();

        Date date3 = new Date();
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(date3);

        System.out.println("To get Eigen Values: " + (calendar3.getTimeInMillis() - calendar2.getTimeInMillis()));
*/

/*
        String inFile = "resources/images/orl_faces/s1/8.bmp";

        //Test the image Reader
        Image image = testImage(inFile);

        //Test edge converting
        ConvertEdge convertEdge = new ConvertEdge(image);
        convertEdge.convert(35);

        BlobProcessing myBlobProcessor = new BlobProcessing(convertEdge.getImage());

        //remove some blobs
        List<Blob> myBlobs = new ArrayList<Blob>();
        for (int i = 0; i < myBlobProcessor.getBlobs().size(); i++) {
            if (myBlobs.size() < 10) {
                myBlobs.add(myBlobProcessor.getBlobs().get(i));
            } else {
                boolean replaceOne = false;
                for (Blob blob : myBlobs) {
                    if (myBlobProcessor.getBlobs().get(i).getNumPoints() > blob.getNumPoints()) {
                        replaceOne = true;
                    }
                }
                if (replaceOne) {
                    int smallestBlob = 0;
                    for (int j = 0; j < myBlobs.size(); j++) {
                        if (myBlobs.get(smallestBlob).getNumPoints() > myBlobs.get(j).getNumPoints()) {
                            smallestBlob = j;
                        }
                    }
                    myBlobs.remove(smallestBlob);
                    myBlobs.add(myBlobProcessor.getBlobs().get(i));
                }
            }
        }

        //write all of the blobs to a folder.
        ImageWriter imageWriter;
        for (int i = 0; i < myBlobs.size(); i++) {
            imageWriter = new ImageWriter(myBlobs.get(i));
            try {
                imageWriter.writeImage("resources/images/blobs/out" + i + ".bmp", inFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //Test image writer
        ImageWriter otherimageWriter = new ImageWriter(convertEdge.getImage());

        try {
            otherimageWriter.writeImage("resources/images/out.bmp", inFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

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
            for (int j = 0; j < MN; j++) {
                myArray[j][i] = tempArray[j];
            }
        }
        return myArray;
    }

    private static double[][] loadFace(int number, int MN, int p, int n){

        String testFile = "resources/images/orl_faces/s" + number + "/4.bmp";

        //first column length, second row length
        double[][] myArray = new double[MN][p*n];

        Image image;
        double tempArray[];
        for (int i = 0; i < p*n; i++) {
            image = testImage(testFile);
            tempArray = image.getGrayArray();
            for (int j = 0; j < MN; j++) {
                myArray[j][i] = tempArray[j];
            }
        }
        return myArray;
    }

    private static void displayImage(Image image) {
        for (int i = 0; i < image.getYSize(); i++) {
            for (int j = 0; j < image.getXSize(); j++) {
                Color color;
                try {
                    color = image.getColorPoint(j, i);
                    if (color.getR() == 0 && color.getG() == 0 && color.getB() == 0) {
                        System.out.print("@ ");
                    }
                    else if (color.getR() == 255 && color.getG() == 255 && color.getB() == 255) {
                        System.out.print("- ");
                    }
                    else if (color.getR() == 237 && color.getG() == 28 && color.getB() == 36) {
                        System.out.print("R ");
                    }
                    else if (color.getR() == 63 && color.getG() == 72 && color.getB() == 204) {
                        System.out.print("B ");
                    }
                    else if (color.getR() == 34 && color.getG() == 177 && color.getB() == 76) {
                        System.out.print("G ");
                    }
                    else {
                        System.out.print("? ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.print("\n");
        }
    }

    private static Image testImage(String inFile) {
        //Test image read and store
        ImageReader imageReader = new ImageReader(inFile);
        Image image = imageReader.getImage();
        return image;
    }
}
