package WizardTD.UI;

import WizardTD.*;

public class GuiConfig {

    /**
     * How many pixels wide the sidebar is
     */
    public static final int SIDEBAR_WIDTH_PX = 120;

    /*
     * How many pixels high the topbar is
     */
    public static final int TOPBAR_HEIGHT_PX = 40;

    /**
     * Total window width, in pixels
     */
    public static final int WINDOW_HEIGHT_PX = GameConfig.BOARD_SIZE_TILES * GameConfig.CELL_SIZE_PX + TOPBAR_HEIGHT_PX;

    /**
     * Total window height, in pixels
     */
    public static final int WINDOW_WIDTH_PX = GameConfig.CELL_SIZE_PX * GameConfig.BOARD_SIZE_TILES + SIDEBAR_WIDTH_PX;

    public static final float TARGET_FPS = 60.0f;

}
