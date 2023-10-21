package WizardTD;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Tiles.*;
import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import lombok.experimental.*;
import mikera.vectorz.*;
import processing.core.*;

import java.util.*;

import static WizardTD.UI.Appearance.GuiConfig.*;

@UtilityClass
public class Debug {
    public static boolean
        pathfindingOverlayEnabled = false,
        tileHoverOverlayEnabled = true,
        f3OverlayEnabled = true,
        towerUpgradeOverlayEnabled = false;
    
    // TODO: Fix app overlay image
    static final PImage tileHoverImage =
            ImageExt.generatePattern(
                    CELL_SIZE_PX, CELL_SIZE_PX,
                    1, 1,
                    ImageExt.ImagePattern.CHECKERS,
                    Colour.BLACK.withAlpha(0.5),
                    Colour.WHITE.withAlpha(0.5)
            );

    /**
     * Draws a pathfinding overlay for the game to assist with debugging
     */
    public void drawPathfindingOverlay(final PApplet app, final GameData game) {
        if(!pathfindingOverlayEnabled) return;
        
        final float LINE_THICKNESS = 2.0f;
        final Colour[] DRAW_COLOURS = new Colour[]{
                Colour.BLACK,
                Colour.WHITE,
                Colour.RED,
                Colour.GREEN,
                Colour.BLUE,
                Colour.LIGHT_BLUE,
                Colour.YELLOW,
                Colour.DEEP_ORANGE,
                Colour.BRIGHT_ORANGE,
                Colour.CYAN,
                Colour.DEEP_PURPLE,
                Colour.BRIGHT_PURPLE,
        };

        final List<EnemyPath> paths = game.enemyPaths;
        for (int i = 0; i < paths.size(); i++) {
            final EnemyPath path = paths.get(i);
            // Give each line a slight offset, so they don't all overlap
            final double offsetVal = Numerics.lerp(-CELL_SIZE_PX / 2.0, CELL_SIZE_PX / 2.0, (double) i / paths.size());
            final Vector2 offset = new Vector2(offsetVal, offsetVal);
            final Colour colour = DRAW_COLOURS[i % DRAW_COLOURS.length].withAlpha(0.5);
            // Lerp along the path and draw lines/dots to visualise
            for (double d = 0; d <= path.positions.length; d += 1) {
                final Vector2 pos1 = UiManager.tileToPixelCoords(path.calculatePos(d));
                final Vector2 pos2 = UiManager.tileToPixelCoords(path.calculatePos(d + 1));

                app.fill(colour.asInt());
                app.stroke(colour.asInt());
                app.strokeWeight(LINE_THICKNESS);
                app.line((float) (pos1.x + offset.x), (float) (pos1.y + offset.y),
                         (float) (pos2.x + offset.x), (float) (pos2.y + offset.y)
                );
            }
        }
    }

    /**
     * Draws a small overlay for which tile is currently hovered
     */
    public void drawHoveredTileOverlay(final PApplet app, final GameData game) {
        if(!tileHoverOverlayEnabled) return;

        final Vector2 mousePos = new Vector2(app.mouseX, app.mouseY);
        final Tile tile = UiManager.pixelCoordsToTile(mousePos, game);
        if (tile == null) return;
        Renderer.renderSimpleTile(app, tileHoverImage, UiManager.tileToPixelCoords(tile));
    }

    public void showTowerUpgradeOverlay(final PApplet app, final GameData game) {
        if(!towerUpgradeOverlayEnabled) return;

        game.board.stream()
                  .filter(TowerTile.class::isInstance)
                  .map(TowerTile.class::cast)
                  .forEach(t -> {
                      final String str = "r: " + t.rangeUpgrades + "\n" +
                                         "s: " + t.speedUpgrades + "\n" +
                                         "d: " + t.damageUpgrades;

                      app.fill(Colour.RED.asInt());
                      app.textAlign(PConstants.CENTER, PConstants.CENTER);
                      app.textSize(Theme.TEXT_SIZE_NORMAL);
                      app.textLeading(Theme.TEXT_SIZE_NORMAL * 0.6f);
                      final Vector2 pos = UiManager.tileToPixelCoords(t);
                      app.text(str, (float)pos.x, (float)pos.y);
                  });
    }
    
    public void showF3Overlay(final PApplet app, final GameData game, final UiState ui){
        final String SEP = "=====";
        final String str = String.format(
                "%s FRAMES %s\n" +
                "P.frameRate=%03.1f, P.frameCount=%05d\n" +
                "lastTick=%08.3f, appTick=%08.3f, deltaTime=%.4f, fps=%03.1f\n",
                SEP, SEP,
                app.frameRate, app.frameCount,
                lastTick, appTick, deltaTime, 1 / deltaTime
        );

        final float x1 = 16, y1 = 40 + 16;

        app.fill(Theme.TEXT.asInt());
        app.textAlign(PConstants.LEFT, PConstants.TOP);
        app.textSize(Theme.TEXT_SIZE_NORMAL);

        app.text(str, x1, y1);
    }
}
