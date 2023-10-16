package WizardTD.UI.Appearance;

import WizardTD.*;
import lombok.experimental.*;

@UtilityClass
public class GuiConfig {
    /**
     * How many pixels large each tile is
     */
    public static final int CELL_SIZE_PX = 32;
    @UtilityClass
    public class UiPositions {
        /**
         * How many pixels wide the sidebar is
         */
        public static final int SIDEBAR_WIDTH_PX = 120;
        /*
         * How many pixels high the top bar is
         */
        public static final int TOP_BAR_HEIGHT_PX = 40;

        /**
         * Coordinate for where to draw the board onscreen (top left corner)
         */
        public static final int BOARD_POS_X = 0, BOARD_POS_Y = UiPositions.TOP_BAR_HEIGHT_PX;
    }
    @UtilityClass
    public class Window {

        /**
         * Total window width, in pixels
         */
        public static final int WINDOW_HEIGHT_PX =
                GameConfig.BOARD_SIZE_TILES * CELL_SIZE_PX + UiPositions.TOP_BAR_HEIGHT_PX;
        /**
         * Total window height, in pixels
         */
        public static final int WINDOW_WIDTH_PX =
                CELL_SIZE_PX * GameConfig.BOARD_SIZE_TILES + UiPositions.SIDEBAR_WIDTH_PX;
        public static final double TARGET_FPS = 60.0f;
    }

}
