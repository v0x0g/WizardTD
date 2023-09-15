package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Spawners.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

/**
 * Represents a level that can be played.
 * <p>
 * Note that this is merely the *template*, as opposed to the actual instance being played
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class GameDescriptor {

    public final @NonNull String name;

    public final @NonNull Board board;

    public final @NonNull GameDataConfig config;

    public final @NonNull List<@NonNull Wave> waves;

}