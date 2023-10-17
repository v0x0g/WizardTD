package WizardTD.Gameplay.Pathfinding;

import WizardTD.Gameplay.Tiles.*;
import lombok.*;

@ToString
@EqualsAndHashCode
public class Node implements Comparable<Node> {
    public boolean walkable;
    public TilePos pos;
    public Node parent;
    public int heapIndex;
    @Getter
    double gCost;
    @Getter
    double hCost;

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
