package WizardTD;

import WizardTD.Gameplay.Game.*;

public interface Tickable {
    /**
     * Ticks (updates) the object
     *
     * @param game            Instance for the game data
     * @param gameDeltaTime   Delta-time for game-related interactions, such as movement.
     * @param visualDeltaTime Delta-time for visual interactions, such as animations
     */
    void tick(final GameData game, final double gameDeltaTime, final double visualDeltaTime);
}
