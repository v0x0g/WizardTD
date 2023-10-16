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
public final class GrassTile extends Tile {
    /**
     * Only one tile for grass
     */
    public static @Nullable PImage tileImage = null;

    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(@NonNull final Event event) {
        final @NonNull PApplet app = (PApplet) event.dataObject;
        tileImage = UiManager.loadImage(app, Resources.Tiles.Grass.ONLY_TILE);
    }

    @Override
    public void render(@NonNull final PApplet app, final @NonNull GameData gameData, final @NonNull UiState uiState) {
        Renderer.renderSimpleTile(app, tileImage, UiManager.tileToPixelCoords(this));
    }
}
