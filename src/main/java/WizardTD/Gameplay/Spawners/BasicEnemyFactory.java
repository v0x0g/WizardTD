package WizardTD.Gameplay.Spawners;

import WizardTD.Gameplay.Enemies.*;
import lombok.*;

@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public abstract class BasicEnemyFactory<TEnemy extends Enemy> extends EnemyFactory<TEnemy> {

    /**
     * How much health the enemy has remaining
     */
    public final long health;

    /**
     * How many units (tiles) per frame the monster moves
     */
    public final double speed;

    /**
     * Multiplier on how much damage the enemy takes
     */
    public final double damageMultiplier;

    /**
     * How much mana the wizard will gain upon killing this enemy
     */
    public final long manaGainedOnKill;

}
