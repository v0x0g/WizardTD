package WizardTD.Gameplay.Enemies;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Rendering.*;
import lombok.*;
import mikera.vectorz.*;

@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class Enemy extends Renderable {
//TODO: Render
    /**
     * How much health the enemy has remaining
     */
    public double health;

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
    private double pathProgress;

    protected Enemy(
            final double health, final Vector2 position, final double speed, final double damageMultiplier, final double manaGainedOnKill) {
        this.health = health;
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

    public void tick(final GameData game, final double visualDeltaTime, final double gameDeltaTime) {
        this.pathProgress += gameDeltaTime * this.speed;
        this.position = this.path.calculatePos(this.pathProgress);
    }
}
