package WizardTD.Ext;

import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
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
    public PImage rotate(final PApplet applet, final PImage pImg, final double angle) {
        final BufferedImage img = (BufferedImage) pImg.getNative();
        final double rads = Math.toRadians(angle);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = img.getWidth();
        final int h = img.getHeight();
        final int newWidth = (int) Math.floor(w * cos + h * sin);
        final int newHeight = (int) Math.floor(h * cos + w * sin);

        final PImage result = applet.createImage(newWidth, newHeight, PConstants.RGB);
        //TODO: Maybe we can use BufferedImage directly and avoid having to reference PApplet
//        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
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

    public PImage generatePattern(
            final int w, final int h, final int thickness, final int spacing, final ImagePattern pattern,
            final int col1, final int col2) {
        final PImage img = new PImage(w, h);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final int a = (x / thickness % spacing);
                final int b = (y / thickness % spacing);
                final int v = pattern.calculate(a, b);
                img.set(x, y, v != 0 ? col1 : col2);
            }
        }
        return img;
    }

    public boolean isValidImage(@Nullable final PImage img) {
        return img != null && img.height > 0 && img.width > 0;
    }

    @SuppressWarnings("unused") // Public API
    public enum ImagePattern {
        CHECKERS {
            @Override
            int calculate(final int a, final int b) {
                return (a + b) % 2;
            }
        } // If we change the `%2` we get some cool stuff
        , DIAGONAL_LINES {
            @Override
            int calculate(final int a, final int b) {
                return a ^ b;
            }
        }, DOTS {
            @Override
            int calculate(final int a, final int b) {
                return a | b;
            }
        }, DOTS_ALT {
            @Override
            int calculate(final int a, final int b) {
                return a + b;
            }
        }, GRID_LINES {
            @Override
            int calculate(final int a, final int b) {
                return a * b;
            }
        }, HORIZONTAL_LINES {
            @Override
            int calculate(final int a, final int b) {
                return b;
            }
        }, SOLID {
            @Override
            int calculate(final int a, final int b) {
                return 1;
            }
        }, TRIANGLES {
            @Override
            int calculate(final int a, final int b) {
                return a - b;
            }
        }, VERTICAL_LINES {
            @Override
            int calculate(final int a, final int b) {
                return a;
            }
        };

        abstract int calculate(final int a, final int b);
    }
}
