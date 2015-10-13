package image;

/**
 * Created by elfeylord on 10/9/15.
 */
public class Image {

    private int xSize;
    private int ySize;
    private Color[][] color;

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

}
