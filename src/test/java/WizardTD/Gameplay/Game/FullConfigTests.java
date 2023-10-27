package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Spawners.*;
import WizardTD.Gameplay.Tiles.*;
import WizardTD.*;
import lombok.experimental.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static WizardTD.GameConfig.REFERENCE_FPS;
import static WizardTD.GameConfig.TILE_SIZE_PX;
import static org.junit.jupiter.api.Assertions.*;

@ExtensionMethod(Arrays.class)
public class FullConfigTests {

    @Test
    public void testLevel1() {
        final String cfgPath = TestResources.BASE_DIR + TestResources.FULL_CFG_DIR + "full_config_1.json";

        final GameDescriptor desc = GameLoader.loadGameDescriptor(cfgPath);
        assertNotNull(desc);

        final GameData game = GameLoader.createGame(desc);
        assertNotNull(game);

        // First 4 rows are paths
        assertAll(
                game.board.tiles
                        .stream()
                        .limit(4)
                        .flatMap(Arrays::stream)
                        .map(tile -> () -> assertTrue(tile instanceof PathTile))
        );
        // 5th is a lot of harry potters
        assertAll(
                game.board.tiles
                        .stream()
                        .skip(4)
                        .limit(1)
                        .flatMap(Arrays::stream)
                        .map(tile -> () -> assertTrue(tile instanceof WizardHouseTile))
        );
        // 6th, 7th is blank (therefore grass)
        assertAll(
                game.board.tiles
                        .stream()
                        .skip(5)
                        .limit(2)
                        .flatMap(Arrays::stream)
                        .map(tile -> () -> assertTrue(tile instanceof GrassTile))
        );
        // 8th is actual grass, 9th half 'n half
        assertAll(
                game.board.tiles
                        .stream()
                        .skip(7)
                        .limit(2)
                        .flatMap(Arrays::stream)
                        .map(tile -> () -> assertTrue(tile instanceof GrassTile))
        );
        // 10th: grass/shrub mix
        assertAll(
                game.board.tiles
                        .stream()
                        .skip(9)
                        .limit(1)
                        .flatMap(Arrays::stream)
                        .map(tile -> () -> assertTrue(tile instanceof GrassTile || tile instanceof ShrubTile))
        );
        // 11th: shrubs
        assertAll(
                game.board.tiles
                        .stream()
                        .skip(10)
                        .limit(1)
                        .flatMap(Arrays::stream)
                        .map(tile -> () -> assertTrue(tile instanceof ShrubTile))
        );
        // The rest are empty (so grass)
        assertAll(
                game.board.tiles
                        .stream()
                        .skip(11)
                        .flatMap(Arrays::stream)
                        .map(tile -> () -> assertTrue(tile instanceof GrassTile))
        );

    }

    @Test
    public void testConfig1() {
        final String cfgPath = TestResources.BASE_DIR + TestResources.FULL_CFG_DIR + "full_config_1.json";

        final GameDescriptor desc = GameLoader.loadGameDescriptor(cfgPath);
        assertNotNull(desc);

        final GameData game = GameLoader.createGame(desc);
        assertNotNull(game);

        assertEquals(123.0 / TILE_SIZE_PX, game.config.tower.initialTowerRange);
        assertEquals(1.5, game.config.tower.initialTowerFiringSpeed);
        assertEquals(99,  game.config.tower.initialTowerDamage);
        assertEquals(69,  game.mana);
        assertEquals(2,   game.config.mana.initialManaTrickle);
        assertEquals(100, game.config.tower.towerCost);
        assertEquals(0,   game.config.spell.manaPool.initialCost);
        assertEquals(666, game.config.spell.manaPool.costIncrease);
        assertEquals(5.5,  game.config.spell.manaPool.manaGainMultiplier);
        assertEquals(10, game.config.spell.manaPool.manaCapMultiplier);
    }
    
    @Test
    public void testEnemies1() {
        final String cfgPath = TestResources.BASE_DIR + TestResources.FULL_CFG_DIR + "full_config_1.json";

        final GameDescriptor desc = GameLoader.loadGameDescriptor(cfgPath);
        assertNotNull(desc);

        final GameData game = GameLoader.createGame(desc);
        assertNotNull(game);

        assertEquals(1, game.waves.size());
        final Wave w = game.waves.get(0);
        
        assertEquals(64, w.duration);
        assertEquals(32, w.delayBeforeWave);
        assertEquals(1, w.waveNumber);
        assertEquals(1, w.enemyFactories.size());
        
        final EnemyFactory fack = w.enemyFactories.get(0);
        assertEquals(250,fack.health);
        assertEquals(0.5 * REFERENCE_FPS / TILE_SIZE_PX, fack.speed);
        assertEquals(0.4,fack.damageMultiplier);
        assertEquals(1000,fack.manaGainedOnKill);
        assertEquals(BigInteger.valueOf(2500), fack.maxQuantity);
        assertEquals(GremlinEnemy.class, fack.spawnEnemy().getClass());
    }
}