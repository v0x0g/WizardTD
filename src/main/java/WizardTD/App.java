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
import org.tinylog.*;
import processing.core.*;
import processing.event.*;

import java.lang.reflect.*;
import java.util.*;

public final class App extends PApplet {

    public final UiState uiState;
    public GameData gameData;
    /**
     * The last time at which a frame was rendered
     * <p/>
     * See {@link System#nanoTime()}
     */
    private double lastFrameTime = -1.0;

    public App() throws AppInitException {
        Logger.info("app ctor");

        Logger.info("init game and stuff");

        final GameDescriptor gameDesc = GameLoader.loadGameDescriptor(GameConfig.CONFIG_PATH);
        Logger.debug("gameDesc={}", gameDesc);
        if (gameDesc == null) {
            Logger.warn("did not manage to load game descriptor :(");
            throw new AppInitException("couldn't load game descriptor");
        }
        Logger.debug("got gameDesc");
        this.gameData = GameLoader.createGame(gameDesc);
        Logger.debug("gameData={}", this.gameData);

        Logger.debug("init uiState");
        this.uiState = new UiState();
        UiManager.initUi(this.uiState);

        Logger.debug("done");
    }

    public static void main(final String[] args) {
        final String APP_CLASS_NAME = "WizardTD.App";

        Logger.info("program start; args={}", Arrays.toString(args));

        EventManager.reflectAndSubscribe();
        EventManager.invokeEvent(new Event(EventType.Bootstrap));

        Logger.debug("run PApplet.main({})", APP_CLASS_NAME);
        try {
            PApplet.main(APP_CLASS_NAME);
        } catch (final Exception e) {
            if (e.getCause() instanceof InvocationTargetException && e
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

        Logger.trace("cap framerate: {} fps", GameConfig.TARGET_FPS);
        frameRate((float) GameConfig.TARGET_FPS);
        EventManager.invokeEvent(new Event(EventType.AppSetup, this));

        Logger.info("done setup");
    }

    // ========== INPUT ==========
    // region

    @Override
    protected void handleKeyEvent(final KeyEvent evt) {
        super.handleKeyEvent(evt);

        final KeyCode keyCode = KeyCode.fromInt(evt.getKeyCode());
        final KeyAction keyAction = KeyAction.fromInt(evt.getAction());
        if (evt.getKeyCode() == 0) return; // Processing does this sometimes
        if (keyCode == null || keyAction == null) {
            Loggers.INPUT.warn("didn't recognise input code={}({}) char='{}'", evt.getKeyCode(), keyCode, evt.getKey());
            return;
        }
        UiManager.keyEvent(this, gameData, uiState, new KeyPress(keyCode, evt.isAutoRepeat(), keyAction));
    }

    @Override
    protected void handleMouseEvent(final MouseEvent evt) {
        super.handleMouseEvent(evt);

        final MouseCode mouseCode = MouseCode.fromInt(evt.getButton());
        final MouseAction mouseAction = MouseAction.fromInt(evt.getAction());
        // Cursor enters or exits the app window
        if (mouseCode == null && (mouseAction == MouseAction.EXIT || mouseAction == MouseAction.ENTER)) return;
        if (mouseAction == null || mouseCode == null) {
            Loggers.INPUT.warn(
                    "didn't recognise input action={}({}) button={}({})",
                    evt.getAction(),
                    mouseAction,
                    evt.getButton(),
                    mouseCode
            );
        }
        else {
            UiManager.mouseEvent(
                    this, gameData, uiState,
                    new MousePress(new Vector2(evt.getX(), evt.getY()), mouseCode, evt.getCount(), mouseAction)
            );
        }
    }

    // endregion

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        Loggers.RENDER.debug("enter draw");

        final double NANOS_PER_SECOND = 1000_000_000.0;
        final double lastTick = this.lastFrameTime > 0 ? this.lastFrameTime : System.nanoTime() / NANOS_PER_SECOND;
        final double thisTick = System.nanoTime() / NANOS_PER_SECOND;
        final double deltaTime = thisTick - lastTick;
        this.lastFrameTime = thisTick;

        Loggers.RENDER.trace("background");
        background(Colour.DEEP_PURPLE.asInt());

        final double tickDuration =
                Debug.timeAction(() -> GameManager.tickGameWithSubtick(this, this.gameData, deltaTime));
        final double renderDuration = Debug.timeAction(() -> Renderer.render(this, this.gameData, this.uiState));

        Debug.Stats.Frames.deltaTime = deltaTime;
        Debug.Stats.Frames.fps = 1 / deltaTime;
        Debug.Stats.Frames.avgFps = this.frameRate;
        Debug.Stats.Frames.frameCount++;
        Debug.Stats.Frames.lastTick = lastTick;
        Debug.Stats.Frames.thisTick = thisTick;

        Debug.Stats.Perf.tickTime = tickDuration;
        Debug.Stats.Perf.renderTime = renderDuration;

        Debug.drawPathfindingOverlay(this, gameData);
        Debug.drawHoveredTileOverlay(this, gameData);
        Debug.showTowerUpgradeOverlay(this, gameData);
        Debug.showF3Overlay(this, gameData, uiState);

        Loggers.RENDER.debug("exit draw");
    }

    /**
     * Special exception type for when the app can't be init'ed
     */
    @StandardException
    public static final class AppInitException extends Exception {}

}
