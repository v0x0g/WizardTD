package WizardTD.Gameplay.Enemies;

import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import processing.core.*;

import java.util.*;
import java.util.stream.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class GremlinEnemy extends Enemy {
    public static final double DEATH_ANIM_DURATION = 4 / 60.0 /* 4 frames @ 60 FPS */;
    // TODO: Dying Enemy
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
        final PApplet app = (PApplet) event.dataObject;
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
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        Renderer.renderSimpleEnemy(app, entityImage, this.position);
    }

}
