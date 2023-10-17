package WizardTD.Gameplay.Pathfinding.FloydWarshall;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Tiles.*;

import java.lang.reflect.*;
import java.util.*;

import static WizardTD.GameConfig.*;

public class FloydWarshallPathfinder {
//    public List<EnemyPath> findPaths(final Board board, final TilePos startPos, final TilePos endPos) {
//        // See https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
//        // For an explanation
//        
//        final double[][] distances = (double[][]) Array.newInstance(double.class, BOARD_SIZE_TILES, BOARD_SIZE_TILES);
//        // Fill with infinities
//        for (int i = 0; i < BOARD_SIZE_TILES; i++)
//            for (int j = 0; j < BOARD_SIZE_TILES; j++)
//                distances[i][j] = Double.POSITIVE_INFINITY;
//        final Tile[][] prev = (Tile[][]) Array.newInstance(Tile.class, BOARD_SIZE_TILES, BOARD_SIZE_TILES);
//
//        if prev[u][v] = null then
//        return []
//        path <- [v]
//        while u ≠ v
//        v <- prev[u][v]
//        path.prepend(v)
//        return path
//    }
}
