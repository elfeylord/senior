package image;

import java.util.ArrayList;

/**
 * Created by elfeylord on 10/9/15.
 */
public class Image {

    private int xSize;
    private int ySize;
    private Color[][] color;

    /**
     *
     * @param xSize
     * @param ySize
     */
    public Image(int xSize, int ySize) {
        if (xSize < 1) {
            xSize = 1;
        }
        if (ySize < 1) {
            ySize = 1;
        }
        this.xSize = xSize;
        this.ySize = ySize;
        this.color = new Color[xSize][ySize];
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                color[j][i] = new Color();
            }
        }
    }

    /**
     *
     * @param array
     * @param xSize
     * @param ySize
     */
    public Image(double array[], int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.color = new Color[xSize][ySize];
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                color[j][i] = new Color(array[(i*xSize) + j]);
            }
        }
    }

    //We do not want setters because if we have setters then the array will not be the correct size
    public int getYSize() {
        return ySize;
    }

    public int getXSize() { return xSize; }

    private boolean isValidPoint(int x, int y) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0) {
            return true;
        }
        return false;
    }

    public boolean setColorPoint(int x, int y, Color color) {
        if (isValidPoint(x, y))
        {
            this.color[x][y] = new Color(color);
            return true;
        } else {
            return false;
        }
    }

    public Color getColorPoint(int x, int y) throws Exception {
        if (isValidPoint(x, y)) {
            return this.color[x][y];
        }
        throw new Exception();
    }

    public int getDifference (int x, int y, Color color) {
        int total = 0;


        try {
            Color colorComp = this.getColorPoint(x, y);
            total += Math.abs(colorComp.getR() - color.getR());
            total += Math.abs(colorComp.getG() - color.getG());
            total += Math.abs(colorComp.getB() - color.getB());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public boolean isBlackPixel(int x, int y) {
        try {
            return getColorPoint(x, y).isBlack();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public void setBlackPixel(int x, int y) {
        try {
            getColorPoint(x,y).setBlack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setWhitePixel(int x, int y) {
        try {
            getColorPoint(x, y).setWhite();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean hasAbove(int x, int y) {
        if (y > 0) {
            return true;
        }
        return false;
    }
    public boolean hasBelow(int x, int y) {
        if ( y < (ySize - 1)) {
            return true;
        }
        return false;
    }
    public boolean hasLeft(int x, int y) {
        if (x > 0) {
            return true;
        }
        return false;
    }
    public boolean hasRight(int x, int y) {
        if (x < (xSize - 1)) {
            return true;
        }
        return false;
    }

    public void convertImageToGray() {
        for (int i = 0; i < ySize; i++){
            for (int j = 0; j < xSize; j++) {
                try {
                    getColorPoint(j, i).convertColorToGray();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public double[] getGrayArray() {
        double[] myArray;
        myArray = new double[xSize * ySize];

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                int index = (i * xSize) + j;
                try {
                    myArray[index] = getColorPoint(j, i).getGrayValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return myArray;
    }

    public double[] resizeImage(double[] oldArray, int newWidth, int newHeight, int oldWidth, int oldHeight) {
        //Thanks to http://willperone.net/Code/codescaling.php

        double[] newArray = new double[newWidth * newHeight];


        int YD = (oldHeight / newHeight) * oldWidth - oldWidth;
        int YR = oldHeight % newHeight;
        int XD = oldWidth / newWidth;
        int XR = oldWidth % newWidth;
        int outOffset = 0;
        int inOffset = 0;

        for (int y = newHeight, YE = 0; y > 0; y--) {
            for (int x = newWidth, XE = 0; x > 0; x--) {
                newArray[outOffset++] = oldArray[inOffset];
                inOffset += XD;
                XE += XR;
                if(XE >= newWidth) {
                    XE -= newWidth;
                    inOffset++;
                }
            }
            inOffset += YD;
            YE += YR;
            if (YE >= newHeight) {
                YE -= newHeight;
                inOffset += oldWidth;
            }
        }
        return newArray;
    }
}
