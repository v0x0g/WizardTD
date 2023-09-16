package WizardTD.Gameplay.Tiles;

import WizardTD.Event.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import java.util.*;

import static WizardTD.Ext.ImageExt.*;
import static WizardTD.Gameplay.Tiles.TileSides.*;

@ToString
@EqualsAndHashCode(callSuper = false)
public final class Path extends Tile {
    public static @Nullable PImage tileImage = null;
    private static @NonNull Hashtable<EnumSet<TileSides>, PImage> pathSidesMap = new Hashtable<>();
    private transient @NonNull EnumSet<TileSides> connectedPathSides = EnumSet.noneOf(TileSides.class);

    @SuppressWarnings("DuplicatedCode")
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(@NonNull final Event evt) {
        assert evt.dataObject != null;
        @NonNull final PApplet app = (PApplet) evt.dataObject;

        // Angles to rotate by
        final double NONE = 0;
        final double CW = 90;
        final double FLIP = 180;
        final double CCW = 270;

        PImage straight = UiManager.loadImage(app, Resources.Tiles.Path.STRAIGHT);
        PImage corner = UiManager.loadImage(app, Resources.Tiles.Path.CORNER);
        PImage threeWay = UiManager.loadImage(app, Resources.Tiles.Path.THREE_WAY);
        PImage fourWay = UiManager.loadImage(app, Resources.Tiles.Path.FOUR_WAY);
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
        map.put(EnumSet.of(DOWN, UP), rotate(app, straight, CW));
        map.put(EnumSet.of(LEFT, RIGHT), rotate(app, straight, NONE));
        map.put(EnumSet.of(RIGHT, LEFT), rotate(app, straight, NONE));
        // Corners
        map.put(EnumSet.of(LEFT, UP), rotate(app, corner, CW));
        map.put(EnumSet.of(UP, LEFT), rotate(app, corner, CW));
        map.put(EnumSet.of(LEFT, DOWN), rotate(app, corner, NONE));
        map.put(EnumSet.of(DOWN, LEFT), rotate(app, corner, NONE));
        map.put(EnumSet.of(UP, RIGHT), rotate(app, corner, FLIP));
        map.put(EnumSet.of(RIGHT, UP), rotate(app, corner, FLIP));
        map.put(EnumSet.of(DOWN, RIGHT), rotate(app, corner, CCW));
        map.put(EnumSet.of(RIGHT, DOWN), rotate(app, corner, CCW));
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
    public void boardDirty(@NonNull Board board) {
        // Update what sides are connected.
        val left = board.<Path>maybeGetTileGeneric(this.getPosX() - 1, this.getPosY()).isPresent();
        val right = board.<Path>maybeGetTileGeneric(this.getPosX() + 1, this.getPosY()).isPresent();
        val up = board.<Path>maybeGetTileGeneric(this.getPosX(), this.getPosY() - 1).isPresent();
        val down = board.<Path>maybeGetTileGeneric(this.getPosX(), this.getPosY() + 1).isPresent();

        EnumSet<TileSides> sides = EnumSet.noneOf(TileSides.class);
        if (left) sides.add(LEFT);
        if (right) sides.add(RIGHT);
        if (up) sides.add(UP);
        if (down) sides.add(DOWN);

        this.connectedPathSides = sides;
    }

    @Override
    public @Nullable PImage getImage() {
        // May return null
        return pathSidesMap.get(this.connectedPathSides);
    }
}
