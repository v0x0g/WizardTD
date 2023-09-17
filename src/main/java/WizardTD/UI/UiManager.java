package WizardTD.UI;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Tiles.*;
import com.google.errorprone.annotations.*;
import lombok.experimental.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;
import processing.core.*;

import static WizardTD.GameConfig.*;
import static WizardTD.UI.GuiConfig.*;

@UtilityClass
public class UiManager {

    private final @NonNull TaggedLogger UiLog = Logger.tag("ui");
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
        UiLog.debug("loading image at {}", path);
        //noinspection LocalCanBeFinal
        val img = app.loadImage(path);
        UiLog.debug("loaded image at {}: {} (valid={})", path, img, isValidImage(img));
        return img;
    }

    @SuppressWarnings("LocalCanBeFinal")
    public void renderTiles(@NonNull final PApplet app, @NonNull final GameData game) {
        // Render the game tiles
        UiLog.debug("start render tiles");
        // Where we start rendering all tiles (global offset)
        final int globalOffsetX = 0, globalOffsetY = TOPBAR_HEIGHT_PX;
        Vector2 tileStartCoord = new Vector2(0, TOPBAR_HEIGHT_PX);
        for (int r = 0; r < BOARD_SIZE_TILES; r++) {
            for (int c = 0; c < BOARD_SIZE_TILES; c++) {
                UiLog.trace("render tile [{00}}, {00}]", r, c);
                Tile tile = game.board.getTile(r, c);
                PImage img = tile.getImage();
                UiLog.trace("tile [{00}, {00}]: tile {} img {}", r, c, tile, img);
                if (!isValidImage(img)) {
                    img = missingTextureImage;
                    UiLog.trace("tile [{00}, {00}]: missing texture", r, c);
                }
                // Offset for this tile (relative to global offset) 
                final int localOffsetX = r * CELL_SIZE_PX, localOffsetY = c * CELL_SIZE_PX;
                // The start and end coordinates where we render the image (opposite corners)
                final int startPosX = globalOffsetX + localOffsetX, startPosY = globalOffsetY + localOffsetY;
                //  final int endPosX = globalOffsetX + localOffsetX, endPosY = globalOffsetY + localOffsetY;

                // A note on how processing works:
                // It uses a really weird filtering (I think bicubic) on images, which does look kinda wack
                // So 
                if (img.width != CELL_SIZE_PX || img.height != CELL_SIZE_PX) {
                    UiLog.trace(
                            "tile [{00}, {00}]: expected dimensions [{}x{}] but got [{}x{}], expect weird scaling", r,
                            c, CELL_SIZE_PX, CELL_SIZE_PX, img.width, img.height
                    );
                    app.image(img, startPosX, startPosY, CELL_SIZE_PX, CELL_SIZE_PX);
                }
                else app.image(img, startPosX, startPosY);
            }
        }
    }

}