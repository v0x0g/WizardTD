package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Spawners.*;
import lombok.*;

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

    public final String name;

    public final Board board;

    public final GameDataConfig config;

    public final List<Wave> waves;

}