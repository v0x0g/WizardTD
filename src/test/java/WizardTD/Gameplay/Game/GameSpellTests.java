package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Spells.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameSpellTests {
    @Test
    public void test(){
        final double INITIAL_COST = 10;
        final double CAP_INCREASE = 2;
        final double GAIN_INCREASE = 1.1;
        final double COST_INCREASE = 10;
        
        final GameData game = GameLoader.createGame(new GameDescriptor(
                "Test",
                new Board(),
                new GameDataConfig(
                        new GameDataConfig.TowerConfig(0.0, 0.0, 0.0, 0.0),
                        new GameDataConfig.ManaConfig(0.0, 1.0, 0.0),
                        new GameDataConfig.SpellConfig(
                                new GameDataConfig.SpellConfig.ManaPool(
                                        INITIAL_COST, COST_INCREASE, GAIN_INCREASE, CAP_INCREASE
                                )
                        )
                ),
                new ArrayList<>()
        ));

        final ManaSpell spell = game.spells.manaSpell;
        assertEquals(spell.currentCost, INITIAL_COST);
        
        // Shouldn't be able to cast with no mana
        game.mana = 0.0;
        spell.cast(game);
        assertEquals(game.mana, 0.0);
        
        // Should cast
        game.mana = INITIAL_COST;
        spell.cast(game);
        assertEquals(game.mana, 0.0);
        assertEquals(game.manaGainMultiplier, GAIN_INCREASE);
        assertEquals(game.manaCap, CAP_INCREASE);

        // Shouldn't be able to cast with no mana
        game.mana = 0.0;
        spell.cast(game);
        assertEquals(game.mana, 0.0);
    }
}
