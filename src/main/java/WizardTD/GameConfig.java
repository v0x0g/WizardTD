package WizardTD;

import lombok.experimental.*;

/**
 * Class that configures the program.
 * <p>
 * All of these are compile-time constants
 */
@UtilityClass
public class GameConfig {

    /**
     * How many tiles large the board is (each dimension)
     */
    public static final int BOARD_SIZE_TILES = 20;
    public static final String CONFIG_PATH = "config.json";
    /**
     * How fast the game should go when fast-forwarded
     */
    public static final double FAST_FORWARD_SPEED = 20.0;
    /**
     * Time threshold for enabling subticks.
     * <p>
     * If {@code deltaTime > SUB_TICK_THRESHOLD}, then the tick will be split into multiple sub-ticks,
     * each at most SUB_TICK_THRESHOLD long
     */
    public static final double SUB_TICK_THRESHOLD = 50.0 / 1000.0 /*50 ms*/;
    public static final int MAX_SUB_TICKS_PER_FRAME = 100;
    /**
     * How many pixels large each tile is
     */
    public static final int TILE_SIZE_PX = 32;
    /**
     * Reference framerate assumed by the assignment spec
     */
    public static final double REFERENCE_FPS = 60.0;
    public static final double TARGET_FPS = 60.0;
}
