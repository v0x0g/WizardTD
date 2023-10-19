package WizardTD.UI;

import WizardTD.Delegates.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Spawners.*;
import WizardTD.Gameplay.Tiles.*;
import WizardTD.Input.*;
import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.Elements.*;
import WizardTD.UI.InteractableElements.*;
import com.google.errorprone.annotations.*;
import lombok.experimental.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;
import processing.core.*;

import java.text.*;
import java.util.*;
import java.util.stream.*;

import static WizardTD.GameConfig.*;
import static WizardTD.UI.Appearance.GuiConfig.*;
import static WizardTD.UI.Appearance.GuiConfig.UiPositions.*;

@UtilityClass
public class UiManager {

    /**
     * Loads an image at a given path
     *
     * @param app  Applet instance to load the image with
     * @param path Path to the image file
     */
    public PImage loadImage(final PApplet app, @CompileTimeConstant final String path) {
        Loggers.UI.debug("loading image at {}", path);
        final PImage img = app.loadImage(path);
        Loggers.UI.debug("loaded image at {}: {} (valid={})", path, img, ImageExt.isValidImage(img));
        return img;
    }

    public @Nullable Tile pixelCoordsToTile(final Vector2 coords, final GameData gameData) {
        // Use inverse lerp to extract the tile coordinates from the mouse pos
        final double x = Numerics.inverseLerp(coords.x, BOARD_POS_X, BOARD_POS_X + (CELL_SIZE_PX * BOARD_SIZE_TILES));
        final double y = Numerics.inverseLerp(coords.y, BOARD_POS_Y, BOARD_POS_Y + (CELL_SIZE_PX * BOARD_SIZE_TILES));

        // Floor to avoid small negatives rounding to zero
        final int tileX = (int) (Math.floor(x * BOARD_SIZE_TILES));
        final int tileY = (int) (Math.floor(y * BOARD_SIZE_TILES));

        return gameData.board.maybeGetTile(tileX, tileY);
    }

    public Vector2 tileToPixelCoords(final Tile tile) {
        return tileToPixelCoords(new Vector2(tile.getPos().getX(), tile.getPos().getY()));
    }

    /**
     * Converts a tile coordinate to pixel coordinates
     */
    public Vector2 tileToPixelCoords(final Vector2 tilePos) {
        // Offset by the tile's coordinates, and then half a tile extra to move to the centre 
        final double middlePosX = BOARD_POS_X + (tilePos.x * CELL_SIZE_PX) + (CELL_SIZE_PX / 2.0);
        final double middlePosY = BOARD_POS_Y + (tilePos.y * CELL_SIZE_PX) + (CELL_SIZE_PX / 2.0);
        return new Vector2(middlePosX, middlePosY);
    }

    /**
     * Initialises the UI, by creating all the UI elements
     */
    public void initUi(final UiState uiState) {
        // ===== BACKGROUND =====
        {
            uiState.uiElements.add(new RectElement(
                    new Vector2(0, 0),
                    new Vector2(Window.WINDOW_WIDTH_PX, Window.WINDOW_HEIGHT_PX),
                    Theme.APP_BACKGROUND,
                    Colour.NONE
            ) {
                @Override
                public RenderOrder getRenderOrder() {
                    return RenderOrder.BACKGROUND;
                }
            });
        }

        // ===== MANA BAR =====
        {
            final Vector2 manaBarPos1 = new Vector2(320, 10);
            final Vector2 manaBarPos2 = new Vector2(640, 30);
            uiState.uiElements.add(new RectElement(
                    manaBarPos1,
                    manaBarPos2,
                    Theme.WIDGET_BACKGROUND,
                    Theme.OUTLINE
            ));


            uiState.uiElements.add(new DynamicWrapperElement<>(
                    new RectElement(
                            manaBarPos1,
                            new Vector2(0, 0) /* Will be overwritten */,
                            Theme.MANA,
                            Theme.OUTLINE
                    ),
                    (rectElement, app, game, ui) -> {
                        // Each frame, update the position of the mana bar to reflect the
                        // fraction of how much mana the wizard has, compared to the max
                        rectElement.pos2.x = Numerics.lerp(
                                manaBarPos1.x,
                                manaBarPos2.x,
                                Math.max(0, game.mana / game.manaCap) // Don't go -ve
                        );
                        rectElement.pos2.y = manaBarPos2.y;
                    }
            ));
            uiState.uiElements.add(new DynamicWrapperElement<>(
                    new TextElement(manaBarPos1, manaBarPos2, ""),
                    (text, app, game, ui) -> text.text = MessageFormat.format(
                            "Mana: {0,number,integer}/{1,number,integer}",
                            game.mana,
                            game.manaCap
                    )
            ));
        }

        // ===== NEXT WAVE INDICATOR =====
        {
            final Vector2 waveIndicatorPos1 = new Vector2(0, 10);
            final Vector2 waveIndicatorPos2 = new Vector2(320, 30);
            uiState.uiElements.add(new DynamicWrapperElement<>(
                    new TextElement(
                            waveIndicatorPos1,
                            waveIndicatorPos2,
                            ""
                    ),
                    (text, app, game, ui) -> {
                        final Wave wave = game.waves.isEmpty() ? null : game.waves.get(0);
                        if (wave == null) {
                            text.text = "No more waves remaining";
                        }
                        else if (wave.getWaveState() == Wave.WaveState.PRE_DELAY) {
                            final double timeTillSpawn = -wave.getTimer() + wave.delayBeforeWave;
                            text.text = MessageFormat.format(
                                    "Wave {0,number,##} starts: {1,number,00.00} seconds",
                                    wave.waveNumber,
                                    timeTillSpawn
                            );
                        }
                        else if (wave.getWaveState() == Wave.WaveState.SPAWNING) {
                            text.text = MessageFormat.format("Wave {0,number,##} spawning now", wave.waveNumber);
                        }
                        else if (wave.getWaveState() == Wave.WaveState.COMPLETE) {
                            text.text = MessageFormat.format("Wave {0,number,##} complete", wave.waveNumber);
                        }
                        else {
                            text.text = "ERROR: WAVE GOOFED";
                            Loggers.GAMEPLAY.warn("wave {} had invalid wave state", wave.waveNumber);
                        }
                    }
            ));
        }


        // ===== SIDEBAR BUTTONS =====
        {
            final Vector2 buttonPos = new Vector2(640 + 16, 40 + 16);
            addSidebarButton(
                    uiState,
                    buttonPos,
                    "FF",
                    KeyCode.F,
                    (button, app, game, ui) -> Logger.debug("toggle fast forward = {}", game.fastForward ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(game.fastForward, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    buttonPos,
                    "P",
                    KeyCode.P,
                    (button, app, game, ui) -> Logger.debug("toggle pause = {}", game.paused ^= true),
                    (button, app, game, ui) -> button.fillColour = Theme.buttonColour(game.paused, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    buttonPos,
                    "U1",
                    KeyCode.NUM_1,
                    (button, app, game, ui) -> Logger.debug("toggle upgrade range = {}", ui.wantsUpgradeRange ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(ui.wantsUpgradeRange, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    buttonPos,
                    "U2",
                    KeyCode.NUM_2,
                    (button, app, game, ui) -> Logger.debug("toggle upgrade speed = {}", ui.wantsUpgradeSpeed ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(ui.wantsUpgradeSpeed, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    buttonPos,
                    "U3",
                    KeyCode.NUM_3,
                    (button, app, game, ui) -> Logger.debug(
                            "toggle upgrade damage = {}",
                            ui.wantsUpgradeDamage ^= true
                    ),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(ui.wantsUpgradeDamage, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    buttonPos,
                    "M",
                    KeyCode.M,
                    (button, app, game, ui) -> {
                        Loggers.GAMEPLAY.debug("cast mana pool");
                        button.fillColour = Theme.MANA;
                        game.spells.manaSpell.cast(game);
                    },
                    // Nice little fade out animation
                    (button, app, game, ui) -> {
                        final double ANIM_SPEED = 0.03;
                        button.fillColour = Colour.lerp(
                                button.fillColour,
                                button.isHovered ? Theme.BUTTON_HOVERED : Theme.BUTTON_DISABLED,
                                ANIM_SPEED
                        );
                    }
            );
        }

        // ===== HACK: TOWER SELECTION =====
        uiState.uiElements.add(
                new KeyboardElement(
                        new KeyPress(KeyCode.T, false, KeyAction.PRESS),
                        (_elem, app, game, ui) -> {
                            // Find which tile is hovered
                            final Tile tile = UiManager.pixelCoordsToTile(
                                    new Vector2(app.mouseX, app.mouseY),
                                    game
                            );
                            Logger.info("Tower {}", tile);
                        }
                )
        );
    }

    /**
     * A helper method that adds sidebar buttons to the UI
     *
     * @param uiState       Object containing the UI data
     * @param buttonPos     Vector position of the button on-screen, will be mutated
     * @param text          Text for the button
     * @param activationKey Key that activates the button
     * @param click         Function to run when the button is clicked/activated
     * @param draw          Function to be called every frame
     */
    private static void addSidebarButton(
            final UiState uiState, final Vector2 buttonPos,
            final String text, final @Nullable KeyCode activationKey,
            final UiAction<ButtonElement> click,
            final UiAction<ButtonElement> draw) {
        final Vector2 buttonSize = new Vector2(48, 48);

        final Vector2 pos1 = buttonPos.clone();
        final Vector2 pos2 = buttonPos.clone();
        pos2.add(buttonSize);
        buttonPos.y += buttonSize.y;

        uiState.uiElements.add(new DynamicWrapperElement<>(new ButtonElement(
                pos1,
                pos2,
                text,
                Theme.TEXT_SIZE_LARGE,
                Theme.BUTTON_DISABLED,
                Theme.OUTLINE,
                activationKey == null ? null : new KeyPress(
                        activationKey,
                        false,
                        KeyAction.PRESS
                ),
                click
        ), draw));
    }

    // ========== INPUT ========== 
    // region

    public void mouseEvent(
            final PApplet app, final GameData gameData, final UiState uiState,
            final MousePress press) {
        final @Nullable Tile tile = UiManager.pixelCoordsToTile(new Vector2(press.coords.x, press.coords.y), gameData);
        if (tile != null)
            Loggers.INPUT.debug(
                    "mouse event: {}; [{}, {}]: {}",
                    press,
                    tile.getPos().getX(),
                    tile.getPos().getY(),
                    tile
            );
        else
            Loggers.INPUT.debug("mouse event: {}; no tile", press);

        // Click any elements that matched on hovering
        // Hopefully this only clicks one element at a time
        if (press.action == MouseAction.CLICK) {
            getInteractiveElements(uiState.uiElements)
                    .filter(elem -> elem.isMouseOver(press.coords))
                    .forEach(elem -> {
                        Loggers.INPUT.debug("activate element {}", elem);
                        elem.activate(app, gameData, uiState);
                    });
        }

        getInteractiveElements(uiState.uiElements)
                .forEach(elem -> elem.isHovered = elem.isMouseOver(press.coords));
    }

    public void keyEvent(
            final PApplet app, final GameData gameData, final UiState uiState,
            final KeyPress press) {
        // Pass on any key-presses to the UI elements
        Loggers.INPUT.debug("key event: {}", press);

        // Hopefully this only clicks one element at a time
        getInteractiveElements(uiState.uiElements)
                .filter(elem -> elem.keyMatches(press))
                .forEach(elem -> {
                    Loggers.INPUT.debug("activate element {}", elem);
                    elem.activate(app, gameData, uiState);
                });
    }

    @SuppressWarnings("unchecked")
    public static Stream<InteractiveElement> getInteractiveElements(
            final Collection<UiElement> uiElements) {
        /*
         SAFETY: This does use unchecked casting, however the objects are validated
         Apologies for the horrible code, but Java's type erasure makes this very
         difficult and awkward, and I don't think there's a better way to do it
         Would be much simpler with pattern matching
        
         TLDR:
         Java sucks donkey testicles harder than a vacuum cleaner
        */
        return uiElements.stream()
                         .map(elem -> {
                             // Try cast it to a clickable element type
                             if (elem instanceof InteractiveElement) {
                                 return (InteractiveElement) elem;
                             }
                             // Also see if it's a DynamicWrapperElement
                             else if ((elem instanceof DynamicWrapperElement) &&
                                      (((DynamicWrapperElement<UiElement>) elem).element instanceof InteractiveElement)) {
                                 return (InteractiveElement) ((DynamicWrapperElement<UiElement>) elem).element;
                             }
                             // Couldn't cast, skip it
                             else {
                                 //noinspection ReturnOfNull
                                 return null;
                             }
                         })
                         .filter(Objects::nonNull);
    }

    //endregion
}