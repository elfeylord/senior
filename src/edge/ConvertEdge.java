package edge;

import image.Color;
import image.Image;

/**
 * Created by elfeylord on 10/10/15.
 */
public class ConvertEdge {

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private Image image;

    public ConvertEdge(Image image) {
        this.image = image;
    }

    public void convert(int threshold) {
        Image newImage = new Image(image.getXSize(), image.getYSize());

        int ySize = image.getYSize();
        int xSize = image.getXSize();

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                boolean rewrite = rewritePixel(image, j, i, threshold);
                if (rewrite) {
                    newImage.setColorPoint(j, i, new Color(0 ,0 ,0));
                } else {
                    newImage.setColorPoint(j, i, new Color(255 ,255 ,255));
                }
            }
        }
        this.image = newImage;
    }

    private boolean rewritePixel(Image image, int x, int y, int threshold) {
        int darkestPixel = 0;
        int lightestPixel = 255;

        //top left
        if (x > 0 && y > 0) {
            try {
                Color color = image.getColorPoint((x - 1), (y - 1));
                int brightnessValue = getBrightnessValue(color);
                if (brightnessValue < lightestPixel) {
                    lightestPixel = brightnessValue;
                }
                if (brightnessValue > darkestPixel) {
                    darkestPixel = brightnessValue;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
        //top
        if (y > 0) {
            try {
                Color color = image.getColorPoint((x), (y - 1));
                int brightnessValue = getBrightnessValue(color);
                if (brightnessValue < lightestPixel) {
                    lightestPixel = brightnessValue;
                }
                if (brightnessValue > darkestPixel) {
                    darkestPixel = brightnessValue;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
        //top right
        if (((x + 1) < image.getXSize()) && y > 0) {
            try {
                Color color = image.getColorPoint((x + 1), (y - 1));
                int brightnessValue = getBrightnessValue(color);
                if (brightnessValue < lightestPixel) {
                    lightestPixel = brightnessValue;
                }
                if (brightnessValue > darkestPixel) {
                    darkestPixel = brightnessValue;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
        //left
        if (x > 0) {
            try {
                Color color = image.getColorPoint((x - 1), (y));
                int brightnessValue = getBrightnessValue(color);
                if (brightnessValue < lightestPixel) {
                    lightestPixel = brightnessValue;
                }
                if (brightnessValue > darkestPixel) {
                    darkestPixel = brightnessValue;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
        //right
        if ((x + 1) < image.getXSize()) {
            try {
                Color color = image.getColorPoint((x + 1), (y));
                int brightnessValue = getBrightnessValue(color);
                if (brightnessValue < lightestPixel) {
                    lightestPixel = brightnessValue;
                }
                if (brightnessValue > darkestPixel) {
                    darkestPixel = brightnessValue;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
        //bottom left
        if (x > 0  && ((y + 1) < image.getYSize())) {
            try {
                Color color = image.getColorPoint((x - 1), (y + 1));
                int brightnessValue = getBrightnessValue(color);
                if (brightnessValue < lightestPixel) {
                    lightestPixel = brightnessValue;
                }
                if (brightnessValue > darkestPixel) {
                    darkestPixel = brightnessValue;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
        //bottom
        if ((y + 1) < image.getYSize()) {
            try {
                Color color = image.getColorPoint((x), (y + 1));
                int brightnessValue = getBrightnessValue(color);
                if (brightnessValue < lightestPixel) {
                    lightestPixel = brightnessValue;
                }
                if (brightnessValue > darkestPixel) {
                    darkestPixel = brightnessValue;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
        //bottom right
        if (((x + 1) < image.getXSize()) && ((y + 1) < image.getYSize())) {
            try {
                Color color = image.getColorPoint((x + 1), (y + 1));
                int brightnessValue = getBrightnessValue(color);
                if (brightnessValue < lightestPixel) {
                    lightestPixel = brightnessValue;
                }
                if (brightnessValue > darkestPixel) {
                    darkestPixel = brightnessValue;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        if ((darkestPixel - lightestPixel) > threshold ) {
            return true;
        }
        return false;

    }

    private int getBrightnessValue(Color color) {

        //Perceived Brightness of a Color
        //http://www.nbdtech.com/Blog/archive/2008/04/27/Calculating-the-Perceived-Brightness-of-a-Color.aspx
        //brightness  =  sqrt( .241 R2 + .691 G2 + .068 B2 )

        int r = color.getR();
        int g = color.getG();
        int b = color.getB();
        double brightness = 0.333 * (double)(r*r) + 0.333 * (double)(g*g) + 0.334 * (double)(b*b);
        //double brightness = 0.299 * (double)(r*r) + 0.587 * (double)(g*g) + 0.114 * (double)(b*b);
        brightness = Math.sqrt(brightness);
        return (int)Math.round(brightness);
    }
}
