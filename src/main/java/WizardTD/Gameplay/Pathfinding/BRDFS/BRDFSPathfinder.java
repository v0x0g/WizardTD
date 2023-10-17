package WizardTD.Gameplay.Pathfinding.BRDFS;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

import static java.lang.Math.*;

@UtilityClass
public class BRDFSPathfinder {

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
                if (isValid) list.add(new Vertex(tile, vertex));
            }
        }
        return list;
    }

    public @Nullable List<EnemyPath> findPaths(final Board board, final TilePos startPos, final TilePos endPos) {
        /*
        * ===== DEPTH FIRST SEARCH =====
        */
        final Vertex finalNode;
        
        final Queue<Vertex> queue = new ArrayDeque<>();
        final Set<Vertex> explored = new HashSet<>();
        final Vertex root = new Vertex(board.getTile(startPos.getX(), startPos.getY()), null);
        queue.add(root);
        
        while (!queue.isEmpty()) {
            final Vertex v = queue.remove();
            // If we have found the target end node
            if (v.tile.getPos().equals(endPos)) {
                finalNode = v;
                break;
//                // Build up the path by going 
//                final Stack<TilePos> path = new Stack<>();
//                Vertex x = v;
//                while (x.parent != null) {
//                    path.push(x.tile.getPos());
//                    x = x.parent;
//                }
//                solutions.add(
//                        // Why the hell do I need to allocate a zero-sized array
//                        // Just so that the type is recognised
//                        // What the **** is wrong with this language
//                        new EnemyPath(path.toArray(new TilePos[0]))
//                );
            }
            adjacentEdges(board, v)
                    .forEach(w -> {
                        if (!explored.contains(w)) {
                            explored.add(w);
                            w.parent = v;
                            queue.add(w);
                        }
                    });
            explored.add(v);
        }
        
        
        return ;
    }

    /**
     * Structure for a node in a BFS/DFS Graph.
     * Holds a tile and a parent node
     */
    @ToString
    @EqualsAndHashCode(exclude = "parent")
    @AllArgsConstructor
    public static class Vertex {
        public Tile tile;
        public Vertex parent;
    }
}
