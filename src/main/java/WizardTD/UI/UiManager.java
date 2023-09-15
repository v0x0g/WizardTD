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

    /**
     * Function that loads and initialises all the graphics objects.
     * Since this is run on the animation thread, we can do long-running operations here.
     */
    public void loadGraphics(@NonNull final PApplet app) {
        UiLog.debug("starting load graphics");
        // Unfortunately I gotta load all the images here and forward them onto the classes here
        WizardTD.Gameplay.Tiles.Grass.tileImage = loadImage(app, Resources.Tiles.Grass.Tile);
        WizardTD.Gameplay.Tiles.Path.tileImage = loadImage(app, Resources.Tiles.Path.Tile);
        WizardTD.Gameplay.Tiles.Shrub.tileImage = loadImage(app, Resources.Tiles.Shrub.Tile);
        WizardTD.Gameplay.Tiles.Tower.tileImage = loadImage(app, Resources.Tiles.Grass.Tile);
        WizardTD.Gameplay.Tiles.WizardHouse.tileImage = loadImage(app, Resources.Tiles.Grass.Tile);
        WizardTD.Gameplay.Tiles.Grass.tileImage = loadImage(app, Resources.Tiles.Grass.Tile);
        WizardTD.Gameplay.Tiles.Grass.tileImage = loadImage(app, Resources.Tiles.Grass.Tile);
        WizardTD.Gameplay.Tiles.Grass.tileImage = loadImage(app, Resources.Tiles.Grass.Tile);
        
        UiLog.debug("done load graphics");
    }
    
    private boolean isValidImage(@Nullable final PImage img){
        return img != null && img.loaded && img.height > 0 && img.width > 0;
    }
    
    private @NonNull PImage loadImage(@NonNull final PApplet app, @NonNull @CompileTimeConstant final String path){
        UiLog.trace("loading image at {}", path);
        val img = app.loadImage(path);
        UiLog.trace("loaded image at {}: {}", path, img);
        return img;
    }

    @UtilityClass
    private static class Resources {

        public static final @NonNull String BASE_DIR = "src/main/resources/WizardTD";

        @UtilityClass
        private static class Tiles {

            public static final @NonNull String DIR = Resources.BASE_DIR + "/Tiles";

            @UtilityClass
            private static class Grass {
                public static final @NonNull String DIR = Tiles.DIR + "/Grass";
                
                public static final @NonNull String Tile = DIR + "/grass.png";                    
            }

        }

    }

}
