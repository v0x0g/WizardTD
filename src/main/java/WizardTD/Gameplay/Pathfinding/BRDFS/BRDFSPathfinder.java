package WizardTD.Gameplay.Pathfinding.BRDFS;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.*;

import java.util.*;
import java.util.stream.*;

public class BRDFSPathfinder {
    
    @ToString @EqualsAndHashCode @AllArgsConstructor
    public class Vertex {
            public Tile tile;
            public Vertex parent;
    }
    
    Stream<Vertex> adjacentEdges(final Board board, final Vertex vertex){
        Stream.Builder<Vertex> builder = Stream.builder();
        for (int i = -1; i < 1; i++){
            for(int j = -1; j < 1; j++){
                Tile tile = board.maybeGetTile(vertex.tile.getPos().getX() + i, vertex.tile.getPos().getY() + j);
                if (tile != null) builder.add(new Vertex(tile,vertex));
            }
        }
        return builder.build();
    }
    
    public List<EnemyPath> findPaths(final Board board, final TilePos startPos, final TilePos endPos){
        final Queue<Vertex> queue = new ArrayDeque<>();
        queue.add(new Vertex(board.getTile(startPos.getX(), startPos.getY()), null));
        Vertex v;
        while (null != (v = queue.poll())){
            if (v.tile.getPos().equals(endPos)){
                return v;
            }
            
        }
//        9          for all edges from v to w in G.adjacentEdges(v) do
//            10              if w is not labeled as explored then
//        11                  label w as explored
//        12                  w.parent := v
//        13                  queue.enqueue(w)
    }
}
