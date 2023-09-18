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

import java.util.*;

import static WizardTD.GameConfig.*;
import static WizardTD.UI.GuiConfig.*;
import static WizardTD.UI.GuiConfig.UiPositions.*;

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

    public @NonNull Optional<Tile> getTileFromMouseCoords(final int mouseX, final int mouseY, final @NonNull GameData gameData) {
        // Use inverse lerp to extract the tile coordinates from the mouse pos
        final double x = Numerics.inverseLerp(mouseX, BOARD_POS_X, BOARD_POS_X + (CELL_SIZE_PX * BOARD_SIZE_TILES));
        final double y = Numerics.inverseLerp(mouseY, BOARD_POS_Y, BOARD_POS_Y + (CELL_SIZE_PX * BOARD_SIZE_TILES));

        // Int conversion naturally floors to zero-based indices
        final int tileX = (int) (x * BOARD_SIZE_TILES);
        final int tileY = (int) (y * BOARD_SIZE_TILES);

        final Optional<Tile> tile = gameData.board.maybeGetTile(tileX, tileY);
        Logger.info("x={0.000} ({})\ty={0.000} ({}): {}", x, tileX, y, tileY, tile);
        return tile;

    }

    public @NonNull Vector2 calculateUiCoordsForTile(final int tileX, final int tileY) {
        // Offset by the tile's coordinates, and then half a tile extra to move to the centre 
        final int middlePosX = BOARD_POS_X + (tileX * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
        final int middlePosY = BOARD_POS_Y + (tileY * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
        return new Vector2(middlePosX, middlePosY);
    }
}