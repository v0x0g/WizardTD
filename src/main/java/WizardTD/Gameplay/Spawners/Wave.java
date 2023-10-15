package WizardTD.Gameplay.Spawners;

import WizardTD.Gameplay.Enemies.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;
import java.util.concurrent.*;

@ToString
@EqualsAndHashCode
public final class Wave {

    /**
     * How long this wave should last
     */
    public final double duration;
    /**
     * How long to wait before starting the wave
     */
    public final double delayBeforeWave;
    /**
     * Multiplier on how fast monsters should spawn. This should be 1/duration
     */
    public final double spawnRateMult;
    public final @NonNull List<@NonNull EnemyFactory<?>> enemyFactories;

    /**
     * Internal value used to keep track of how enemies to spawn.
     * This should be incremented each frame by @c deltaTime.
     * It indicates how many available enemies there are to spawn;
     * e.g. a value of 2.31 indicates we have 2 enemy slots available
     */
    private double enemySpawnCounter = 0;
    private double timer = 0;

    public Wave(
            final double duration, final double delayBeforeWave, // final double spawnRateMult,
            @NonNull final List<@NonNull EnemyFactory<?>> enemyFactories) {
        this.duration = duration;
        this.delayBeforeWave = delayBeforeWave;
        this.spawnRateMult = 1.0 / duration;
        this.enemyFactories = enemyFactories;
    }
    
    public void tick(double deltaTime){
        this.timer += deltaTime;
        
        final double t = (this.timer - this.delayBeforeWave);
        if (t > 0) this.enemySpawnCounter += deltaTime * this.spawnRateMult;
    }
    
    /// Gets the next enemy for this wave
    public @Nullable Enemy getEnemy() {
        if (this.timer < this.delayBeforeWave) return null; // no enemies to spawn
        else if (this.timer > (this.duration + this.delayBeforeWave)) return null; // Too late

        while (!enemyFactories.isEmpty()) {
            // Pick a random factory by index, and spawn an enemy from it
            final int randIndex = ThreadLocalRandom.current()
                                                   .nextInt(0, this.enemyFactories.size());
            final EnemyFactory<?> factory = this.enemyFactories.get(randIndex);

            @Nullable Enemy enemy;
            try {
                enemy = factory.spawnEnemy();
            }
            catch (NoSuchElementException e) {
                // NoSuchElementException indicates the factory is empty
                // so remove
                this.enemyFactories.remove(randIndex);
                enemy = null;
            }

            if (enemy != null) {
                return enemy;
            }
        }

        return null;
    }
}