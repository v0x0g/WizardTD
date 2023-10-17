package WizardTD.Gameplay.Pathfinding.AStar;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Tiles.*;
import com.google.common.collect.*;
import lombok.experimental.*;

import java.util.*;

import static WizardTD.GameConfig.*;

/**
 * Helper class that does pathfinding
 */
@UtilityClass
public class AStarPathfinder {

    /**
     * Scans the board to find all the wizard houses and valid spawn points
     *
     * @param spawnPoints  The destination list to add the wizard houses to
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

    // TODO: Currently this only returns ONE shortest path, not all of them. We might need to fix this
    public List<EnemyPath> findPath(final Grid grid, final Tile startTile, final Tile endTile) {
        final Node startNode = grid.grid[startTile.getPos().getX()][startTile.getPos().getY()];
        final Node targetNode = grid.grid[endTile.getPos().getX()][endTile.getPos().getY()];

        final Heap openSet = new Heap();
        final HashSet<Node> closedSet = new HashSet<>();
        openSet.add(startNode);

        final List<EnemyPath> validPaths = new ArrayList<>();

        while (openSet.count > 0) {
            final Node currentNode = openSet.removeFirst();
            closedSet.add(currentNode);

            // If we are at the target node, we have found the path we want
            // So add to the list and keep searching
            if (currentNode == targetNode) {
                final List<Node> nodes = retracePath(startNode, targetNode);
                validPaths.add(new EnemyPath(nodes.stream().map(node -> node.pos).toArray(TilePos[]::new)));
            }

            for (final Node neighbour : grid.getNeighbours(currentNode)) {
                if (!neighbour.walkable || closedSet.contains(neighbour)) {
                    continue;
                }

                final double newMovementCostToNeighbour = currentNode.gCost + getDistance(currentNode, neighbour);
                if (newMovementCostToNeighbour < neighbour.gCost || !openSet.contains(neighbour)) {
                    neighbour.gCost = newMovementCostToNeighbour;
                    neighbour.hCost = getDistance(neighbour, targetNode);
                    neighbour.parent = currentNode;

                    if (!openSet.contains(neighbour))
                        openSet.add(neighbour);
//                    else {
//                        openSet.UpdateItem(neighbour);
//                    }
                }
            }
        }
        return validPaths;
    }

    List<Node> retracePath(final Node startNode, final Node endNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = endNode;

        while (currentNode != startNode) {
            path.add(currentNode);
            assert currentNode != null;
            currentNode = currentNode.parent;
        }
        path.add(startNode);
        path = Lists.reverse(path);

        return path;
    }

    @SuppressWarnings("MagicNumber")
        // A* magic constants
    int getDistance(final Node nodeA, final Node nodeB) {
        final int dstX = Math.abs(nodeA.pos.getX() - nodeB.pos.getX());
        final int dstY = Math.abs(nodeA.pos.getY() - nodeB.pos.getY());

        if (dstX > dstY) return 14 * dstY + 10 * (dstX - dstY);
        else return 14 * dstX + 10 * (dstY - dstX);
    }
}
