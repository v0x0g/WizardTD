package WizardTD.Gameplay.Tiles;

import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class Tile extends Renderable {
    /**
     * The tile coordinates for this tile
     */
    @Getter
    @Setter
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

    @Override
    public @NonNull RenderOrder getRenderOrder() {
        return RenderOrder.TILE;
    }

    /**
     * Method to be called whenever something changes on the board,
     * and the tile should be updated to reflect this
     */
    public void boardDirty(@NonNull final Board board) {}
}
