package WizardTD.Gameplay.Tiles;

import WizardTD.Ext.*;
import WizardTD.UI.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

/**
 * A tile that represents an invalid tile that has not been initialised
 * This do be bad
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class InvalidTile extends Tile {

    private static final @NonNull PImage invalidTileImg = ImageExt.generatePattern(
            GuiConfig.CELL_SIZE_PX, GuiConfig.CELL_SIZE_PX, GuiConfig.CELL_SIZE_PX/2, 2,
            ImageExt.ImagePattern.CHECKERS,
            Colours.BRIGHT_PURPLE.code, Colours.RED.code
    );

    @Override
    public @NonNull PImage getImage() {return invalidTileImg;}
}
