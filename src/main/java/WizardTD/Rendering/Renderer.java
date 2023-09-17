package WizardTD.Rendering;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Tiles.*;
import WizardTD.UI.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import static WizardTD.GameConfig.*;
import static WizardTD.UI.GuiConfig.*;

@UtilityClass
public class Renderer {
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
        for (int r = 0; r < BOARD_SIZE_TILES; r++) {
            for (int c = 0; c < BOARD_SIZE_TILES; c++) {
                Tile tile = game.board.getTile(r, c);
                if (!(tile instanceof WizardHouse)) continue;
                // Offset by the tile's coordinates, and then half a tile extra to move to the centre 
                final int middlePosX = globalOffsetX + (r * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
                final int middlePosY = globalOffsetY + (c * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
                tile.render(app, middlePosX, middlePosY);
            }
        }
    }

    public void renderSimpleTile(
            @NonNull final PApplet app, @Nullable PImage img,
            final float centreX, final float centreY) {
        Loggers.UI.trace("tile [{00}}, {00}]: render img {}", centreX, centreY, img);
        if (!UiManager.isValidImage(img)) {
            img = UiManager.missingTextureImage;
            Loggers.UI.trace("render tile [{00}, {00}]: missing texture", centreX, centreY);
        }
        app.imageMode(PConstants.CENTER);
        app.colorMode(PConstants.ARGB);
        app.image(img, centreX, centreY);
    }
}
