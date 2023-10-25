package WizardTD.Gameplay.Spawners;

import WizardTD.Delegates.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Enemies.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.*;

import java.math.*;
import java.util.*;

/**
 * Factory class that creates enemies of a specific type
 */
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class EnemyFactory {
    /**
     * How much health the enemy has remaining
     */
    public final double health;

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
    public final double manaGainedOnKill;

    /// Maximum number of enemies that can be spawned by this factory.
    /// If @c null then there is no cap on the enemies
    public final @Nullable BigInteger maxQuantity;
    public final Func1<Enemy, EnemyFactory> spawn;
    /// How many enemies have been spawned so far
    private BigInteger quantitySpawned = BigInteger.valueOf(0L);

    public Enemy spawnEnemy() {
        // Spawn cap
        if (this.maxQuantity != null && this.maxQuantity.compareTo(this.quantitySpawned) <= 0) {
            Loggers.GAMEPLAY.trace("reached max capacity: {}", this);
            throw new NoSuchElementException("spawn cap reached");
        }
        quantitySpawned = quantitySpawned.add(BigInteger.valueOf(1));
        // Delegate to function
        return spawn.invoke(this);
    }
}