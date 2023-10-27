package WizardTD.Gameplay.Game;

import lombok.experimental.*;

import java.util.*;

@UtilityClass
public class GameTestHelper {
    public static GameData createBlankGame() {
        final GameDescriptor descriptor = new GameDescriptor(
                "Test",
                new Board(),
                new GameDataConfig(
                        new GameDataConfig.TowerConfig(
                                0.0, 0.0, 0.0, 0.0
                        ),
                        new GameDataConfig.ManaConfig(
                                0.0, 0.0, 0.0
                        ),
                        new GameDataConfig.SpellConfig(
                                new GameDataConfig.SpellConfig.ManaPool(
                                        0.0, 0.0, 0.0, 0.0
                                )
                        )
                ),
                new ArrayList<>()
        );

        return GameLoader.createGame(descriptor);
    }
}
