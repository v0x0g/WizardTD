package WizardTD.UI;

import com.google.errorprone.annotations.*;
import lombok.experimental.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;
import processing.core.*;

@UtilityClass
public class UiManager {

    private final @NonNull TaggedLogger UiLog = Logger.tag("ui");

    private boolean isValidImage(@Nullable final PImage img) {
        return img != null && img.loaded && img.height > 0 && img.width > 0;
    }

    public @NonNull PImage loadImage(@NonNull final PApplet app, @NonNull @CompileTimeConstant final String path) {
        UiLog.trace("loading image at {}", path);
        final val img = app.loadImage(path);
        UiLog.trace("loaded image at {}: {}", path, img);
        return img;
    }

}
