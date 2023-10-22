package WizardTD.Gameplay.Projectiles;

import WizardTD.Event.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class FireballProjectile extends Projectile {

    /**
     * How many units (tiles) per frame the fireball moves
     */
    public static final double FIREBALL_SPEED = 5.0;

    private static PImage projectileImage;
    /**
     * How much damage the fireball should do upon striking an enemy
     */
    public double damage;

    public FireballProjectile(final Vector2 position, final double baseDamage) {
        super(position);
        this.damage = baseDamage;
    }

    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(final Event event) {
        final PApplet app = (PApplet) event.dataObject;
        projectileImage = UiManager.loadImage(app, Resources.Projectiles.Fireball.ONLY_IMAGE);
    }

    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.PROJECTILE;
    }

    
    
    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        final Vector2 pos = UiManager.tileToPixelCoords(this.position);
        Renderer.renderSimpleEnemy(app, projectileImage, pos);
    }
}
