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
    private Color[][] color;

    private int[][] raster;

    Image(int rows, int cols) {
        if (rows < 1) {
            rows = 1;
        }
        if (cols < 1) {
            cols = 1;
        }
        this.rows = rows;
        this.cols = cols;
        this.raster = new int[rows][cols];
        this.color = new Color[rows][cols];
    }

    private boolean isValidPoint(int x, int y) {
        if (x < rows && y < cols && x >= 0 && y >= 0) {
            return true;
        }
        return false;
    }

    public boolean setDataPoint(int x, int y, int raster) {
        if (isValidPoint(x, y))
        {
            this.raster[x][y] = raster;
            return true;
        } else {
            this.raster[x][y] = -1;
            return false;
        }
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

}
