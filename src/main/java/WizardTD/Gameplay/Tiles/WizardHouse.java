package WizardTD.Gameplay.Tiles;

import WizardTD.Event.*;
import WizardTD.UI.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = false)
public final class WizardHouse extends Tile {

    public static @Nullable PImage tileImage = null;
    /**
     * How much mana the wizard has remaining
     */
    public double mana;
    public double manaCap;
    public double manaTrickle;


    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(@NonNull final Event event) {
        final @NonNull PApplet app = (PApplet) event.dataObject;
        tileImage = UiManager.loadImage(app, Resources.Tiles.WizardHouse.ONLY_TILE);
    }

    @Override
    public void render(@NonNull final PApplet app, final float centreX, final float centreY) {
        UiManager.renderSimpleTile(app, tileImage, centreX, centreY);
    }
}