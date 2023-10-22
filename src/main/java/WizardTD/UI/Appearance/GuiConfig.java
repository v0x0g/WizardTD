package WizardTD.UI.Appearance;

import WizardTD.*;
import lombok.experimental.*;

import static WizardTD.UI.Appearance.GuiConfig.UiPositions.*;

@UtilityClass
public class GuiConfig {
    @UtilityClass
    public class UiPositions {
        /*
         * How many pixels high the top bar is
         */
        public static final int TOP_BAR_HEIGHT_PX = 40;
        public static final int TOP_BAR_X_PX = 0;
        public static final int TOP_BAR_Y_PX = 0;

        /**
         * How many pixels wide the sidebar is
         */
        public static final int SIDEBAR_WIDTH_PX = 120;
        public static final int SIDEBAR_HEIGHT_PX = GameConfig.BOARD_SIZE_TILES * GameConfig.TILE_SIZE_PX;
        public static final int SIDEBAR_X_PX = GameConfig.BOARD_SIZE_TILES * GameConfig.TILE_SIZE_PX;
        public static final int SIDEBAR_Y_PX = TOP_BAR_HEIGHT_PX;

        /**
         * Coordinate for where to draw the board onscreen (top left corner)
         */
        public static final int BOARD_X_PX = 0;
        public static final int BOARD_Y_PX = TOP_BAR_HEIGHT_PX;
    }
    @UtilityClass
    public class Window {
        /**
         * Total window width, in pixels
         */
        public static final int WINDOW_HEIGHT_PX = TOP_BAR_HEIGHT_PX + SIDEBAR_HEIGHT_PX;
        /**
         * Total window height, in pixels
         */
        public static final int WINDOW_WIDTH_PX = SIDEBAR_X_PX + SIDEBAR_WIDTH_PX;
    }
}
