package WizardTD;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Class that configures the program.
 * <p>
 * All of these are compile-time constants
 */
@ToString
@EqualsAndHashCode
public class GameConfig {

    /**
     * How many tiles large the board is (each dimension)
     */
    public static final int BOARD_SIZE_TILES = 20;

    public static final String CONFIG_PATH = "config.json";
    /**
     * How fast the game should go when fast-forwarded
     */
    public static final double FAST_FORWARD_SPEED = 2.0;
    /**
     * Time threshold for enabling subticks.
     * <p> 
     * If {@code deltaTime > SUB_TICK_THRESHOLD}, then the tick will be split into multiple sub-ticks,
     * each at most SUB_TICK_THRESHOLD long
     */
    public static final double SUB_TICK_THRESHOLD = 0.05 /*50 ms*/;

}
