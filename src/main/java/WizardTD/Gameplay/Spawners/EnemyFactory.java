package WizardTD.Gameplay.Spawners;

import WizardTD.Gameplay.Enemies.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

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
     * @return Returns the new enemy.
     * 
     * @throws java.util.NoSuchElementException
     * If the factory has no more enemies to spawn (e.g. reached the end of the wave),
     * you should throw a NoSuchElementException to indicate this.
     */
    public abstract @Nullable TEnemy spawnEnemy() throws NoSuchElementException;
}