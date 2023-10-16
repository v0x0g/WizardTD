package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Projectiles.*;
import WizardTD.Gameplay.Spawners.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

import static WizardTD.GameConfig.*;
import static org.tinylog.Logger.*;

/**
 * Data class that contains all the game state
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class GameData {

    public @NonNull Board board;

    public @NonNull List<@NonNull Enemy> enemies;
    public @NonNull List<@NonNull Projectile> projectiles;

    public @NonNull List<@NonNull Wave> waves;

    public @NonNull WizardHouseTile wizardHouse;

    public @NonNull GameDataConfig config;

}