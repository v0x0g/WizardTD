package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Spawners.*;
import lombok.*;

import java.util.*;
import java.util.stream.*;

/**
 * Represents a level that can be played.
 * <p>
 * Note that this is merely the *template*, as opposed to the actual instance being played
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class GameDescriptor {

    public final String name;

    public final Board board;

    public final GameDataConfig config;

    public final List<Wave> waves;

    public GameDescriptor(final GameDescriptor other) {
        this.board = new Board(other.board.tiles);
        this.name = other.name;
        this.config = other.config;
        this.waves = other.waves
                .stream()
                .map(w -> new Wave(w.duration, w.delayBeforeWave, w.spawnRateMult,
                                   w.waveNumber,
                                   w.enemyFactories.stream().map(EnemyFactory::new).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}