package detection;

import image.Image;

/**
 * Created by elfeylord on 10/24/15.
 */
public class Blob extends Image{
    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getNumPoints() {
        return numPoints;
    }

    public void setNumPoints(int numPoints) {
        this.numPoints = numPoints;
    }

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    private int numPoints;


    public Blob(int x, int y) {
        super(x, y);
        numPoints = 0;
        maxX = -1;
        maxY = -1;
        minX = x;
        minY = y;
    }

    public void setBlackPixel(int x, int y) {
        if (!super.isBlackPixel(x, y)) {
            super.setBlackPixel(x, y);
            if (x > maxX) {
                maxX = x;
            }
            if (y > maxY) {
                maxY = y;
            }
            if (x < minX) {
                minX = x;
            }
            if (y < minY) {
                minY = y;
            }
            numPoints++;
        }
    }
}
