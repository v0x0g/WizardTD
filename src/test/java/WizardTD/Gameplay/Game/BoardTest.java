package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Tiles.*;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.*;

import static WizardTD.GameConfig.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private static final int NUM_TESTS = 10_000;

    @Test
    public void emptyIsAllInvalid() {
        final Board board = new Board();
        assertTrue(board.stream().allMatch(t -> t instanceof InvalidTile));
    }

    @Test
    public void canGetOrSetTiles() {
        final Board board = new Board();
        final Random rng = ThreadLocalRandom.current();

        for (int i = 0; i < NUM_TESTS; i++) {
            final int x = rng.nextInt(BOARD_SIZE_TILES);
            final int y = rng.nextInt(BOARD_SIZE_TILES);

            final Tile tile;
            switch (rng.nextInt(3)) {
                case 0:
                    tile = new GrassTile();
                    break;
                case 1:
                    tile = new InvalidTile();
                    break;
                case 2:
                    tile = new ShrubTile();
                    break;
                default:
                    tile = fail("random value invalid");
            }
            board.setTile(x, y, tile);
            assertEquals(board.getTile(x, y), tile);
            assertSame(board.getTile(x, y), tile);
        }
    }

    @Test
    public void outOfBounds() {
        final Board board = new Board();
        final Random rng = ThreadLocalRandom.current();

        for (int i = 0; i < NUM_TESTS; i++) {
            final int x = rng.nextInt();
            final int y = rng.nextInt();

            if (x < 0 || y < 0 || x >= BOARD_SIZE_TILES || y >= BOARD_SIZE_TILES) continue;

            assertNull(board.maybeGetTile(x, y));
            assertThrows(IndexOutOfBoundsException.class, () -> board.getTile(x, y));
        }
    }
    
    @Test
    public void getGeneric() {
        final Board board = new Board();
        final Random rng = ThreadLocalRandom.current();

        for (int i = 0; i < NUM_TESTS; i++) {
            final int x = rng.nextInt(BOARD_SIZE_TILES);
            final int y = rng.nextInt(BOARD_SIZE_TILES);

            board.setTile(x, y, new GrassTile());
            assertNotNull(board.maybeGetTileGeneric(GrassTile.class, x,y));
            assertNull(board.maybeGetTileGeneric(PathTile.class, x, y));
        }
    }
}
