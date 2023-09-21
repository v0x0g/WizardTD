package WizardTD.Gameplay.Tiles;

import WizardTD.Event.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class WizardHouse extends Tile {

    private static @Nullable PImage tileImage = null;
    private static @Nullable PImage grassUnderTileImage = null;

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
        grassUnderTileImage = UiManager.loadImage(app, Resources.Tiles.Grass.ONLY_TILE);
    }

    @Override
    public @NonNull RenderOrder getRenderOrder() {
        return RenderOrder.TILE_PRIORITY;
    }

    @Override
    public void render(@NonNull final PApplet app, @NonNull GameData gameData, @NonNull UiState uiState) {
        final Vector2 pos = UiManager.tileToPixelCoords(this);
        // Render an extra grass sprite beneath the house, because house is transparent and doesn't fill tile
        Renderer.renderSimpleTile(app, grassUnderTileImage, pos.x, pos.y);
        Renderer.renderSimpleTile(app, tileImage, pos.x, pos.y);
    }
}