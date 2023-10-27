package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Integration.*;
import WizardTD.*;
import org.junit.jupiter.api.*;

import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

public class WinGameTests extends IntegrationTest {
    public final static String CONFIG_PATH = TestResources.FULL_CFG_DIR + "win_game_tests_config.json";

    @Test
    public void fuckingNukeAllTheEnemiesWithAGoddamnCheatCode() throws InterruptedException {
        setUpApp(CONFIG_PATH);
        assertEquals(app.gameData.gameState, GameState.PLAYING);

        app.loop(); // enable ticking
        Thread.sleep(1000); // wait to spawn
        app.gameData.enemies.forEach(e -> GameManager.killEnemy(app.gameData, e)); // kill all enemies
        Thread.sleep(1000); // wait for killed

        // Check back that we've won
        assertEquals(GameState.WON, app.gameData.gameState);
    }

    @Test
    public void winButWithATower() throws InterruptedException {
        setUpApp(CONFIG_PATH);
        assertEquals(app.gameData.gameState, GameState.PLAYING);

        app.loop(); // enable ticking
        app.gameData.board.stream().forEach( // place a metric fuckton of towers
                tile -> IntStream.range(0, 10).forEach( $_ -> GameManager.placeOrUpgradeTower(app.gameData, tile, true, true, true))
        );
        Thread.sleep(1000); // wait to spawn and die

        // Check back that we've won
        assertEquals(GameState.WON, app.gameData.gameState);
    }
}
