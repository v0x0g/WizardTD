package WizardTD.Gameplay.Tiles;

import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = false)
public final class Tower extends Tile {

    public static @Nullable PImage tileImage = null;

    @Override
    public @Nullable PImage getImage() {
        return tileImage;
    }
}
