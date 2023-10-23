package WizardTD.Gameplay.Enemies;

import WizardTD.Event.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
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
        final App app = (PApplet) event.dataObject;
        entityImage = UiManager.loadImage(app, Resources.Enemies.Worm.ONLY_TILE);
    }

    @Override
    public void render(final App app, final GameData gameData, final UiState uiState) {
        Renderer.renderSimpleEnemy(app, entityImage, this.position);
        super.render(app,gameData,uiState);
    }
}
