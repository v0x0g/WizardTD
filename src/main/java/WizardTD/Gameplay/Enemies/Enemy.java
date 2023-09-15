package WizardTD.Gameplay.Enemies;

import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Enemy {

    /**
     * How much health the enemy has remaining
     */
    public long health;

    /**
     * Where the enemy is on the map
     */
    public @NonNull Vector2 position;

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
    public long manaGainedOnKill;

}
