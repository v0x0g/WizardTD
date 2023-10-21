package WizardTD.Gameplay.Tiles;

import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import lombok.*;

import java.util.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class Tile extends Renderable {

    @Getter @NonNull private final TilePos pos = new TilePos(0,0);
    
    /**
     * Parses a char as a Tile
     *
     * @param c The char to parse
     * @return Either a parsed tile, or empty if the char was invalid
     */
    public static Optional<Tile> fromChar(final char c) {
        switch (c) {
            case 'S':
                return Optional.of(new ShrubTile());
            case 'W':
                return Optional.of(new WizardHouseTile());
            case ' ':
                return Optional.of(new GrassTile());
            case 'X':
                return Optional.of(new PathTile());
            default:
                return Optional.empty();
        }
    }

    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.TILE;
    }

    /**
     * Called every tick, you can update the tiles here
     */
    public void tick(final GameData game) {}
}
