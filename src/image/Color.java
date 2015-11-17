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
    public Color() { setColor(255, 255, 255); }


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

    public int getGrayValue() {
        float gray = 0;

        // 0.2989, 0.5870, 0.1140.
        //http://stackoverflow.com/questions/687261/converting-rgb-to-grayscale-intensity

        gray += (float)r * 0.2989;
        gray += (float)g * 0.5870;
        gray += (float)b * 0.1140;

        return (int)gray;
    }

    public void convertColorToGray() {
        r =  (int)((float)r * 0.2989);
        g =  (int)((float)g * 0.5870);
        b =  (int)((float)b * 0.1140);
    }
    public Color(double grayValue) {
        grayValue = Math.abs(grayValue);
        r =  notBigger((int)(grayValue * 0.2989));
        g =  notBigger((int)(grayValue * 0.5870));
        b =  notBigger((int)(grayValue * 0.1140));
    }
    private int notBigger(int value) {
        if (value > 255) {
            return 255;
        }
        return value;
    }
}
