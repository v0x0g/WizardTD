package WizardTD.Gameplay.Tiles;

import WizardTD.Gameplay.Game.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

import java.util.*;

@Getter
@ToString
public abstract class Tile {
    @Setter(AccessLevel.PUBLIC)
    private int posX, posY;

    /**
     * Parses a char as a Tile
     *
     * @param c The char to parse
     * @return Either a parsed tile, or empty if the char was invalid
     */
    public static @NonNull Optional<Tile> fromChar(final char c) {
        switch (c) {
            case 'S':
                return Optional.of(new Shrub());
            case 'W':
                return Optional.of(new WizardHousePlaceholder());
            case ' ':
                return Optional.of(new Grass());
            case 'X':
                return Optional.of(new Path());
            default:
                return Optional.empty();
        }
    }

    /**
     * Method to be called whenever something changes on the board,
     * and the tile should be updated to reflect this
     */
    public void boardDirty(@NonNull final Board board) {}

    /**
     * Renders the current tile, with the given coordinates `centreX, centreY`
     * as the centre of the tile (on-screen)
     */
    public abstract void render(final @NonNull PApplet app, final float centreX, final float centreY);
}
