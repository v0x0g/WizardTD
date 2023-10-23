package WizardTD.Gameplay.Enemies;

import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Projectiles.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import processing.core.*;

import java.util.*;
import java.util.stream.*;

import static WizardTD.GameConfig.REFERENCE_FPS;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GremlinEnemy extends Enemy {
    public static final double DEATH_ANIM_DURATION = 4 * 5 / REFERENCE_FPS /* 5 images @ 4 frames/img @ 60 FPS */;

    public static PImage entityImage;
    public static ImageAnimation deathAnim;

    public GremlinEnemy(
            final double health, final Vector2 position, final double speed, final double damageMultiplier,
            final double manaGainedOnKill) {
        super(health, position, speed, damageMultiplier, manaGainedOnKill);
    }

    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(final Event event) {
        final App app = (PApplet) event.dataObject;
        entityImage = UiManager.loadImage(app, Resources.Enemies.Gremlin.NORMAL);
        deathAnim = new ImageAnimation(
                Arrays.stream(new String[]{
                              Resources.Enemies.Gremlin.DYING_1,
                              Resources.Enemies.Gremlin.DYING_2,
                              Resources.Enemies.Gremlin.DYING_3,
                              Resources.Enemies.Gremlin.DYING_4,
                              Resources.Enemies.Gremlin.DYING_5
                      })
                      .map(path -> UiManager.loadImage(app, path))
                      .collect(Collectors.toList()),
                DEATH_ANIM_DURATION
        );
    }

    @Override
    public void render(final App app, final GameData gameData, final UiState uiState) {
        super.render(app, gameData, uiState);
        Renderer.renderSimpleEnemy(app, entityImage, this.position);
    }

    @Override
    public void onDeath(final GameData game) {
        game.projectiles.add(new DeathAnimationProjectile(this.position, deathAnim));
    }
}
