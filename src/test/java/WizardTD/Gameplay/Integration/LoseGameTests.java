package WizardTD.Gameplay.Integration;

import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Integration.*;
import WizardTD.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoseGameTests extends IntegrationTest {
    public final static String CONFIG_PATH = TestResources.FULL_CFG_DIR + "lose_game_tests_config.json";

    @Test
    public void yeahBroYouGotSmashedByALevel50000UltraBossGoblin() throws InterruptedException {
        setUpApp(CONFIG_PATH);
        Assertions.assertEquals(app.gameData.gameState, GameState.PLAYING);

        app.loop(); // enable ticking
        Thread.sleep(1000); // wait to spawn, and to die

        // Check back that we've won
        assertEquals(GameState.LOST, app.gameData.gameState);
    }

    @Test
    public void manaGoesDownAsWeLose() throws InterruptedException {
        setUpApp(CONFIG_PATH);
        assertEquals(app.gameData.gameState, GameState.PLAYING);
        
        // Give them a ton of mana
        app.gameData.manaCap = 100000000.0;
        app.gameData.mana = 100000000.0;
        app.loop(); // enable ticking
        
        final int NUM_ITERATIONS = 100;
        double lastMana = app.gameData.mana;
        for(int i = 0; i < NUM_ITERATIONS; i++){
            Thread.sleep((long) (2 * 1000.0 / GameConfig.TARGET_FPS)); // wait 2 frames
            assertTrue(app.gameData.mana <= lastMana); // assert mana has dropped
            lastMana = app.gameData.mana;
        }
    }
}
