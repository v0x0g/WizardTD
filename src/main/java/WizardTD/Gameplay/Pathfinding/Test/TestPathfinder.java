package WizardTD.Gameplay.Pathfinding.BRDFS;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;

import static java.lang.Math.*;

@UtilityClass
public class TestPathfinder {

    /**
     * Returns a list of all the adjacent edges to a given vertex
     */
    List<Vertex> adjacentEdges(final Board board, final Vertex vertex) {
        final List<Vertex> list = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final Tile tile = board.maybeGetTile(vertex.tile.getPos().getX() + i, vertex.tile.getPos().getY() + j);
                // Don't connect to self, only want sides not corners
                if (abs(i) + abs(j) != 1) continue;
                final boolean isValid = tile instanceof PathTile || tile instanceof WizardHouseTile;
                if (isValid) list.add(new Vertex(tile, vertex, vertex.depth + 1));
            }
        }
        return list;
    }

    public List<EnemyPath> findPaths(final Board board, final TilePos startPos, final TilePos endPos) {
        // 
        final Queue<Vertex> queue = new ArrayDeque<>();
        final Set<Vertex> explored = new HashSet<>();
        final Vertex root = new Vertex(board.getTile(startPos.getX(), startPos.getY()), null, 0);

        // We use this to keep track of the depth at which our end node was found
        // Since it's BFS, the first depth we hit our end node at is going to be the shortest depth
        // We keep track of this depth here
        int foundDepth = Integer.MAX_VALUE;
        queue.add(root);

        final List<EnemyPath> solutions = new ArrayList<>();
        while (!queue.isEmpty()) {
            final Vertex v = queue.remove();
            explored.add(v);
            
            // If we're at a node that is deeper than our depth
            if(v.depth > foundDepth) continue;

            // If we have found the target end node
            if (v.tile.getPos().equals(endPos)) {
                foundDepth = v.depth;
                // Build up the path by going backwards up the tree
                final Stack<TilePos> path = new Stack<>();
                Vertex x = v;
                while (x.parent != null) {
                    path.push(x.tile.getPos());
                    x = x.parent;
                }
                solutions.add(
                        // Why the hell do I need to allocate a zero-sized array
                        // Just so that the type is recognised
                        // What the **** is wrong with this language
                        new EnemyPath(path.toArray(new TilePos[0]))
                );
            }
            // Explore any adjacent vertices
            adjacentEdges(board, v)
                    .forEach(w -> {
                        if (!explored.contains(w)) {
                            queue.add(w);
                        }
                    });
        }
        return solutions;
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
        @EqualsAndHashCode.Exclude public Vertex parent;
        /**
         * How deep in the tree we are.
         */
        @EqualsAndHashCode.Exclude public int depth;

//        public boolean equals(final Object o) {
//            if (o == this) return true;
//            if (!(o instanceof Vertex)) return false;
//            final Vertex other = (Vertex) o;
//            if (!Objects.equals(this.tile, other.tile)) return false;
//            if(this.parent != other.parent) return false;
////            return this.depth == other.depth;
//            return true;
//        }
//
//        public int hashCode() {
//            final int PRIME = 59;
//            int result = 1;
//            final Object $tile = this.tile;
//            result = result * PRIME + ($tile == null ? 43 : $tile.hashCode());
////            result = result * PRIME + this.depth;
//            return result;
//        }
    }
}
