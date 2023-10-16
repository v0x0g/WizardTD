package WizardTD.Gameplay.Enemies;

import WizardTD.Rendering.*;
import lombok.*;
import mikera.vectorz.*;

@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.ENTITY;
    }
}
