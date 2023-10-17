//package WizardTD.Gameplay.Pathfinding.BRDFS;
//
//import WizardTD.Gameplay.Game.*;
//import WizardTD.Gameplay.Pathfinding.*;
//import WizardTD.Gameplay.Tiles.*;
//import lombok.*;
//import lombok.experimental.*;
//import org.checkerframework.checker.nullness.qual.*;
//
//import java.util.*;
//
//import static WizardTD.GameConfig.BOARD_SIZE_TILES;
//import static java.lang.Math.*;
//
//@UtilityClass
//public class TestPathfinder {
//
//    /**
//     * Returns a list of all the adjacent edges to a given vertex
//     */
//    List<Vertex> adjacentEdges(final Board board, final Vertex vertex) {
//        final List<Vertex> list = new ArrayList<>();
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <= 1; j++) {
//                final Tile tile = board.maybeGetTile(vertex.tile.getPos().getX() + i, vertex.tile.getPos().getY() + j);
//                // Don't connect to self, only want sides not corners
//                if (abs(i) + abs(j) != 1) continue;
//                final boolean isValid = tile instanceof PathTile || tile instanceof WizardHouseTile;
//                if (isValid) list.add(new Vertex(tile, vertex));
//            }
//        }
//        return list;
//    }
//
//    public @Nullable List<EnemyPath> findPaths(final Board board, final TilePos startPos, final TilePos endPos) {
//        List<EnemyPath> solutions = new ArrayList<>();
//    }
//
//    /**
//     * Structure for a node in a BFS/DFS Graph.
//     * Holds a tile and a parent node
//     */
//    @ToString
//    @EqualsAndHashCode(exclude = "parent")
//    @AllArgsConstructor
//    public static class Vertex {
//        public Tile tile;
//        public Vertex parent;
//    }
//}
