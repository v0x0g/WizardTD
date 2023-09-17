package WizardTD.UI;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Tiles.*;
import com.google.errorprone.annotations.*;
import lombok.experimental.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import static WizardTD.GameConfig.*;
import static WizardTD.UI.GuiConfig.*;

@UtilityClass
public class UiManager {

    private final @NonNull PImage missingTextureImage = ImageExt.generatePattern(
            GuiConfig.CELL_SIZE_PX, GuiConfig.CELL_SIZE_PX, CELL_SIZE_PX >> 2, 2,
            ImageExt.ImagePattern.CHECKERS,
            Colours.BRIGHT_PURPLE.code, Colours.BLACK.code
    );

    private boolean isValidImage(@Nullable final PImage img) {
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

    @SuppressWarnings("LocalCanBeFinal")
    public void renderTiles(@NonNull final PApplet app, @NonNull final GameData game) {
        // Render the game tiles
        Loggers.UI.debug("start render tiles");
        // Where we start rendering all tiles (global offset)
        //noinspection UnnecessaryLocalVariable
        final int globalOffsetX = 0, globalOffsetY = TOPBAR_HEIGHT_PX;
        for (int r = 0; r < BOARD_SIZE_TILES; r++) {
            for (int c = 0; c < BOARD_SIZE_TILES; c++) {
                Loggers.UI.trace("render tile [{00}}, {00}]", r, c);
                Tile tile = game.board.getTile(r, c);
                // Offset by the tile's coordinates, and then half a tile extra to move to the centre 
                final int middlePosX = globalOffsetX + (r * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
                final int middlePosY = globalOffsetY + (c * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
                tile.render(app, middlePosX, middlePosY);
            }
        }
    }

    public void renderSimpleTile(
            @NonNull final PApplet app, @Nullable PImage img, final float centreX, final float centreY) {
        Loggers.UI.trace("tile [{00}}, {00}]: render img {}", centreX, centreY, img);
        if (!isValidImage(img)) {
            img = missingTextureImage;
            Loggers.UI.trace("render tile [{00}, {00}]: missing texture", centreX, centreY);
        }
        app.image(img, centreX, centreY);
    }
}