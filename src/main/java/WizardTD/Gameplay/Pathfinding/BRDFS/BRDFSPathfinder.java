package WizardTD.Gameplay.Pathfinding.BRDFS;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

@UtilityClass
public class BRDFSPathfinder {

    List<Vertex> adjacentEdges(final Board board, final Vertex vertex) {
        final List<Vertex> list = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final Tile tile = board.maybeGetTile(vertex.tile.getPos().getX() + i, vertex.tile.getPos().getY() + j);
                if (i == 0 && j == 0) continue; // Don't connect to self
                final boolean isValid = tile instanceof PathTile || tile instanceof WizardHouseTile;
                if (isValid) list.add(new Vertex(tile, vertex));
            }
        }
        return list;
    }

    public @Nullable List<EnemyPath> findPaths(final Board board, final TilePos startPos, final TilePos endPos) {
        final Queue<Vertex> queue = new ArrayDeque<>();
        final Set<Vertex> explored = new HashSet<>();
        final Vertex root = new Vertex(board.getTile(startPos.getX(), startPos.getY()), null);
        queue.add(root);
//        explored.add(root);
        while (!queue.isEmpty()) {
            final Vertex v = queue.remove();
            // If we have found the target end node
            if (v.tile.getPos().equals(endPos)) {
                // Build up the path by going 
                final Stack<TilePos> path = new Stack<>();
                Vertex x = v;
                while (x.parent != null) {
                    path.push(x.tile.getPos());
                    x = x.parent;
                }
                return Collections.singletonList(
                        // Why the hell do I need to allocate a zero-sized array
                        // Just so that the type is recognised
                        // What the **** is wrong with this language
                        new EnemyPath(path.toArray(new TilePos[0]))
                );
            }
            adjacentEdges(board, v)
                    .forEach(w -> {
                        if (!explored.contains(w)) {
                            explored.add(w);
                            w.parent = v;
                            queue.add(w);
                        }
                    });
//            explored.add(v);
        }
        return null;
    }
    @ToString
    @EqualsAndHashCode(exclude = "parent")
    @AllArgsConstructor
    public static class Vertex {
        public Tile tile;
        public Vertex parent;
    }
}
