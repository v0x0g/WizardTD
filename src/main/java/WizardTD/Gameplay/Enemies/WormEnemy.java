package WizardTD.Gameplay.Enemies;

import WizardTD.Event.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = false)
public class WormEnemy extends Enemy {

    public static @Nullable PImage entityImage = null;

    public WormEnemy(
            final double health, final Vector2 position, final double speed, final double damageMultiplier,
            final double manaGainedOnKill) {
        super(health, position, speed, damageMultiplier, manaGainedOnKill);
    }

    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(final Event event) {
        final PApplet app = (PApplet) event.dataObject;
        entityImage = UiManager.loadImage(app, Resources.Enemies.Worm.ONLY_TILE);
    }

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        Renderer.renderSimpleTile(app, entityImage, this.position);
    }

}
