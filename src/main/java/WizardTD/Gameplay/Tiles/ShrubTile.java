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
public final class ShrubTile extends Tile {
    public static @Nullable PImage tileImage = null;

    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(final Event event) {
        final PApplet app = (PApplet) event.dataObject;
        tileImage = UiManager.loadImage(app, Resources.Tiles.Shrub.ONLY_TILE);
    }

    @Override
    public void render(final PApplet app, GameData gameData, UiState uiState) {
        Renderer.renderSimpleTile(app, tileImage, UiManager.tileToPixelCoords(this));
    }
}
