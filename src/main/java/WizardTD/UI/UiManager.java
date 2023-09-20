package WizardTD.UI;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Tiles.*;
import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.ClickableElements.*;
import WizardTD.UI.Elements.*;
import com.google.errorprone.annotations.*;
import lombok.experimental.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.tinylog.*;
import processing.core.*;

import java.text.*;
import java.util.*;
import java.util.function.*;

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
    public @NonNull PImage loadImage(@NonNull final PApplet app, @NonNull @CompileTimeConstant final String path) {
        Loggers.UI.debug("loading image at {}", path);
        //noinspection LocalCanBeFinal
        val img = app.loadImage(path);
        Loggers.UI.debug("loaded image at {}: {} (valid={})", path, img, ImageExt.isValidImage(img));
        return img;
    }

    public @NonNull Optional<Tile> pixelCoordsToTile(final @NonNull Vector2 coords, final @NonNull GameData gameData) {
        return pixelCoordsToTile(coords, gameData, new Ref<>(0), new Ref<>(0));
    }

    public @NonNull Optional<Tile> pixelCoordsToTile(
            final @NonNull Vector2 coords, final @NonNull GameData gameData, final @NonNull Ref<Integer> outX,
            final @NonNull Ref<Integer> outY) {
        // Use inverse lerp to extract the tile coordinates from the mouse pos
        final double x = Numerics.inverseLerp(coords.x, BOARD_POS_X, BOARD_POS_X + (CELL_SIZE_PX * BOARD_SIZE_TILES));
        final double y = Numerics.inverseLerp(coords.y, BOARD_POS_Y, BOARD_POS_Y + (CELL_SIZE_PX * BOARD_SIZE_TILES));

        // Floor to avoid small negatives rounding to zero
        final int tileX = (int) (Math.floor(x * BOARD_SIZE_TILES));
        final int tileY = (int) (Math.floor(y * BOARD_SIZE_TILES));

        final Optional<Tile> tile = gameData.board.maybeGetTile(tileX, tileY);
        outX.value = tileX;
        outY.value = tileY;
        return tile;
    }

    public @NonNull Vector2 tileToPixelCoords(final @NonNull Tile tile) {
        // Offset by the tile's coordinates, and then half a tile extra to move to the centre 
        final int middlePosX = BOARD_POS_X + (tile.getPosX() * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
        final int middlePosY = BOARD_POS_Y + (tile.getPosY() * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
        return new Vector2(middlePosX, middlePosY);
    }

    public void initUi(@NonNull final UiState uiState) {
        //Background
        {
            uiState.uiElements.add(
                    new RectElement(
                     new Vector2(0, 0),
                     new Vector2(Window.WINDOW_WIDTH_PX, Window.WINDOW_HEIGHT_PX),
                     Theme.APP_BACKGROUND.code, Colour.NONE.code) {
                        @Override
                        public @NonNull RenderOrder getRenderOrder() {
                            return RenderOrder.BACKGROUND;
                        }
                    }
            );
        }

        // Mana bar
        {
            final Vector2 manaBarPos1 = new Vector2(320, 10);
            final Vector2 manaBarPos2 = new Vector2(640, 30);
            uiState.uiElements.add(
                    new RectElement(
                            manaBarPos1,
                            manaBarPos2,
                            Theme.WIDGET_BACKGROUND.code,
                            Theme.OUTLINE.code
                    )
            );


            uiState.uiElements.add(
                    new DynamicWrapperElement<>(
                            new RectElement(
                                    manaBarPos1,
                                    new Vector2(0, 0), // Will be overwritten
                                    Theme.MANA.code,
                                    Theme.OUTLINE.code
                            ),
                            (rectElement, gameData) -> {
                                rectElement.pos2.x = Numerics.lerp(
                                        manaBarPos1.x, manaBarPos2.x,
                                        gameData.wizardHouse.mana / gameData.wizardHouse.manaCap
                                );
                                rectElement.pos2.y = manaBarPos2.y;
                            }
                    )
            );
            uiState.uiElements.add(
                    new DynamicWrapperElement<>(
                            new TextElement(manaBarPos1, manaBarPos2, ""),
                            (text, data) -> text.text =
                                    MessageFormat.format(
                                            "Mana: {0}/{1}",
                                            data.wizardHouse.mana,
                                            data.wizardHouse.manaCap
                                    )
                    ));
        }
        // Next wave indicator
        {
            final Vector2 waveIndicatorPos1 = new Vector2(0, 10);
            final Vector2 waveIndicatorPos2 = new Vector2(320, 30);
            uiState.uiElements.add(
                    new DynamicWrapperElement<>(
                            new TextElement(waveIndicatorPos1, waveIndicatorPos2, ""),
                            (text, game) -> text.text = MessageFormat.format(
                                    "TODO: Wave {0,number,##} starts: {1,number,00.00} seconds",
                                    59 - PApplet.second(),
                                    PApplet.second() * 26 / (float) 1000
                            )
                    ));
        }


        {
            final Vector2 buttonSize = new Vector2(48, 48);
            final Vector2 buttonPos = new Vector2(640 + 16, 40 + 16);

            @SuppressWarnings("unused")
            @lombok.experimental.Helper
            class Helper {
                public void addButton(final @NonNull String text, final @NonNull BiConsumer<GameData, UiState> click) {
                    final Vector2 pos1 = buttonPos.clone();
                    final Vector2 pos2 = buttonPos.clone();
                    pos2.add(buttonSize);

                    buttonPos.y += buttonSize.y;

                    uiState.uiElements.add(
                            new ButtonElement(
                                    pos1, pos2,
                                    text,
                                    Theme.TEXT_SIZE_LARGE,
                                    Theme.BUTTON_DISABLED.code,
                                    Theme.OUTLINE.code,
                                    KeyCode.F,
                                    click
                            )
                    );
                }
            }
            
//            addButton(
//                    "FF",
//                    ($_, $__) -> Logger.info("press test button")
//            );

        }
    }

    // ========== INPUT ========== 
    // region

    public void mouseClick(final @NonNull PApplet app, final @NonNull GameData gameData, final @NonNull UiState uiState) {
        final Ref<Integer> tileX = new Ref<>(0);
        final Ref<Integer> tileY = new Ref<>(0);
        @NonNull final Optional<Tile> tile =
                UiManager.pixelCoordsToTile(new Vector2(app.mouseX, app.mouseY), gameData, tileX, tileY);
        Loggers.UI.debug(
                "mouse click: x={0.000} ({})\ty={0.000} ({}) tile {}", app.mouseX, tileX, app.mouseY, tileY, tile);
    }
    
    public void updateUi(final @NonNull PApplet app, final @NonNull GameData game, final @NonNull UiState state) {
        // Pass on any key-presses to the UI elements
        // 
        final KeyPress keyPress = new KeyPress(KeyCode.L);
        //noinspection ReturnOfNull
        state.uiElements.stream()
                        .map(e -> e instanceof ClickableElement ? (ClickableElement) e : null)
                        .filter(Objects::nonNull)
                        .forEach(elem -> {
                            if(keyPress.keyCode.equals(elem.activationKey)){
                                Logger.warn("press: todo");
                            }
                        });
        // Try to interact with any elements
    }

    //endregion
}