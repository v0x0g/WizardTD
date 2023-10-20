package WizardTD.Gameplay.Pathfinding;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Tiles.*;
import com.google.common.collect.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;
import java.util.stream.*;

import static WizardTD.GameConfig.*;
import static java.lang.Math.*;

/**
 * Helper class for pathfinding stuff
 */
@UtilityClass
public class Pathfinder {

    /**
     * Returns a list of all the adjacent edges to a given vertex
     */
    List<Vertex> adjacentEdges(final Board board, final Vertex vertex, final TilePos endNodePos) {
        final List<Vertex> list = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final Tile tile = board.maybeGetTile(vertex.pos.getX() + i, vertex.pos.getY() + j);
                // Don't connect to self, only want sides not corners
                if (abs(i) + abs(j) != 1) continue;
                // Paths and the target Wizard Houses are considered valid connected tiles
                // We take in a reference to the end node so that we don't try and path-find
                // through one wizard house, towards another
                final boolean isValid = tile != null && (tile instanceof PathTile || tile.getPos() == endNodePos);
                if (isValid) list.add(new Vertex(tile.getPos(), vertex, vertex.depth + 1));
            }
        }
        return list;
    }

    public List<EnemyPath> findPaths(final Board board) {
        // Find all the spawn points and wizard houses
        final List<TilePos> wizardHouses =
                board.stream()
                     .filter(WizardHouseTile.class::isInstance)
                     .map(Tile::getPos)
                     .collect(Collectors.toList());

        Loggers.GAMEPLAY.debug(
                "wizard houses: {}",
                wizardHouses.stream().map(TilePos::toString).collect(Collectors.joining(", "))
        );

        // What we actually do here is find the square/ring of tiles that are just
        // outside the board. These are considered our spawn points,
        // since it makes the monsters walk from the outer edge of the map
        final List<TilePos> spawnPoints = new ArrayList<>();
        for (int i = -1; i <= BOARD_SIZE_TILES; i++) {
            for (int j = -1; j <= BOARD_SIZE_TILES; j++) {
                // If we are on a tile that is on the 'edge of the edge'
                if (i == -1 || i == BOARD_SIZE_TILES
                    || j == -1 || j == BOARD_SIZE_TILES) {
                    spawnPoints.add(new TilePos(i,j));
                }
            }
        }

        Loggers.GAMEPLAY.debug(
                "spawn points: {}",
                spawnPoints.stream().map(TilePos::toString).collect(Collectors.joining(", "))
        );


        // List of paths we've found
        final List<EnemyPath> foundPaths = new ArrayList<>();

        // Iterate over the cartesian product of spawn points and wizard houses
        for (final TilePos startPos : wizardHouses) {
            for (final TilePos endPos : spawnPoints) {

                final Queue<Vertex> queue = new ArrayDeque<>();
                final Set<Vertex> explored = new HashSet<>();

                // We use this to keep track of the depth at which our end node was found
                // Since it's BFS, the first depth we hit our end node at is going to be the shortest depth
                // We keep track of this depth here
                int foundDepth = Integer.MAX_VALUE;

                // Start with initial root node
                queue.add(new Vertex(startPos, null, 0));

                while (!queue.isEmpty()) {
                    final Vertex v = queue.remove();
                    explored.add(v);

                    // If we're at a node that is deeper than our depth
                    if (v.depth > foundDepth) continue;

                    // If we have found the target end node
                    if (v.pos.equals(endPos)) {
                        foundDepth = v.depth;
                        // Build up the path by going backwards up the tree
                        final List<TilePos> path = new ArrayList<>();
                        Vertex x = v;
                        while (x != null) {
                            path.add(x.pos);
                            x = x.parent;
                        }

                        // RANT: Why the hell do I need to allocate a zero-sized array
                        // Just so that the type is recognised
                        // What the **** is wrong with this language
                        final EnemyPath enemyPath = new EnemyPath(Lists.reverse(path).toArray(new TilePos[0]));
                        Loggers.GAMEPLAY.debug("found enemy path {}", enemyPath);

                        // Since we initially added the deepest node (the wizard house),
                        // and we finished on the root node (spawn tile)
                        // we also need to reverse the list
                        foundPaths.add(enemyPath);
                    }

                    // Explore any adjacent vertices too
                    adjacentEdges(board, v, endPos)
                            .stream()
                            .filter(w -> !explored.contains(w))
                            .forEach(queue::add);
                }
            }
        }

        return foundPaths;
    }

    /**
     * Structure for a node in a BFS/DFS Graph.
     * Holds a tile and a parent node
     */
    @ToString
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Vertex {
        /**
         * The corresponding tile position for this vertex
         */
        public TilePos pos;
        /**
         * The parent vertex for this vertex.
         * May be null if this a root vertex
         */
        @EqualsAndHashCode.Exclude
        public Vertex parent;
        /**
         * How deep in the tree we are.
         */
        @EqualsAndHashCode.Exclude
        public int depth;
    }
}
