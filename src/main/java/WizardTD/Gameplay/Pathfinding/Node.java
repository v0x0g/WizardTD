package WizardTD.Gameplay.Pathfinding;

import WizardTD.Gameplay.Tiles.*;
import lombok.*;

@ToString
@EqualsAndHashCode
public class Node implements Comparable<Node> {
    /**
     * Is this tile a valid path that can be walked upon (traversed)
     */
    public boolean walkable;
    /**
     * Position of this node in Tiles
     */
    public TilePos pos;
    /**
     * Parent node of this node (since we have a heap/tree structure)
     */
    public Node parent;
    /**
     * Numeric index of this node in the heap
     */
    public int heapIndex;

    /**
     * See an A* tutorial for what this is
     */
    @Getter
    double gCost, hCost;

    /**
     * See an A* tutorial for what this is
     *
     * @return
     */
    public double getFCost() {
        return this.gCost + this.hCost;
    }

    @Override
    public int compareTo(final Node other) {
        int compare = Double.compare(this.getFCost(), other.getFCost());
        if (compare == 0) {
            compare = Double.compare(this.hCost, other.hCost);
        }
        return -compare;
    }
}
