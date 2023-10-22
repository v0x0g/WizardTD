package WizardTD.UI;

import WizardTD.*;
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
import static WizardTD.UI.Appearance.GuiConfig.Window.*;

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
        final double x = Numerics.inverseLerp(coords.x, BOARD_X_PX, BOARD_X_PX + (CELL_SIZE_PX * BOARD_SIZE_TILES));
        final double y = Numerics.inverseLerp(coords.y, BOARD_Y_PX, BOARD_Y_PX + (CELL_SIZE_PX * BOARD_SIZE_TILES));

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
        final double middlePosX = BOARD_X_PX + (tilePos.x * CELL_SIZE_PX) + (CELL_SIZE_PX / 2.0);
        final double middlePosY = BOARD_Y_PX + (tilePos.y * CELL_SIZE_PX) + (CELL_SIZE_PX / 2.0);
        return new Vector2(middlePosX, middlePosY);
    }

    /**
     * Initialises the UI, by creating all the UI elements
     */
    public void initUi(final UiState uiState) {
        // ===== BACKGROUND =====
        {
            uiState.uiElements.add(new RectElement(
                    new Vector2(TOP_BAR_X_PX, TOP_BAR_Y_PX),
                    new Vector2(WINDOW_WIDTH_PX, TOP_BAR_HEIGHT_PX),
                    Theme.APP_BACKGROUND,
                    Colour.NONE
            ) {
                @Override
                public RenderOrder getRenderOrder() {
                    return RenderOrder.BACKGROUND;
                }
            });
            uiState.uiElements.add(new RectElement(
                    new Vector2(SIDEBAR_X_PX, SIDEBAR_Y_PX),
                    new Vector2(WINDOW_WIDTH_PX, WINDOW_HEIGHT_PX),
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
        final Vector2 sidebarButtonPos = new Vector2(SIDEBAR_X_PX + 8, SIDEBAR_Y_PX + 8);
        {
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "FF", "Fast Forward",
                    KeyCode.F,
                    (button, app, game, ui) -> Logger.debug("toggle fast forward = {}", game.fastForward ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(game.fastForward, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "P", "Pause",
                    KeyCode.P,
                    (button, app, game, ui) -> Logger.debug("toggle pause = {}", game.paused ^= true),
                    (button, app, game, ui) -> button.fillColour = Theme.buttonColour(game.paused, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "T", "Build Tower",
                    KeyCode.T,
                    (button, app, game, ui) -> Logger.debug("toggle tower = {}", ui.wantsPlaceTower ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(ui.wantsPlaceTower, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "U1", "Upgrade Range",
                    KeyCode.NUM_1,
                    (button, app, game, ui) -> Logger.debug("toggle upgrade range = {}", ui.upgradeRange ^= true),
                    (button, app, game, ui) -> button.fillColour = Theme.buttonColour(ui.upgradeRange, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "U2", "Upgrade Speed",
                    KeyCode.NUM_2,
                    (button, app, game, ui) -> Logger.debug("toggle upgrade speed = {}", ui.upgradeSpeed ^= true),
                    (button, app, game, ui) -> button.fillColour = Theme.buttonColour(ui.upgradeSpeed, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "U3", "Upgrade Damage",
                    KeyCode.NUM_3,
                    (button, app, game, ui) -> Logger.debug("toggle upgrade damage = {}", ui.upgradeDamage ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(ui.upgradeDamage, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "M", "Mana Pool",
                    KeyCode.M,
                    // Nice little fade out animation
                    (button, app, game, ui) -> {
                        Loggers.GAMEPLAY.debug("cast mana pool");
                        button.fillColour = Theme.MANA;
                        game.spells.manaSpell.cast(game);
                    },
                    (button, app, game, ui) -> {
                        final double ANIM_SPEED = 0.03;
                        button.fillColour = Colour.lerp(
                                button.fillColour,
                                button.isHovered ? Theme.BUTTON_HOVERED : Theme.BUTTON_DISABLED,
                                ANIM_SPEED
                        );
                        // A slight hack to modify the text that shows the cost of the spell
                        // Don't really have a good way to access the text
                        // It would be pointless to refactor addSidebarButton() for one use case
                        final TextElement text = ui.uiElements.stream()
                                                              .filter(TextElement.class::isInstance)
                                                              .map(TextElement.class::cast)
                                                              .filter(t -> t.text.startsWith("Mana Pool"))
                                                              .findAny().orElse(null);
                        if (text == null) {
                            Loggers.UI.warn("couldn't find the text element for the mana pool description");
                        }
                        else {
                            text.text = MessageFormat.format(
                                    "Mana Pool:\nCost: {0,number,####}",
                                    game.spells.manaSpell.currentCost
                            );
                        }
                    }
            );
        }

        // ===== BOARD CLICK HANDLER =====
        {
            uiState.uiElements.add(new ButtonElement(
                    new Vector2(BOARD_X_PX, BOARD_Y_PX),
                    new Vector2(SIDEBAR_X_PX, WINDOW_HEIGHT_PX),
                    null, Colour.NONE, Colour.NONE, null,
                    (button, app, game, ui) -> {
                        Loggers.GAMEPLAY.debug("board clicked");

                        if (!ui.wantsPlaceTower) return;

                        Loggers.INPUT.info("place tower interaction");
                        // Find which tile is hovered
                        // Should never be null, since this element should perfectly cover the board
                        final Tile tile = UiManager.pixelCoordsToTile(new Vector2(app.mouseX, app.mouseY), game);
                        assert tile != null;

                        GameManager.placeOrUpgradeTower(
                                app, game, tile,
                                ui.upgradeRange,
                                ui.upgradeSpeed,
                                ui.upgradeDamage
                        );
                    }
            ));
        }

        // ===== DEBUG STUFF =====
        {
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "H", "Hover Overlay",
                    KeyCode.H,
                    (button, app, game, ui) ->
                            Logger.debug("toggle tile hover overlay = {}", Debug.tileHoverOverlayEnabled ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(Debug.tileHoverOverlayEnabled, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "E", "Enemy Paths",
                    KeyCode.E,
                    (button, app, game, ui) ->
                            Logger.debug("toggle path overlay = {}", Debug.pathfindingOverlayEnabled ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(Debug.pathfindingOverlayEnabled, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "U", "Upgrade Overlay",
                    KeyCode.U,
                    (button, app, game, ui) ->
                            Logger.debug("toggle tower upgrade overlay = {}", Debug.towerUpgradeOverlayEnabled ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(Debug.towerUpgradeOverlayEnabled, button.isHovered)
            );
            addSidebarButton(
                    uiState,
                    sidebarButtonPos,
                    "F3", "Stats Overlay",
                    KeyCode.FUNCTION_3,
                    (button, app, game, ui) ->
                            Logger.debug("toggle f3 overlay = {}", Debug.f3OverlayEnabled ^= true),
                    (button, app, game, ui) -> button.fillColour =
                            Theme.buttonColour(Debug.f3OverlayEnabled, button.isHovered)
            );
        }
    }

    /**
     * A helper method that adds sidebar buttons to the UI
     *
     * @param uiState       Object containing the UI data
     * @param buttonPos     Vector position of the button on-screen, will be mutated
     * @param text          Text for the button
     * @param description   Text to display as the description (to the side of the button)
     * @param activationKey Key that activates the button
     * @param click         Function to run when the button is clicked/activated
     * @param draw          Function to be called every frame
     */
    private static void addSidebarButton(
            final UiState uiState, final Vector2 buttonPos,
            final String text, final String description,
            final @Nullable KeyCode activationKey,
            final UiAction<ButtonElement> click,
            final UiAction<ButtonElement> draw) {
        final double BUTTON_SIZE = 48;
        final double SPACING = 4.0;

        final Vector2 buttonSize = new Vector2(BUTTON_SIZE, BUTTON_SIZE);

        final Vector2 buttonPos1 = buttonPos.clone();
        final Vector2 buttonPos2 = (Vector2) buttonPos.addCopy(buttonSize);
        buttonPos.y += buttonSize.y + SPACING;

        uiState.uiElements.add(new DynamicWrapperElement<>(new ButtonElement(
                buttonPos1, buttonPos2,
                text,
                Theme.BUTTON_DISABLED, Theme.OUTLINE,
                activationKey == null ? null : new KeyPress(
                        activationKey,
                        false,
                        KeyAction.PRESS
                ),
                click
        ), draw));

        final Vector2 descPos1 = (Vector2) buttonPos1.addCopy(new Vector2(BUTTON_SIZE + SPACING, 0));
        final Vector2 descPos2 = new Vector2(WINDOW_WIDTH_PX, descPos1.y + BUTTON_SIZE);
        uiState.uiElements.add(new TextElement(
                descPos1, descPos2,
                description
        ));
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