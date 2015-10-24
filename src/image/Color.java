package image;

/**
 * Created by elfeylord on 10/10/15.
 */
public class Color {
    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    private int r;
    private int g;
    private int b;

    public void setColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void setColor(Color color)
    {
        setColor(color.getR(), color.getG(), color.getB());
    }

    public Color(int r, int g, int b) {
        setColor(r, g, b);
    }

    public boolean isBlack() {
        if (r == 0 && g == 0 && b ==0) {
            return true;
        }
        return false;
    }

    public void setBlack () {
        r = 0;
        g = 0;
        b = 0;
    }

    public void setWhite () {
        r = 255;
        g = 255;
        b = 255;
    }

    public Color(Color color) {
        setColor(color);
    }
}
