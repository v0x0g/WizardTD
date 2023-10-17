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
     * 
     * @param spawnPoints The destination list to add the wizard houses to
     * @param wizardHouses The destination list to add the spawn points to
     */
    public void scanBoard(final Board board, final List<Tile> wizardHouses, final List<Tile> spawnPoints) {
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

    /**
     * 
     * @return
     */
    public List<EnemyPath> findPath(final Tile startPos, final Tile endPos){
        Heap<>
    }
}
