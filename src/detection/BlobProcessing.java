package detection;

import image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elfeylord on 10/24/15.
 */
public class BlobProcessing {

    public BlobProcessing(Image image) {
        this.image = image;
        blobs = new ArrayList<Blob>();
        processImage();

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private Image image;

    public List<Blob> getBlobs() {
        return blobs;
    }

    public void setBlobs(List<Blob> blobs) {
        this.blobs = blobs;
    }

    private List<Blob> blobs;

    private void processImage(){
        Blob newBlob;
        for (int i = 0; i < image.getYSize(); i++) {
            for (int j = 0; j < image.getXSize(); j++) {
                //Check if this pixel is already part of a blob
                boolean skipPixel = false;
                for (int k = 0; k < blobs.size(); k++){
                    if (blobs.get(k).isBlackPixel(j, i)) {
                        skipPixel = true;
                    }
                }
                if (!skipPixel && image.isBlackPixel(j, i)) {

                    newBlob = new Blob(image.getXSize(), image.getYSize());

                    //set the pixel in the blob
                    newBlob.setBlackPixel(j,i);
                    //then loop through all the pixels until we can't set another pixel
                    boolean keepGoing = true;
                    while (keepGoing) {
                        keepGoing = false;
                        int maxY = (newBlob.getMaxY() + 1);
                        int maxX = (newBlob.getMaxX() + 1);
                        for (int i2 = newBlob.getMinY(); i2 < maxY; i2++) {
                            for (int j2 = newBlob.getMinX(); j2 < maxX; j2++) {
                                if (newBlob.isBlackPixel(j2, i2)) {
                                    if (newBlob.hasAbove(j2, i2)) {
                                        if (image.isBlackPixel(j2, i2 - 1) && !newBlob.isBlackPixel(j2, i2 - 1)) {
                                            newBlob.setBlackPixel(j2, i2 - 1);
                                            keepGoing = true;
                                        }
                                    }
                                    if (newBlob.hasBelow(j2, i2)) {
                                        if (image.isBlackPixel(j2, i2 + 1) && !newBlob.isBlackPixel(j2, i2 + 1)) {
                                            newBlob.setBlackPixel(j2, i2 + 1);
                                            keepGoing = true;
                                        }
                                    }
                                    if (newBlob.hasRight(j2, i2)) {
                                        if (image.isBlackPixel(j2 + 1, i2) && !newBlob.isBlackPixel(j2 + 1, i2)) {
                                            newBlob.setBlackPixel(j2 + 1, i2);
                                            keepGoing = true;
                                        }
                                    }
                                    if (newBlob.hasLeft(j2, i2)) {
                                        if (image.isBlackPixel(j2 - 1, i2) && !newBlob.isBlackPixel(j2 - 1, i2)) {
                                            newBlob.setBlackPixel(j2 - 1, i2);
                                            keepGoing = true;
                                        }
                                    }
                                }

                            }
                        }
                    }
                    blobs.add(newBlob);
                }
            }
        }
    }
}
