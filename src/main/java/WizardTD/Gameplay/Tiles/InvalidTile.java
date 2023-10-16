package WizardTD.Gameplay.Tiles;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import WizardTD.UI.Appearance.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

/**
 * A tile that represents an invalid tile that has not been initialised
 * This do be bad
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class InvalidTile extends Tile {

    private static final @NonNull PImage invalidTileImg = ImageExt.generatePattern(
            GuiConfig.CELL_SIZE_PX, GuiConfig.CELL_SIZE_PX, GuiConfig.CELL_SIZE_PX/2, 2,
            ImageExt.ImagePattern.CHECKERS,
            Colour.BRIGHT_PURPLE.asInt(), Colour.RED.asInt()
    );

    @Override
    public void render(@NonNull final PApplet app, @NonNull final GameData gameData, @NonNull final UiState uiState) {
        Renderer.renderSimpleTile(app, invalidTileImg, UiManager.tileToPixelCoords(this));
    }
}
