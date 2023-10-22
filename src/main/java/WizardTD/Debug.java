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

import java.text.*;
import java.util.*;
import java.util.stream.*;

import static WizardTD.UI.Appearance.GuiConfig.*;

@UtilityClass
public class Debug {
    // TODO: Fix hover overlay image
    static final PImage tileHoverImage =
            ImageExt.generatePattern(
                    GameConfig.TILE_SIZE_PX, GameConfig.TILE_SIZE_PX,
                    1, 1,
                    ImageExt.ImagePattern.CHECKERS,
                    Colour.BLACK.withAlpha(0.5),
                    Colour.WHITE.withAlpha(0.5)
            );
    public static boolean
            pathfindingOverlayEnabled = false,
            tileHoverOverlayEnabled = true,
            f3OverlayEnabled = false,
            towerUpgradeOverlayEnabled = false;

    /**
     * Draws a pathfinding overlay for the game to assist with debugging
     */
    public void drawPathfindingOverlay(final PApplet app, final GameData game) {
        if (!pathfindingOverlayEnabled) return;

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
            final double offsetVal = Numerics.lerp(-GameConfig.TILE_SIZE_PX / 2.0, GameConfig.TILE_SIZE_PX / 2.0, (double) i / paths.size());
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
        if (!tileHoverOverlayEnabled) return;

        final Vector2 mousePos = new Vector2(app.mouseX, app.mouseY);
        final Tile tile = UiManager.pixelCoordsToTile(mousePos, game);
        if (tile == null) return;
        Renderer.renderSimpleTile(app, tileHoverImage, UiManager.tileToPixelCoords(tile));
    }

    public void showTowerUpgradeOverlay(final PApplet app, final GameData game) {
        if (!towerUpgradeOverlayEnabled) return;

        game.board.stream()
                  .filter(TowerTile.class::isInstance)
                  .map(TowerTile.class::cast)
                  .forEach(t -> {
                      final String str = MessageFormat.format(
                              "r: {0}\ns: {1}\nd: {2}\nm: {3,number,#.000}",
                              t.rangeUpgrades,
                              t.speedUpgrades,
                              t.damageUpgrades,
                              t.magazine
                      );

                      app.fill(Colour.RED.asInt());
                      app.textAlign(PConstants.CENTER, PConstants.CENTER);
                      app.textSize(Theme.TEXT_SIZE_NORMAL);
                      app.textLeading(Theme.TEXT_SIZE_NORMAL);
                      final Vector2 pos = UiManager.tileToPixelCoords(t);
                      app.text(str, (float) pos.x, (float) pos.y);
                  });
    }

    public void showF3Overlay(final PApplet app, final GameData game, final UiState ui) {
        if (!f3OverlayEnabled) return;

        final String str = String.format(
                "\n" +
                "===== FRAMES =====\n" +
                "avgFps=%03.1f fps, fps=%03.1f fps, frameCount=%05d f\n" +
                "lastTick=%08.3fs, appTick=%08.3fs, deltaTime=%.4fs\n" +
                "uiElements=%02d\n" +

                "\n" +
                "===== TICK =====\n" +
                "subTickThresh=%03.1f s, numTicks=%02d, speedMult=%03.1f\n" +
                "gameDelta=%01.4f s, visualDelta=%01.4f s\n" +

                "\n" +
                "===== PATHFINDING =====\n" +
                "pathCount=%03d, lengths=%s\n" +

                "\n" +
                "===== ENTITIES =====\n" +
                "enemies=%03d, projectiles=%03d\n" +

                "\n" +
                "===== MANA =====\n" +
                "trickle=%03.2f (%03.2f) /sec, mult=x%03.02f",

                Stats.Frames.avgFps, Stats.Frames.fps, Stats.Frames.frameCount,
                Stats.Frames.lastTick, Stats.Frames.thisTick, Stats.Frames.deltaTime,
                ui.uiElements.size(),

                Stats.Tick.subTickThresh, Stats.Tick.numTicks, Stats.Tick.speedMultiplier,
                Stats.Tick.gameDelta, Stats.Tick.visualDelta,

                game.enemyPaths.size(), game.enemyPaths.stream().collect(
                        Collectors.groupingBy(p -> p.positions.length, Collectors.counting())),

                game.enemies.size(), game.projectiles.size(),

                game.config.mana.initialManaTrickle, game.config.mana.initialManaTrickle *
                                                     game.manaGainMultiplier, game.manaGainMultiplier

        );

        final float x1 = 16, y1 = 40 + 16,
                x2 = UiPositions.SIDEBAR_X_PX, y2 = Window.WINDOW_HEIGHT_PX;

        app.fill(Theme.TEXT.asInt());
        app.textAlign(PConstants.LEFT, PConstants.TOP);
        app.textSize(Theme.TEXT_SIZE_NORMAL);
        app.rectMode(PConstants.CORNERS);
        app.text(str, x1, y1, x2, y2);
    }

    /**
     * Class containing statistics for debugging purposes
     */
    @UtilityClass
    public class Stats {
        @UtilityClass
        public class Frames {
            public double fps, avgFps;
            public double lastTick, thisTick, deltaTime;
            public long frameCount;
        }
        @UtilityClass
        public class Tick {
            public double subTickThresh, speedMultiplier;
            public double gameDelta, visualDelta;
            public long numTicks;
        }
    }
}
