package WizardTD.Gameplay.Pathfinding;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.experimental.*;

import java.lang.reflect.*;
import java.util.*;

import static WizardTD.GameConfig.*;

@ExtensionMethod(Arrays.class)
public class Grid {

    public Node[][] grid;

    public Grid(final Board board) {
        this.grid = (Node[][]) Array.newInstance(Node.class, BOARD_SIZE_TILES, BOARD_SIZE_TILES);
        for (int x = 0; x < BOARD_SIZE_TILES; x++) {
            for (int y = 0; y < BOARD_SIZE_TILES; y++) {
                final Tile t = board.getTile(x, y);
                final boolean isPath = t instanceof PathTile || t instanceof WizardHouseTile;
                grid[x][y] = new Node(isPath, new TilePos(x, y), null, 0, 0, 0);
            }
        }
    }

    public List<Node> getNeighbours(final Node node) {
        final List<Node> neighbours = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0)
                    continue;

                final int checkX = node.pos.getX() + x;
                final int checkY = node.pos.getY() + y;

                if (checkX >= 0 && checkX < BOARD_SIZE_TILES && checkY >= 0 && checkY < BOARD_SIZE_TILES) {
                    neighbours.add(grid[checkX][checkY]);
                }
            }
        }

        return neighbours;
    }
}
