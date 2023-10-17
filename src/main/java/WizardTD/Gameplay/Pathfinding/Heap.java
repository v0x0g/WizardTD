package WizardTD.Gameplay.Pathfinding;

import lombok.*;

public class Heap {

        Node[] items;
        @Getter
        int count;

public Heap(final int maxHeapSize) {
        items = new Node[maxHeapSize];
        }

public void add(final Node item) {
        item.heapIndex = count;
        items[count] = item;
        SortUp(item);
        count++;
        }

public Node removeFirst() {
        final Node firstItem = items[0];
        count--;
        items[0] = items[count];
        items[0].heapIndex = 0;
        SortDown(items[0]);
        return firstItem;
        }

public void updateItem(final Node item) {
        SortUp(item);
        }

public boolean contains(final Node item) {
        return item.equals(items[item.heapIndex]);
        }

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
        Swap (item,items[swapIndex]);
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

        void SortUp(final Node item) {
        int parentIndex = (item.heapIndex-1)/2;

        while (true) {
        final Node parentItem = items[parentIndex];
        if (item.compareTo(parentItem) > 0) {
        Swap (item,parentItem);
        }
        else {
        break;
        }

        parentIndex = (item.heapIndex-1)/2;
        }
        }

        void Swap(final Node itemA, final Node itemB) {
        items[itemA.heapIndex] = itemB;
        items[itemB.heapIndex] = itemA;
        final int itemAIndex = itemA.heapIndex;
        itemA.heapIndex = itemB.heapIndex;
        itemB.heapIndex = itemAIndex;
        }
        }