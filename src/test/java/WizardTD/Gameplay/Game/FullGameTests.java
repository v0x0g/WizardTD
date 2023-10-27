package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Tiles.*;
import WizardTD.*;
import lombok.experimental.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtensionMethod(Arrays.class)
public class FullGameTests {

    @Test
    public void testGame1() {
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
//        // 5th is a lot of harry potters
//        assertAll(
//                game.board.tiles
//                        .stream()
//                        .skip(4)
//                        .limit(1)
//                        .flatMap(Arrays::stream)
//                        .map(tile -> () -> assertTrue(tile instanceof WizardHouseTile))
//        );
//        // 6th, 7th is blank (therefore grass)
//        assertAll(
//                game.board.tiles
//                        .stream()
//                        .skip(5)
//                        .limit(2)
//                        .flatMap(Arrays::stream)
//                        .map(tile -> () -> assertTrue(tile instanceof GrassTile))
//        );
//        // 8th is actual grass, 9th half 'n half
//        assertAll(
//                game.board.tiles
//                        .stream()
//                        .skip(7)
//                        .limit(2)
//                        .flatMap(Arrays::stream)
//                        .map(tile -> () -> assertTrue(tile instanceof GrassTile))
//        );
//        // 10th: grass/shrub mix
//        assertAll(
//                game.board.tiles
//                        .stream()
//                        .skip(9)
//                        .limit(1)
//                        .flatMap(Arrays::stream)
//                        .map(tile -> () -> assertTrue(tile instanceof GrassTile || tile instanceof ShrubTile))
//        );
//        // 11th: shrubs
//        assertAll(
//                game.board.tiles
//                        .stream()
//                        .skip(10)
//                        .limit(1)
//                        .flatMap(Arrays::stream)
//                        .map(tile -> () -> assertTrue(tile instanceof ShrubTile))
//        );
    }
}