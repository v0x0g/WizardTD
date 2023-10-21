package WizardTD.Gameplay.Tiles;

import WizardTD.Event.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import processing.core.*;

import java.util.*;

import static WizardTD.Ext.ImageExt.*;
import static WizardTD.GameConfig.*;
import static WizardTD.Gameplay.Tiles.TileSides.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class PathTile extends Tile {
    private static final Hashtable<EnumSet<TileSides>, PImage> pathSidesMap = new Hashtable<>();
    private transient EnumSet<TileSides> connectedPathSides = EnumSet.noneOf(TileSides.class);

    @OnEvent(eventTypes = EventType.AppSetup)
    @SuppressWarnings("unused")
    private static void loadImages(final Event evt) {
        assert evt.dataObject != null;
        final PApplet app = (PApplet) evt.dataObject;

        // Angles to rotate by
        final double NONE = 0;
        final double CW = 90;
        final double FLIP = 180;
        final double CCW = 270;

        final PImage straight = UiManager.loadImage(app, Resources.Tiles.Path.STRAIGHT);
        final PImage corner = UiManager.loadImage(app, Resources.Tiles.Path.CORNER);
        final PImage threeWay = UiManager.loadImage(app, Resources.Tiles.Path.THREE_WAY);
        final PImage fourWay = UiManager.loadImage(app, Resources.Tiles.Path.FOUR_WAY);
        final var map = pathSidesMap; // Shorter lines

        // If the sides only match up/down, we can pretend that they continue in that direction
        // But in this case the sidees would be reversed
        // (e.g. connected only at the top -> positioned at the bottom -> vertical path)

        // Edges of board
        map.put(EnumSet.of(DOWN), rotate(app, straight, CW));
        map.put(EnumSet.of(UP), rotate(app, straight, CCW));
        map.put(EnumSet.of(LEFT), rotate(app, straight, NONE));
        map.put(EnumSet.of(RIGHT), rotate(app, straight, FLIP));
        // Straight connections
        map.put(EnumSet.of(UP, DOWN), rotate(app, straight, CW));
        map.put(EnumSet.of(LEFT, RIGHT), rotate(app, straight, NONE));
        // Corners
        map.put(EnumSet.of(LEFT, UP), rotate(app, corner, CW));
        map.put(EnumSet.of(LEFT, DOWN), rotate(app, corner, NONE));
        map.put(EnumSet.of(UP, RIGHT), rotate(app, corner, FLIP));
        map.put(EnumSet.of(DOWN, RIGHT), rotate(app, corner, CCW));
        // Three Way
        map.put(EnumSet.of(LEFT, UP, RIGHT), rotate(app, threeWay, FLIP));
        map.put(EnumSet.of(LEFT, DOWN, RIGHT), rotate(app, threeWay, NONE));
        map.put(EnumSet.of(UP, RIGHT, DOWN), rotate(app, threeWay, CCW));
        map.put(EnumSet.of(UP, LEFT, DOWN), rotate(app, threeWay, CW));
        // ~~QUADRA KILL!~~
        map.put(EnumSet.of(UP, DOWN, LEFT, RIGHT), rotate(app, fourWay, NONE));
    }

    /**
     * Updates what sides of the path are connected to other path objects
     */
    @SuppressWarnings("LocalCanBeFinal")
    @Override
    public void boardDirty(Board board) {
        // Update what sides are connected.
        boolean left = sideConnected(board, -1, 0);
        boolean right = sideConnected(board, +1, 0);
        boolean up = sideConnected(board, 0, -1);
        boolean down = sideConnected(board, 0, +1);

        EnumSet<TileSides> sides = EnumSet.noneOf(TileSides.class);
        if (left) sides.add(LEFT);
        if (right) sides.add(RIGHT);
        if (up) sides.add(UP);
        if (down) sides.add(DOWN);

        this.connectedPathSides = sides;
    }

    /**
     * Calculates whether a certain side of this path tile should be considered connected
     * 
     * @param board Board data, holds the tiles
     * @param offsetX X offset from this tile
     * @param offsetY
     * @return
     */
    private boolean sideConnected(final Board board, final int offsetX, final int offsetY) {
        final int x = this.getPos().getX() + offsetX;
        final int y = this.getPos().getY() + offsetY;

        return // Left/Right edge
                (x == -1) || (x == BOARD_SIZE_TILES)
                // Top/Bottom Edge
                || (y == -1) || (y == BOARD_SIZE_TILES)
                // Another path tile there
                || board.maybeGetTileGeneric(PathTile.class, x, y) != null
                // Wizard house there
                || board.maybeGetTileGeneric(WizardHouseTile.class, x, y) != null;
    }

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        Renderer.renderSimpleTile(app, pathSidesMap.get(this.connectedPathSides), UiManager.tileToPixelCoords(this));
    }
}
