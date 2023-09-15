package WizardTD;

import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.UI.*;
import WizardTD.Gameplay.Game.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;
import processing.core.*;
import processing.event.*;

import java.lang.reflect.*;
import java.util.*;

public final class App extends PApplet {

    private @NonNull GameData gameData;

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
        size(GuiConfig.WINDOW_WIDTH_PX, GuiConfig.WINDOW_HEIGHT_PX);
        Logger.info("done init settings");
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    @Override
    public void setup() {
        Logger.info("enter setup");

        Logger.debug("cap framerate: {} fps", GuiConfig.TARGET_FPS);
        frameRate(GuiConfig.TARGET_FPS);

        // Load images during setup
        // Eg:
//         loadImage("src/main/resources/WizardTD/tower0.png");
        // loadImage("src/main/resources/WizardTD/tower1.png");
        // loadImage("src/main/resources/WizardTD/tower2.png");

        Logger.debug("calling UiManager::loadGraphics()");
        UiManager.loadGraphics(this);

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
    @Override
    public void draw() {
        Logger.trace("enter draw");
        if(millis() > 1_000){
            exit();
        }
        Logger.trace("exit draw");
    }

    @StandardException
    public static final class AppInitException extends Exception {}

}
