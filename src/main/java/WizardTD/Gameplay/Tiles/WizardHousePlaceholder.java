package WizardTD.Gameplay.Tiles;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

import java.util.concurrent.*;

/**
 * Placeholder tile that represents a spot where a wizard should go
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class WizardHousePlaceholder extends Tile {

    @Override
    public @NonNull PImage getImage() {
        // Random colours lol
        final var img = new PImage(1, 1, PConstants.ARGB);
        img.set(0,0, ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
        return img;
    }
}