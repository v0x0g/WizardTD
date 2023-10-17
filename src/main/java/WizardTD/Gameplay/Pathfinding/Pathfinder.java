package WizardTD.Gameplay.Pathfinding;

import WizardTD.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.experimental.*;

import java.util.*;
import java.util.stream.*;

import static WizardTD.GameConfig.BOARD_SIZE_TILES;

/**
 * Helper class that does pathfinding
 */
@UtilityClass
public class Pathfinder {

    /**
     * Scans the board to find all the wizard houses and valid spawn points
     */
    public void scanBoard(final Board board, List<Tile> wizardHouses, List<Tile> spawnPoints) {
        board.stream()
             .filter(tile -> tile instanceof WizardHouseTile)
             .forEach(wizardHouses::add);

        board.stream()
             .filter(t -> (t.getPos().getX() == 0)
                          || (t.getPos().getY() == 0)
                          || (t.getPos().getX() == BOARD_SIZE_TILES)
                          || (t.getPos().getY() == BOARD_SIZE_TILES))
             .filter(tile -> tile instanceof PathTile)
             .forEach(spawnPoints::add);

    }
}
