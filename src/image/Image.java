package image;

/**
 * Created by elfeylord on 10/9/15.
 */
public class Image {

    //We do not want setters because if we have setters then the array will not be the correct size
    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    private int rows;
    private int cols;

    //TODO: change this to color and change all the getters and setters.
    private int[][] data;

    Image(int rows, int cols) {
        if (rows < 1) {
            rows = 1;
        }
        if (cols < 1) {
            cols = 1;
        }
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }

    private boolean isValidPoint(int x, int y) {
        if (x < rows && y < cols && x >= 0 && y >= 0) {
            return true;
        }
        return false;
    }

    public boolean setDataPoint(int x, int y, int dataPoint) {
        if (isValidPoint(x, y))
        {
            this.data[x][y] = dataPoint;
            return true;
        } else {
            return false;
        }
    }

    public int getDataPoint(int x, int y) throws Exception {
        if (isValidPoint(x, y)) {
            return this.data[x][y];
        }
        throw new Exception();
    }

}
