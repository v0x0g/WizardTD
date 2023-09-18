package WizardTD.UI;

import WizardTD.*;

public class GuiConfig {

    /**
     * How many pixels wide the sidebar is
     */
    public static final int SIDEBAR_WIDTH_PX = 120;

    /*
     * How many pixels high the top bar is
     */
    public static final int TOP_BAR_HEIGHT_PX = 40;

    /**
     * How many pixels large each tile is
     */
    public static final int CELL_SIZE_PX = 32;
    /**
     * Total window width, in pixels
     */
    public static final int WINDOW_HEIGHT_PX = GameConfig.BOARD_SIZE_TILES * CELL_SIZE_PX + TOP_BAR_HEIGHT_PX;

    /**
     * Total window height, in pixels
     */
    public static final int WINDOW_WIDTH_PX = CELL_SIZE_PX * GameConfig.BOARD_SIZE_TILES + SIDEBAR_WIDTH_PX;

    public static final double TARGET_FPS = 100000.0f;

}
