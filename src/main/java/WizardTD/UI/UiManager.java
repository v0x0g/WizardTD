package WizardTD.UI;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Tiles.*;
import com.google.errorprone.annotations.*;
import lombok.experimental.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

import java.text.*;
import java.util.*;

import static WizardTD.GameConfig.*;
import static WizardTD.UI.GuiConfig.*;
import static WizardTD.UI.GuiConfig.UiPositions.*;
import static WizardTD.UI.GuiConfig.Window.*;

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

    public void mouseClick(
            final @NonNull PApplet app, final @NonNull GameData gameData, final @NonNull UiState uiState) {
        final Ref<Integer> tileX = new Ref<>(0);
        final Ref<Integer> tileY = new Ref<>(0);
        @NonNull final Optional<Tile> tile =
                UiManager.pixelCoordsToTile(new Vector2(app.mouseX, app.mouseY), gameData, tileX, tileY);
        Loggers.UI.debug(
                "mouse click: x={0.000} ({})\ty={0.000} ({}) tile {}", app.mouseX, tileX, app.mouseY, tileY, tile);
    }
    
    public void initUi(@NonNull final UiState uiState){
        // Corners
        final Vector2 manaBarPos1 = new Vector2(320, 10);
        final Vector2 manaBarPos2 = new Vector2(640, 30);
//        corneredRect(
//                app,
//                manaBarPos1,
//                manaBarPos2,
//                Theme.SELECTION_OUTLINE.code,
//                Theme.WIDGET_BACKGROUND.code
//        );
//        final Vector2 filledManaBarPos2 = new Vector2(
//                Numerics.lerp(manaBarPos1.x, manaBarPos2.x, gameData.wizardHouse.mana / gameData.wizardHouse.manaCap),
//                manaBarPos2.y
//        );
//        corneredRect(
//                app,
//                manaBarPos1,
//                filledManaBarPos2,
//                Theme.SELECTION_OUTLINE.code,
//                Theme.MANA.code
//        );
        uiState.uiElements.add(new TextElement(
                manaBarPos1, manaBarPos2,
                (data) -> MessageFormat.format(
                        "Mana: {0}/{1}",
                        data.wizardHouse.mana,
                        data.wizardHouse.manaCap
                )
        ));

        // Next wave indicator
        final Vector2 waveIndicatorPos1 = new Vector2(0, 10);
        final Vector2 waveIndicatorPos2 = new Vector2(320, 30);
        uiState.uiElements.add(new TextElement(
                waveIndicatorPos1, waveIndicatorPos2,
                (data) -> MessageFormat.format(
                        "Wave {0,number,##} starts: {1,number,00.00} seconds",
                        59 - PApplet.second(),
                        PApplet.second() *26 / (float) 1000
                )
        ));
    }

    public void renderUi(final @NonNull PApplet app, final @NonNull GameData gameData, final @NonNull UiState uiState) {
        // 
        final Vector2 mousePos = new Vector2(app.mouseX, app.mouseY);
        @NonNull final Optional<Tile> hoveredTile =
                UiManager.pixelCoordsToTile(mousePos, gameData);

        // Change cursor if we are hovering a tile
        app.cursor(hoveredTile.isPresent() ? PConstants.CROSS : PConstants.ARROW);

        // Highlight hovered tile
        hoveredTile.ifPresent(value -> centeredRect(
                app,
                tileToPixelCoords(value), new Vector2(CELL_SIZE_PX, CELL_SIZE_PX),
                Theme.SELECTION_OUTLINE.code, Theme.SELECTION_FILL.code
        ));

        // Render the top bar
        final Vector2 topBarPos1 = new Vector2(0, 0);
        final Vector2 topBarPos2 = new Vector2(WINDOW_WIDTH_PX, TOP_BAR_HEIGHT_PX);
        corneredRect(app,
                     topBarPos1,
                     topBarPos2,
                     Colour.NONE.code, Theme.APP_BACKGROUND.code
        );


        // Render the sidebar
        final Vector2 sideBarPos1 = new Vector2(WINDOW_WIDTH_PX - SIDEBAR_WIDTH_PX, TOP_BAR_HEIGHT_PX);
        final Vector2 sideBarPos2 = new Vector2(WINDOW_WIDTH_PX, WINDOW_HEIGHT_PX);
        corneredRect(app,
                     sideBarPos1,
                     sideBarPos2,
                     Colour.NONE.code, Theme.APP_BACKGROUND.code
        );
    }

    // ========== GENERIC RENDERING FUNCTIONS =====
    //region

    public void ellipse(
            final @NonNull PApplet app, final @NonNull Vector2 centre, final @NonNull Vector2 size,
            final int outline, final int fill) {
        shapeSetup(app, outline, fill);
        app.ellipseMode(PConstants.CENTER);
        app.ellipse((float) centre.x, (float) centre.y, (float) size.x, (float) size.y);
    }

    public void centeredRect(
            final @NonNull PApplet app, final @NonNull Vector2 centre, final @NonNull Vector2 size,
            final int outline, final int fill) {
        shapeSetup(app, outline, fill);
        app.rectMode(PConstants.CENTER);
        app.rect((float) centre.x, (float) centre.y, (float) size.x, (float) size.y);
    }

    public void corneredRect(
            final @NonNull PApplet app,
            final @NonNull Vector2 cornerTopLeft, final @NonNull Vector2 cornerBotRight,
            final int outline, final int fill) {

        shapeSetup(app, outline, fill);
        app.rectMode(PConstants.CORNERS);
        app.rect((float) cornerTopLeft.x, (float) cornerTopLeft.y, (float) cornerBotRight.x, (float) cornerBotRight.y);
    }

    private void shapeSetup(
            final @NonNull PApplet app,
            final int outline, final int fill) {
        if (fill == Colour.NONE.code) app.noFill();
        else app.fill(fill);
        if (outline == Colour.NONE.code) app.noStroke();
        else app.stroke(outline);
    }

    //endregion

}