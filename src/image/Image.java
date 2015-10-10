package image;

/**
 * Created by elfeylord on 10/9/15.
 */
public class Image {

    private int rowsSize;
    private int colsSize;
    private Color[][] color;

    public Image(int rowsSize, int colsSize) {
        if (rowsSize < 1) {
            rowsSize = 1;
        }
        if (colsSize < 1) {
            colsSize = 1;
        }
        this.rowsSize = rowsSize;
        this.colsSize = colsSize;
        this.color = new Color[rowsSize][colsSize];
    }

    //We do not want setters because if we have setters then the array will not be the correct size
    public int getColsSize() {
        return colsSize;
    }

    public int getRowsSize() { return rowsSize; }

    private boolean isValidPoint(int x, int y) {
        if (x < rowsSize && y < colsSize && x >= 0 && y >= 0) {
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

}
