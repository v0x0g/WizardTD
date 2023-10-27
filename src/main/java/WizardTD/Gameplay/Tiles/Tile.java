package WizardTD.Gameplay.Tiles;

import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import lombok.*;
import lombok.NonNull;
import org.checkerframework.checker.nullness.qual.*;

@ToString
@EqualsAndHashCode
public abstract class Tile implements Renderable, Tickable {

    @Getter @NonNull private final TilePos pos = new TilePos(0,0);
    
    /**
     * Parses a char as a Tile
     *
     * @param c The char to parse
     * @return Either a parsed tile, or empty if the char was invalid
     */
    public static @Nullable Tile fromChar(final char c) {
        switch (c) {
            case 'S':
                return new ShrubTile();
            case 'W':
                return new WizardHouseTile();
            case ' ':
                return new GrassTile();
            case 'X':
                return new PathTile();
            default:
                return null;
        }
    }

    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.TILE;
    }

    /**
     * Called every tick, you can update the tiles here
     */
    public void tick(final @NonNull GameData game, final double gameDeltaTime, final double visualDeltaTime) {}
}
