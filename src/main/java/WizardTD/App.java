package WizardTD;

import WizardTD.Event.Event;
import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.tinylog.*;
import processing.core.*;
import processing.event.*;

import java.lang.reflect.*;
import java.util.*;

public final class App extends PApplet {

    private final @NonNull GameData gameData;

    public App() throws AppInitException {
        Logger.info("app ctor");

        Logger.info("init game and stuff");

        Logger.warn("WARN: DEBUG MODE");
        final Optional<GameDescriptor> maybeGameDesc = GameManager.loadGameDescriptor();
        Logger.debug("gameDesc={}", maybeGameDesc);
        if (!maybeGameDesc.isPresent()) {
            Logger.warn("did not manage to load game descriptor :(");
            throw new AppInitException("couldn't load game descriptor");
        }
        final GameDescriptor gameDesc = maybeGameDesc.get();
        Logger.debug("got gameDesc");
        final Optional<GameData> maybeGameData = GameData.fromGameDescriptor(gameDesc);
        if (!maybeGameData.isPresent()) {
            Logger.warn("did not manage to create game :(");
            throw new AppInitException("couldn't create game data");
        }
        this.gameData = maybeGameData.get();
        Logger.debug("got gameData");
        Logger.debug("gameData={}", gameData);

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
            if (e.getCause() instanceof InvocationTargetException && e.getCause().getCause() instanceof AppInitException)
                Logger.error("couldn't init game: {}", e.getLocalizedMessage());
            else Logger.error(e, "error running game");
        } finally {
            Logger.info("farewell");
        }
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        Logger.info("enter init settings");
        smooth(8); // MSAA?
        size(GuiConfig.WINDOW_WIDTH_PX, GuiConfig.WINDOW_HEIGHT_PX, P3D);
        Logger.info("done init settings");
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    @Override
    public void setup() {
        Logger.info("enter setup");

        Logger.trace("cap framerate: {} fps", GuiConfig.TARGET_FPS);
        frameRate((float) GuiConfig.TARGET_FPS);
        EventManager.invokeEvent(new Event(EventType.AppSetup, this));

        Logger.info("done setup");
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(final KeyEvent evt) {
        Logger.debug("key pressed: '{}' ({})", evt.getKey(), evt.getKeyCode());
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased(final KeyEvent evt) {
        Logger.debug("key released: '{}' ({})", evt.getKey(), evt.getKeyCode());
    }

    @Override
    public void mousePressed(final MouseEvent evt) {

        Logger.debug("mouse pressed: {} @ ({},{}) count={}", MouseCode.fromInt(evt.getButton()), evt.getX(), evt.getY(), evt.getCount());
    }

    @Override
    public void mouseReleased(final MouseEvent evt) {
        Logger.debug("mouse released: {} @ ({},{}) count={}", MouseCode.fromInt(evt.getButton()), evt.getX(), evt.getY(), evt.getCount());
    }

    @Override
    public void mouseDragged(final MouseEvent evt) {
        Logger.debug("mouse drag: {} @ ({},{}) count={}", MouseCode.fromInt(evt.getButton()), evt.getX(), evt.getY(), evt.getCount());
    }

    /**
     * Draw all elements in the game by current frame.
     */
    @SneakyThrows
    @Override
    public void draw() {
        Loggers.RENDER.debug("enter draw");
        Loggers.RENDER.trace("background");
        background(Colours.CYAN.code);
        Loggers.RENDER.trace("dirty");
        for (int i = 0; i < GameConfig.BOARD_SIZE_TILES; i++) {
            for (int j = 0; j < GameConfig.BOARD_SIZE_TILES; j++) {
                this.gameData.board.getTile(i,j).boardDirty(this.gameData.board);
            }
        }
        Loggers.RENDER.trace("render gameData");
        Renderer.renderGameData(this, gameData);
        Loggers.RENDER.debug("exit draw");
    }

    /**
     * Special exception type for when the app can't be init'ed
     */
    @StandardException
    public static final class AppInitException extends Exception {}

}
