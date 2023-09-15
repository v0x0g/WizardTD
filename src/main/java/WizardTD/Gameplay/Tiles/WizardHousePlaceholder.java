package WizardTD.Gameplay.Tiles;

import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

/**
 * Placeholder tile that represents a spot where a wizard should go
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class WizardHousePlaceholder extends Tile {

    @Override
    public @Nullable PImage getImage() {
        return null;
    }
}