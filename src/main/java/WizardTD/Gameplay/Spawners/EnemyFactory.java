package WizardTD.Gameplay.Spawners;

import WizardTD.Gameplay.Enemies.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Factory class that creates enemies of a specific type
 *
 * @param <TEnemy>
 */
@ToString
public abstract class EnemyFactory<TEnemy extends Enemy> {

    /**
     * Spawns a new enemy of the given type
     *
     * @return Returns the new enemy
     */
    public abstract @NonNull TEnemy spawnEnemy();

}
