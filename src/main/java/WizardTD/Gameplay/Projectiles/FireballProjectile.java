package WizardTD.Gameplay.Projectiles;

import WizardTD.*;
import WizardTD.Event.*;
import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class FireballProjectile extends Projectile {

    /**
     * How many units (tiles) per frame the fireball moves
     */
    public static final double FIREBALL_SPEED_TILES = 5.0 / GameConfig.TILE_SIZE_PX;
    /**
     * Distance between an enemy and projectile that is considered a 'hit'.
     * Necessary because of floating-point approximation errors
     */
    public static final double HIT_DISTANCE_THRESHOLD = 0.01;

    private static PImage projectileImage;
    /**
     * How much damage the fireball should do upon striking an enemy
     */
    public final double damage;
    public @NonNull Enemy targetEnemy;

    public FireballProjectile(final Vector2 position, final @NonNull Enemy targetEnemy, final double baseDamage) {
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
    public void tick(final @NonNull GameData game, final double gameDeltaTime, final double visualDeltaTime) {
        // Ensure we have a target, else kill the projectile
        // This prioritises a target near to the existing target, or else near the fireball's position
        if (!this.targetEnemy.isAlive) {
            // Range is arbitrary, so I chose tower range
            final Enemy e = GameManager.getNearestEnemy(game, this.targetEnemy.position, game.config.tower.initialTowerRange);
            if (e == null) GameManager.getNearestEnemy(game, this.position, game.config.tower.initialTowerRange);
            if(e == null) {
                GameManager.killProjectile(game,this);
                return;
            }
            // We should never get a dead enemy
            assert e.isAlive;
            this.targetEnemy = e;
        }

        // Move towards target
        final Vector2 targetDir = (Vector2) this.targetEnemy.position.subCopy(this.position).toNormal();
        double enemyDistance = this.position.distance(this.targetEnemy.position);
        final double motionAmount = Math.min(FIREBALL_SPEED_TILES, enemyDistance) * gameDeltaTime; // Don't overcompensate
        final Vector2 motion = (Vector2) targetDir.multiplyCopy(motionAmount);
        this.position.add(motion);

        // Calculate distance again on we've moved, to see if we're close enough to kill
        enemyDistance = this.position.distance(this.targetEnemy.position);
        if (enemyDistance < HIT_DISTANCE_THRESHOLD) {
            GameManager.damageEnemy(game, this.targetEnemy, this.damage);
            GameManager.killProjectile(game, this);
        }
    }

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        Renderer.renderSimpleEnemy(app, projectileImage, this.position);
    }
}
