package WizardTD;

import WizardTD.Gameplay.Game.*;

public interface Tickable {
    void tick(final GameData game, final double gameDeltaTime, final double visualDeltaTime);
}
