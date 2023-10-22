package WizardTD.Gameplay.Projectiles;

import WizardTD.Event.*;
import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

import static WizardTD.UI.Appearance.GuiConfig.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class FireballProjectile extends Projectile {

    /**
     * How many units (tiles) per frame the fireball moves
     */
    public static final double FIREBALL_SPEED = 5.0 / TILE_SIZE_PX;

    private static PImage projectileImage;
    /**
     * How much damage the fireball should do upon striking an enemy
     */
    public final double damage;
    public Enemy targetEnemy;

    public FireballProjectile(final Vector2 position, final Enemy targetEnemy, final double baseDamage) {
        super(position);
        this.damage = baseDamage;
        this.targetEnemy = targetEnemy;
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
    public void tick(final @NonNull GameData game, final double visualDeltaTime, final double gameDeltaTime) {
        // Choose new target if old one dead
        if (!this.targetEnemy.isAlive) {
            this.targetEnemy = GameManager.getNearestEnemy(game, this.position);
        }

        // No enemy, fireball goes out with a bang
        if (this.targetEnemy == null) {
            GameManager.killProjectile(game, this);
            return;
        }
        
        // Calculate direction between this and target
        final Vector2 targetDir = (Vector2) this.targetEnemy.position.subCopy(this.position).toNormal();
        final Vector2 motion = (Vector2) targetDir.multiplyCopy(FIREBALL_SPEED);

        this.position.add(motion);
    }

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        Renderer.renderSimpleEnemy(app, projectileImage, this.position);
    }
}
