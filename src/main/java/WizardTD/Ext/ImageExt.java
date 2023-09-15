package WizardTD.Ext;

import lombok.experimental.*;
import processing.core.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

@UtilityClass
public class ImageExt {

    /**
     * <a href="https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java">Source</a>
     *
     * @param pImg   The image to be rotated
     * @param angle  between 0 and 360 degrees
     * @param applet PApplet so we can create the image
     * @return the new rotated image
     */
    public PImage rotate(final PApplet applet, final PImage pimg, final double angle) {
        final BufferedImage img = (BufferedImage) pimg.getNative();
        final double rads = Math.toRadians(angle);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = img.getWidth();
        final int h = img.getHeight();
        final int newWidth = (int) Math.floor(w * cos + h * sin);
        final int newHeight = (int) Math.floor(h * cos + w * sin);

        final PImage result = applet.createImage(newWidth, newHeight, PApplet.RGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        final BufferedImage rotated = (BufferedImage) result.getNative();
        final Graphics2D g2d = rotated.createGraphics();
        final AffineTransform at = new AffineTransform();
        at.translate((double) (newWidth - w) / 2, (double) (newHeight - h) / 2);

        final int x = w / 2;
        final int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }

}
