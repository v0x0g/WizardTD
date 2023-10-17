package WizardTD.Gameplay.Pathfinding;

import WizardTD.*;
import lombok.*;

import static WizardTD.GameConfig.BOARD_SIZE_TILES;

public class Heap {

    /**
     * Array of items we currently have
     */
    Node[] items;
    /**
     * How many items are currently contained in the heap
     */
    @Getter
    int count;

    public Heap() {
        items = new Node[BOARD_SIZE_TILES * BOARD_SIZE_TILES];
    }

    /**
     * Adds a node to the heap
     */
    public void add(final Node item) {
        item.heapIndex = count;
        items[count] = item;
        SortUp(item);
        count++;
    }

    /**
     * Removes the first node in the heap, and returns that node
     */
    public Node removeFirst() {
        final Node firstItem = items[0];
        count--;
        items[0] = items[count];
        items[0].heapIndex = 0;
        SortDown(items[0]);
        return firstItem;
    }

    /**
     * Updates a node's position in the heap
     * @param item
     */
    public void updateItem(final Node item) {
        SortUp(item);
    }

    /**
     * Checks whether the give node is contained within this heap
     */
    public boolean contains(final Node item) {
        return item.equals(items[item.heapIndex]);
    }

    /**
     * Sorts a node downwards in the tree, as fasr as possible
     */
    void SortDown(final Node item) {
        while (true) {
            final int childIndexLeft = item.heapIndex * 2 + 1;
            final int childIndexRight = item.heapIndex * 2 + 2;
            int swapIndex;

            if (childIndexLeft < count) {
                swapIndex = childIndexLeft;

                if (childIndexRight < count) {
                    if (items[childIndexLeft].compareTo(items[childIndexRight]) < 0) {
                        swapIndex = childIndexRight;
                    }
                }

                if (item.compareTo(items[swapIndex]) < 0) {
                    Swap(item, items[swapIndex]);
                }
                else {
                    return;
                }

            }
            else {
                return;
            }

        }
    }

    /**
     * Sorts a node downwards in the tree, as fasr as possible
     */
    void SortUp(final Node item) {
        int parentIndex = (item.heapIndex - 1) / 2;

        while (true) {
            final Node parentItem = items[parentIndex];
            if (item.compareTo(parentItem) > 0) {
                Swap(item, parentItem);
            }
            else {
                break;
            }

            parentIndex = (item.heapIndex - 1) / 2;
        }
    }

    /**
     * Swaps two nodes in the heap
     */
    void Swap(final Node itemA, final Node itemB) {
        items[itemA.heapIndex] = itemB;
        items[itemB.heapIndex] = itemA;
        final int itemAIndex = itemA.heapIndex;
        itemA.heapIndex = itemB.heapIndex;
        itemB.heapIndex = itemAIndex;
    }
}