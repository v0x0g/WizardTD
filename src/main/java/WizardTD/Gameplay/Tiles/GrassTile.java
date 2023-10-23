package WizardTD.Gameplay.Tiles;

import WizardTD.*;
import WizardTD.Event.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class GrassTile extends Tile {
    /**
     * Only one tile for grass
     */
    public static @Nullable PImage tileImage = null;

    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(final Event event) {
        final App app = (App) event.dataObject;
        tileImage = UiManager.loadImage(app, Resources.Tiles.Grass.ONLY_TILE);
    }

    @Override
    public void render(final App app, final GameData gameData, final UiState uiState) {
        Renderer.renderSimpleTile(app, tileImage, UiManager.tileToPixelCoords(this));
    }
}
