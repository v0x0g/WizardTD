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
@ExtensionMethod(Util.class)
public class UiManager {

    private final @NonNull TaggedLogger UiLog = Logger.tag("ui");
    private final @NonNull PImage missingTextureImage = ImageExt.generatePattern(
            GuiConfig.CELL_SIZE_PX, GuiConfig.CELL_SIZE_PX, 1, 1,
            ImageExt.ImagePattern.CHECKERS,
            Colours.BRIGHT_PURPLE.code, Colours.BLACK.code
    );

    private boolean isValidImage(@Nullable final PImage img) {
        return img != null && img.loaded && img.height > 0 && img.width > 0;
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
        UiLog.debug("loaded image at {}: {}", path, img);
        return img;
    }

    @SuppressWarnings("LocalCanBeFinal")
    public void renderTiles(@NonNull final PApplet app, @NonNull final GameData game) {
        // Render the game tiles
        UiLog.debug("start render tiles");
        Vector2 tileStartCoord = new Vector2(0, TOPBAR_HEIGHT_PX);
        for (int r = 0; r < BOARD_SIZE_TILES -4; r++) {
            for (int c = 0; c < BOARD_SIZE_TILES; c++) {
                UiLog.trace("render tile [{00}}, {00}]", r, c);
                Tile tile = game.board.getTile(r, c);
                PImage img = tile.getImage();
                UiLog.trace("tile [{00}}, {00}]: tile {} img {}", r, c, tile, img);
                if (!isValidImage(img)) {
                    img = missingTextureImage;
                }
                Vector2 offset = new Vector2(r * CELL_SIZE_PX, c * CELL_SIZE_PX);
                Vector2 pos = tileStartCoord.exactClone();
                pos.add(offset);
                app.image(img, (float) pos.x, (float) pos.y);
            }
        }
    }

}

@UtilityClass
class Util {
//    public Vector2 toProcessingUiCoords(@NonNull final Vector2 coord) {
//
//    }
}
