package WizardTD.Gameplay.Tiles;

import WizardTD.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import lombok.*;
import processing.core.*;

/**
 * A tile that represents an invalid tile that has not been initialised
 * This do be bad
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class InvalidTile extends Tile {

    private static final PImage invalidTileImg = ImageExt.generatePattern(
            GameConfig.TILE_SIZE_PX, GameConfig.TILE_SIZE_PX, GameConfig.TILE_SIZE_PX / 2, 2,
            ImageExt.ImagePattern.CHECKERS,
            Colour.BRIGHT_PURPLE, Colour.RED
    );

    @Override
    public void render(final App app, final GameData gameData, final UiState uiState) {
        Renderer.renderSimpleTile(app, invalidTileImg, UiManager.tileToPixelCoords(this));
    }
}
