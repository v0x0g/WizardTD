package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Pathfinding.*;
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
    
    public @NonNull GameState gameState;

    public @NonNull Board board;

    public @NonNull List<Enemy> enemies;
    public @NonNull List<Projectile> projectiles;

    public @NonNull List<Wave> waves;
    public @NonNull List<EnemyPath> enemyPaths;

    public @NonNull GameDataConfig config;
    
    public @NonNull GameSpells spells;

    /**
     * Template used to create this game instance.
     */
    public final GameDescriptor descriptor;

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
     * Multiplier for how much mana is gained per kill, and via mana trickle
     */
    public double manaGainMultiplier = 1.0;
}