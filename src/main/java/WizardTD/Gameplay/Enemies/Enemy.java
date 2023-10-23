package WizardTD.Gameplay.Enemies;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Rendering.*;
import WizardTD.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

@ToString
@EqualsAndHashCode
public abstract class Enemy implements Tickable, Renderable {
    public boolean isAlive = true;

    /**
     * How much health the enemy has remaining
     */
    public double health;
    /**
     * Maximum amount of health the enemy can have.
     * Only used for the health bar
     */
    public double maxHealth;

    /**
     * Where the enemy is on the map.
     * <p/>
     * This is in TILE coordinates, not pixels
     */
    public Vector2 position;

    /**
     * How many units (tiles) per frame the monster moves
     */
    public double speed;

    /**
     * Multiplier on how much damage the enemy takes
     */
    public double damageMultiplier;

    /**
     * How much mana the wizard will gain upon killing this enemy
     */
    public double manaGainedOnKill;

    public EnemyPath path;
    public double pathProgress;

    protected Enemy(
            final double health, final Vector2 position, final double speed, final double damageMultiplier,
            final double manaGainedOnKill) {
        this.health = health;
        this.maxHealth = health;
        this.position = position;
        this.speed = speed;
        this.damageMultiplier = damageMultiplier;
        this.manaGainedOnKill = manaGainedOnKill;
        // We are setting to null even though it should never be null
        // This is because we assume the enemy is always given a path immediately after being spawned
        //TODO: Maybe don't do null and give enemy the path in the ctor?
        //noinspection AssignmentToNull
        this.path = null;
        this.pathProgress = 0.0;
    }

    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.ENTITY;
    }

    public void tick(final @NonNull GameData game, final double gameDeltaTime, final double visualDeltaTime) {
        // Move along the path
        this.pathProgress += gameDeltaTime * this.speed;
        this.position = this.path.calculatePos(this.pathProgress);
    }

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        this.renderHealthBar(app);
    }

    /**
     * Renders a health bar above the enemy
     */
    public void renderHealthBar(final PApplet app) {
        /// Offset from the enemy's position to render the bar at
        final Vector2 OFFSET_PX = new Vector2(0, -16);
        /// How large the bar should be
        final Vector2 DIMENSIONS_PX = new Vector2(48, 4);

        final Vector2 uiPos = UiManager.tileToPixelCoords(this.position);
        uiPos.add(OFFSET_PX);

        final double healthRatio = this.health / this.maxHealth;

        final Vector2 corner1 = (Vector2) uiPos.subCopy(DIMENSIONS_PX.multiplyCopy(0.5));
        final Vector2 corner2 = (Vector2) uiPos.addCopy(DIMENSIONS_PX.multiplyCopy(0.5));
        final Vector2 midCorner = new Vector2(corner1.x + (DIMENSIONS_PX.x * healthRatio), corner1.y);
        
        app.noStroke();
        app.rectMode(PConstants.CORNERS);
        app.fill(Theme.HEALTH_FULL.asInt());
        app.rect((float) corner1.x, (float) corner1.y, (float) corner2.x, (float) corner2.y);
        app.fill(Theme.HEALTH_MISSING.asInt());
        app.rect((float) midCorner.x, (float) midCorner.y, (float) corner2.x, (float) corner2.y);
    }
}
