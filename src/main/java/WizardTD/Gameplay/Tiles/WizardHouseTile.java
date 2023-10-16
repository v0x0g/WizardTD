package WizardTD.Gameplay.Tiles;

import WizardTD.Event.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class WizardHouseTile extends Tile {

    private static @Nullable PImage tileImage = null;
    private static @Nullable PImage grassUnderTileImage = null;

    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(final Event event) {
        final PApplet app = (PApplet) event.dataObject;
        tileImage = UiManager.loadImage(app, Resources.Tiles.WizardHouse.ONLY_TILE);
        grassUnderTileImage = UiManager.loadImage(app, Resources.Tiles.Grass.ONLY_TILE);
    }

    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.TILE_PRIORITY;
    }

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        final Vector2 pos = UiManager.tileToPixelCoords(this);
        // Render an extra grass sprite beneath the house, because house is transparent and doesn't fill tile
        Renderer.renderSimpleTile(app, grassUnderTileImage, pos);
        Renderer.renderSimpleTile(app, tileImage, pos);
    }
}