package WizardTD.Gameplay.Spawners;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Enemies.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;

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
    public final List<EnemyFactory> enemyFactories;
    public final long waveNumber;
    /**
     * Internal value used to keep track of how enemies to spawn.
     * This should be incremented each frame by @c deltaTime.
     * It indicates how many available enemies there are to spawn;
     * e.g. a value of 2.31 indicates we have 2 enemy slots available
     */
    private double enemySpawnCounter;
    @Getter
    private double timer;

    public Wave(
            final double duration, final double delayBeforeWave, final double spawnRateMult,
            final long waveNumber,
            final List<EnemyFactory> enemyFactories) {
        this.duration = duration;
        this.delayBeforeWave = delayBeforeWave;
        this.spawnRateMult = spawnRateMult;
        this.enemyFactories = enemyFactories;
        this.waveNumber = waveNumber;
        enemySpawnCounter = 0.0;
        timer = 0.0;
    }

    public void tick(final double deltaTime) {
        this.timer += deltaTime;

        final double t = (this.timer - this.delayBeforeWave);
        if (t > 0) this.enemySpawnCounter += deltaTime * this.spawnRateMult;
    }

    public WaveState getWaveState() {
        if (this.timer < this.delayBeforeWave) return WaveState.PRE_DELAY;
        else if (this.timer > (this.duration + this.delayBeforeWave)) return WaveState.COMPLETE;
        else return WaveState.SPAWNING;
    }

    /// Gets the next enemy for this wave
    public @Nullable Enemy getEnemy() {
        if (this.getWaveState() != WaveState.SPAWNING) return null;
        if (this.enemySpawnCounter < 1) return null;

        while (!enemyFactories.isEmpty()) {
            // Pick a random factory by index, and spawn an enemy from it
            final int randIndex = ThreadLocalRandom.current().nextInt(0, this.enemyFactories.size());
            final EnemyFactory factory = this.enemyFactories.get(randIndex);

            @Nullable Enemy enemy;
            try {
                enemy = factory.spawnEnemy();
            } catch (final NoSuchElementException e) {
                // NoSuchElementException indicates the factory is empty
                // so remove
                this.enemyFactories.remove(randIndex);
                Loggers.GAMEPLAY.trace("remove factory {}", factory);
                enemy = null;
            }

            if (enemy != null) {
                this.enemySpawnCounter -= 1.0;
                return enemy;
            }
        }

        Logger.error("tried to get enemy from wave, but wave had no more factories left. Might be a bug");

        return null;
    }

    public enum WaveState {
        /**
         * Wave is still waiting to spawn enemies
         */
        PRE_DELAY,
        /**
         * Wave is currently spawning enemies
         */
        SPAWNING,
        /**
         * Wave has spawned all the enemies it is going to spawn
         */
        COMPLETE
    }
}