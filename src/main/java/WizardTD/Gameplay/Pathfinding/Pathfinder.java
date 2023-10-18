package WizardTD.Gameplay.Pathfinding;

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
    List<Vertex> adjacentEdges(final Board board, final Vertex vertex, final Tile endNode) {
        final List<Vertex> list = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final Tile tile = board.maybeGetTile(vertex.tile.getPos().getX() + i, vertex.tile.getPos().getY() + j);
                // Don't connect to self, only want sides not corners
                if (abs(i) + abs(j) != 1) continue;
                // Paths and the target Wizard Houses are considered valid connected tiles
                // We take in a reference to the end node so that we don't try and path-find
                // through one wizard house, towards another
                final boolean isValid = tile instanceof PathTile || tile == endNode;
                if (isValid) list.add(new Vertex(tile, vertex, vertex.depth + 1));
            }
        }
        return list;
    }

    public List<EnemyPath> findPaths(final Board board) {
        // Find all the spawn points and wizard houses
        final List<Tile> wizardHouses =
                board.stream()
                     .filter(tile -> tile instanceof WizardHouseTile)
                     .collect(Collectors.toList());

        final List<Tile> spawnPoints =
                board.stream()
                     // Filter by checking they are on the edge
                     .filter(t -> (t.getPos().getX() == 0)
                                  || (t.getPos().getY() == 0)
                                  || (t.getPos().getX() == BOARD_SIZE_TILES)
                                  || (t.getPos().getY() == BOARD_SIZE_TILES))
                     .filter(tile -> tile instanceof PathTile)
                     .collect(Collectors.toList());

        // List of paths we've found
        final List<EnemyPath> foundPaths = new ArrayList<>();

        // Iterate over the cartesian product of spawn points and wizard houses
        for (final Tile wizardHouse : wizardHouses) {
            for (final Tile spawnPoint : spawnPoints) {

                final TilePos startPos = spawnPoint.getPos();
                final TilePos endPos = wizardHouse.getPos();

                final Queue<Vertex> queue = new ArrayDeque<>();
                final Set<Vertex> explored = new HashSet<>();

                // We use this to keep track of the depth at which our end node was found
                // Since it's BFS, the first depth we hit our end node at is going to be the shortest depth
                // We keep track of this depth here
                int foundDepth = Integer.MAX_VALUE;

                // Start with initial root node
                queue.add(new Vertex(board.getTile(startPos.getX(), startPos.getY()), null, 0));

                while (!queue.isEmpty()) {
                    final Vertex v = queue.remove();
                    explored.add(v);

                    // If we're at a node that is deeper than our depth
                    if (v.depth > foundDepth) continue;

                    // If we have found the target end node
                    if (v.tile.getPos().equals(endPos)) {
                        foundDepth = v.depth;
                        // Build up the path by going backwards up the tree
                        final List<TilePos> path = new ArrayList<>();
                        Vertex x = v;
                        while (x != null) {
                            path.add(x.tile.getPos());
                            x = x.parent;
                        }
                        // Since we initially added the deepest node (the wizard house),
                        // and we finished on the root node (spawn tile)
                        // we also need to reverse the list
                        foundPaths.add(
                                // RANT: Why the hell do I need to allocate a zero-sized array
                                // Just so that the type is recognised
                                // What the **** is wrong with this language
                                new EnemyPath(Lists.reverse(path).toArray(new TilePos[0]))
                        );
                    }
                    // Explore any adjacent vertices too
                    adjacentEdges(board, v, wizardHouse)
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
         * The corresponding tile for this vertex
         */
        public Tile tile;
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
