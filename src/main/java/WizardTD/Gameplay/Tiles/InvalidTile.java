package WizardTD.Gameplay.Tiles;

import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import java.util.concurrent.*;

/**
 * A tile that represents an invalid tile that has not been initialised
 * This do be bad
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class InvalidTile extends Tile {

    @Override
    public @Nullable PImage getImage() {
        final int FULL_WHITE = 0xFF_FF_FF_FF;
        final int FULL_BLACK = 0xFF_00_00_00;
        // Flickering B&W
        final var img = new PImage(1, 1, PConstants.ARGB);
        img.set(0, 0, ThreadLocalRandom.current().nextBoolean() ? FULL_WHITE : FULL_BLACK);
        return img;
    }
}
