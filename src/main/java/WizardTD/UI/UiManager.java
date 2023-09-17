package WizardTD.UI;

import WizardTD.Ext.*;
import com.google.errorprone.annotations.*;
import lombok.experimental.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import static WizardTD.UI.GuiConfig.*;

@UtilityClass
public class UiManager {

    public final @NonNull PImage missingTextureImage = ImageExt.generatePattern(
            GuiConfig.CELL_SIZE_PX, GuiConfig.CELL_SIZE_PX, CELL_SIZE_PX >> 2, 2,
            ImageExt.ImagePattern.CHECKERS,
            Colours.BRIGHT_PURPLE.code, Colours.BLACK.code
    );

    public boolean isValidImage(@Nullable final PImage img) {
        return img != null && img.height > 0 && img.width > 0;
    }

    /**
     * Loads an image at a given path
     *
     * @param app  Applet instance to load the image with
     * @param path Path to the image file
     */
    public @NonNull PImage loadImage(@NonNull final PApplet app, @NonNull @CompileTimeConstant final String path) {
        Loggers.UI.debug("loading image at {}", path);
        //noinspection LocalCanBeFinal
        val img = app.loadImage(path);
        Loggers.UI.debug("loaded image at {}: {} (valid={})", path, img, isValidImage(img));
        return img;
    }

}