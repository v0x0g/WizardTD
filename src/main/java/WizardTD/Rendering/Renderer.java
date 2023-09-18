package WizardTD.Rendering;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Tiles.*;
import WizardTD.UI.*;
import lombok.experimental.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import static WizardTD.GameConfig.*;
import static WizardTD.UI.GuiConfig.*;

@UtilityClass
public class Renderer {
    public void renderGameData(@NonNull final PApplet app, @NonNull final GameData game) {
        // Render the game tiles
        Loggers.UI.debug("start render tiles");
        // Where we start rendering all tiles (global offset)
        for (int r = 0; r < BOARD_SIZE_TILES; r++) {
            for (int c = 0; c < BOARD_SIZE_TILES; c++) {
                final Tile tile = game.board.getTile(r, c);
                Loggers.UI.trace("render tile [{00}}, {00}]", r, c);
                tile.render(app);
            }
        }
    }

    public void renderSimpleTile(
            @NonNull final PApplet app, @Nullable PImage img,
            final double centreX, final double centreY) {
        Loggers.UI.trace("tile [{00}}, {00}]: render img {}", centreX, centreY, img);
        if (!UiManager.isValidImage(img)) {
            img = UiManager.missingTextureImage;
            Loggers.UI.trace("render tile [{00}, {00}]: missing texture", centreX, centreY);
        }
        app.imageMode(PConstants.CENTER);
        app.colorMode(PConstants.ARGB);
        app.image(img, (float) centreX, (float) centreY);
    }

    public @NonNull Vector2 calculateUiCoordsForTile(final int tileX, final int tileY) {
        //noinspection UnnecessaryLocalVariable
        final int globalOffsetX = 0, globalOffsetY = TOPBAR_HEIGHT_PX;
        // Offset by the tile's coordinates, and then half a tile extra to move to the centre 
        final int middlePosX = globalOffsetX + (tileX * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
        final int middlePosY = globalOffsetY + (tileY * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
        return new Vector2(middlePosX, middlePosY);
    }
}
