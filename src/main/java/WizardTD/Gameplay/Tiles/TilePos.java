package WizardTD.Gameplay.Tiles;

import lombok.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class TilePos {
    /**
     * The tile coordinates for this tile
     */
    @Getter
    @Setter
    private int x, y;
}
