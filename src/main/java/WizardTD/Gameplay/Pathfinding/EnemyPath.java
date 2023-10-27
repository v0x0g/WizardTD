package WizardTD.Gameplay.Pathfinding;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.*;
import mikera.vectorz.*;

/**
 * A data structure that holds
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class EnemyPath {
    public final TilePos[] positions;

    /**
     * Calculates the position along the path, given a certain distance in tiles
     * E.g. If the distance is 0, this would return the initial spawn point ({@code pos[0]}),
     * 0.5 would return halfway between the spawn point and the first tile, etc.
     * <p/>
     * Values {@code < 0 || >positions.length} will be clamped.
     *
     * @param distance How far along the path the calculated position should be.
     * @return A vector position in tile coordinates for where along the path the point it
     */
    public Vector2 calculatePos(double distance) {
        distance = Math.max(0, Math.min(distance, positions.length - 1)); // clamp
        final int idxLow = (int) Math.floor(distance); // Index for the rounded down position
        final int idxHigh = (int) Math.ceil(distance); // Index for the rounded up position
        final double interpolation = distance - idxLow; // How far between (high and low) we are

        final TilePos posLow = positions[idxLow];
        final TilePos posHigh = positions[idxHigh];

        final double x = Numerics.lerp(posLow.getX(), posHigh.getX(), interpolation);
        final double y = Numerics.lerp(posLow.getY(), posHigh.getY(), interpolation);
        return new Vector2(x, y);
    }
}
