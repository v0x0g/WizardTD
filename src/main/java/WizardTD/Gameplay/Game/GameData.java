package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Projectiles.*;
import WizardTD.Gameplay.Spawners.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

/**
 * Data class that contains all the game state
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public final class GameData {

    public @NonNull Board board;

    public @NonNull List<@NonNull Enemy> enemies;
    public @NonNull List<@NonNull Projectile> projectiles;

    public @NonNull List<@NonNull Wave> waves;

    public @NonNull GameDataConfig config;

    /**
     * Flags for controlling the speed of the game
     */
    public boolean
            paused = false,
            fastForward = false;

    /**
     * How much mana the wizard has remaining
     */
    public double mana;
    /**
     * Maximum amount of mana allowed
     */
    public double manaCap;
    /**
     * Mana gain per second
     */
    public double manaTrickle;
}