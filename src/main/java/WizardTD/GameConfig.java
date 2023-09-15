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
     * How many pixels large each tile is
     */
    public static final int CELL_SIZE_PX = 32;

    /**
     * How many tiles large the board is (each dimension)
     */
    public static final int BOARD_SIZE_TILES = 20;

    public static final @NonNull String CONFIG_PATH = "config.json";

}
