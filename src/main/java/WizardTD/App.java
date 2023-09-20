package WizardTD;

import WizardTD.Event.Event;
import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
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

    public final @NonNull GameData gameData;
    public final @NonNull UiState  uiState;

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

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(final KeyEvent evt) {
        Loggers.INPUT.debug("key pressed: key='{}' code={}, repeat={}, action={}, flavour={}, millis={}, modifiers={}", evt.getKey(), evt.getKeyCode(), evt.isAutoRepeat(), evt.getAction(), evt.getFlavor(), evt.getMillis(), evt.getModifiers());
        final KeyCode code = KeyCode.tryFromInt(evt.getKeyCode());
        if (code == null) {
            Loggers.INPUT.warn("didn't recognise input {} '{}'", evt.getKeyCode(), evt.getKey());
            return;
        }
        UiManager.keyPressed(this, gameData, uiState, new KeyPress(code, evt.isAutoRepeat(), true));
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased(final KeyEvent evt) {
        Loggers.INPUT.debug("key released: key='{}' code={}, repeat={}, action={}, flavour={}, millis={}, modifiers={}", evt.getKey(), evt.getKeyCode(), evt.isAutoRepeat(), evt.getAction(), evt.getFlavor(), evt.getMillis(), evt.getModifiers());
        final KeyCode code = KeyCode.tryFromInt(evt.getKeyCode());
        if (code == null) {
            Loggers.INPUT.warn("didn't recognise input {} '{}'", evt.getKeyCode(), evt.getKey());
            return;
        }
        UiManager.keyPressed(this, gameData, uiState, new KeyPress(code, evt.isAutoRepeat(), false));
    }

    @Override
    public void mousePressed(final MouseEvent evt) {
        Loggers.INPUT.debug(
                "mouse pressed: {} @ ({},{}) count={}", MouseCode.fromInt(evt.getButton()), evt.getX(), evt.getY(),
                evt.getCount()
        );
    }

    @Override
    public void mouseReleased(final MouseEvent evt) {
        Loggers.INPUT.debug(
                "mouse released: {} @ ({},{}) count={}", MouseCode.fromInt(evt.getButton()), evt.getX(), evt.getY(),
                evt.getCount()
        );
    }

    @Override
    public void mouseDragged(final MouseEvent evt) {
        Logger.debug(
                "mouse drag: {} @ ({},{}) count={}", MouseCode.fromInt(evt.getButton()), evt.getX(), evt.getY(),
                evt.getCount()
        );
    }

    /**
     * Draw all elements in the game by current frame.
     */
    @SneakyThrows
    @Override
    public void draw() {
        Loggers.RENDER.debug("enter draw");

        Loggers.RENDER.trace("background");
        background(Colour.DEEP_PURPLE.code);
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
