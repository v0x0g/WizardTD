package WizardTD;

import WizardTD.Event.Event;
import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Input.*;
import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import lombok.experimental.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;
import processing.core.*;
import processing.event.*;

import java.lang.reflect.*;
import java.util.*;

public final class App extends PApplet {

    public final @NonNull GameData gameData;
    public final @NonNull UiState  uiState;

    public App() throws AppInitException {
        Logger.info("app ctor");

        Logger.info("init game and stuff");

        Logger.warn("WARN: DEBUG MODE");
        final GameDescriptor gameDesc = GameManager.loadGameDescriptor();
        Logger.debug("gameDesc={}", gameDesc);
        if (gameDesc == null) {
            Logger.warn("did not manage to load game descriptor :(");
            throw new AppInitException("couldn't load game descriptor");
        }
        Logger.debug("got gameDesc");
        this.gameData = GameManager.createGame(gameDesc);
        Logger.debug("gameData={}", this.gameData);

        Logger.debug("init uiState");
        this.uiState = new UiState();
        UiManager.initUi(this.uiState);

        Logger.debug("done");
    }

    public static void main(final String[] args) {
        final String APP_CLASS_NAME = "WizardTD.App";

        Logger.info("program start; args={}", Arrays.toString(args));

        EventManager.init();
        EventManager.invokeEvent(new Event(EventType.Bootstrap));

        Logger.debug("run PApplet.main({})", APP_CLASS_NAME);
        try {
            PApplet.main(APP_CLASS_NAME);
        } catch (final Exception e) {
            if (e.getCause() instanceof InvocationTargetException
                    && e
                    .getCause()
                    .getCause() instanceof AppInitException)
                Logger.error("couldn't init game: {}", e.getLocalizedMessage());
            else Logger.error(e, "error running game");
        }
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        Logger.info("enter init settings");
        smooth(8); // MSAA?
        size(GuiConfig.Window.WINDOW_WIDTH_PX, GuiConfig.Window.WINDOW_HEIGHT_PX);
        Logger.info("done init settings");
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    @Override
    public void setup() {
        Logger.info("enter setup");

        Logger.trace("cap framerate: {} fps", GuiConfig.Window.TARGET_FPS);
        frameRate((float) GuiConfig.Window.TARGET_FPS);
        EventManager.invokeEvent(new Event(EventType.AppSetup, this));

        Logger.info("done setup");
    }
    
    // ========== INPUT ==========
    // region

    @Override
    protected void handleKeyEvent(final @NonNull KeyEvent evt) {
        final KeyCode keyCode = KeyCode.fromInt(evt.getKeyCode());
        final KeyAction keyAction = KeyAction.fromInt(evt.getAction());
        if(evt.getKeyCode() == 0) return; // Processing does this sometimes
        if (keyCode == null || keyAction == null) {
            Loggers.INPUT.warn("didn't recognise input code={}({}) char='{}'", evt.getKeyCode(), keyCode, evt.getKey());
            return;
        }
        UiManager.keyEvent(this, gameData, uiState, new KeyPress(keyCode, evt.isAutoRepeat(), keyAction));
    }

    @Override
    protected void handleMouseEvent(final @NonNull MouseEvent evt) {
        final MouseCode mouseCode = MouseCode.fromInt(evt.getButton());
        final MouseAction mouseAction = MouseAction.fromInt(evt.getAction());
        if(mouseCode == null && mouseAction == MouseAction.MOVE) return; // Code is null when we move the mouse, ignore
        if (mouseAction == null || mouseCode == null) {
            Loggers.INPUT.warn("didn't recognise input action={}({}) button={}({})", evt.getAction(), mouseAction, evt.getButton(), mouseCode);
            return;
        }
        UiManager.mouseEvent(
                this, gameData, uiState,
                new MousePress(new Vector2(evt.getX(), evt.getY()), mouseCode, evt.getCount(), mouseAction)
        );
    }

    // endregion
    
    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        Loggers.RENDER.debug("enter draw");

        Loggers.RENDER.trace("background");
        background(Colour.DEEP_PURPLE.asInt());
        // TODO: Dirtying logic
        Loggers.RENDER.trace("dirty");
        this.gameData.board
                .stream()
                .forEach(t -> t.boardDirty(this.gameData.board));
        Loggers.RENDER.trace("update ui");
        UiManager.updateUi(this, gameData, uiState);
        Loggers.RENDER.trace("render gameData");
        Renderer.render(this, gameData, uiState);

        Loggers.RENDER.debug("exit draw");
    }

    /**
     * Special exception type for when the app can't be init'ed
     */
    @StandardException
    public static final class AppInitException extends Exception {}

}
