package WizardTD.Gameplay.Spawners;

import WizardTD.Delegates.*;
import WizardTD.Gameplay.Enemies.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.*;

import java.math.*;
import java.util.*;

/**
 * Factory class that creates enemies of a specific type
 *
 * @param <TEnemy>
 */
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class EnemyFactory<TEnemy extends Enemy> {
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
    public final Func1<TEnemy, EnemyFactory<TEnemy>> spawn;
    /// How many enemies have been spawned so far
    private BigInteger quantitySpawned = BigInteger.valueOf(0L);

    @Nullable
    public TEnemy spawnEnemy() {
        // Spawn cap
        if (maxQuantity != null && maxQuantity.compareTo(quantitySpawned) < 0) {
            throw new NoSuchElementException("spawn cap reached");
        }
        quantitySpawned = quantitySpawned.add(BigInteger.valueOf(1));
        // Delegate to function
        return spawn.invoke(this);
    }
}