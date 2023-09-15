package WizardTD.Gameplay.Spawners;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

@ToString
@EqualsAndHashCode

public final class Wave {

    public final double duration;

    public final double delayBeforeWave;

    public final @NonNull List<@NonNull EnemyFactory<?>> enemyFactories;

    Wave(final double duration, final double delayBeforeWave, @NonNull final List<@NonNull EnemyFactory<?>> enemyFactories) {
        this.duration = duration;
        this.delayBeforeWave = delayBeforeWave;
        this.enemyFactories = enemyFactories;
    }

}