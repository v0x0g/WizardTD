package WizardTD;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Tiles.*;
import WizardTD.UI.*;
import WizardTD.UI.Appearance.*;
import lombok.experimental.*;
import mikera.vectorz.*;
import processing.core.*;
import static WizardTD.UI.Appearance.GuiConfig.CELL_SIZE_PX;

import java.util.*;

@UtilityClass
public class Debug {
    public void drawPathfindingOverlay(final App app, final GameData game){
        final List<Tile> wizards = new ArrayList<>();
        final List<Tile> spawnPoints = new ArrayList<>();
        Pathfinder.scanBoard(game.board, wizards, spawnPoints);

        app.ellipseMode(PConstants.CENTER);
        wizards.forEach(t -> {
            final Vector2 pos = UiManager.tileToPixelCoords(t);
            final float size = 40;
            app.fill(Colour.BLACK.asInt());
            app.ellipse((float) pos.x, (float) pos.y, size, size);
        });

        spawnPoints.forEach(t -> {
            final Vector2 pos = UiManager.tileToPixelCoords(t);
            final float size = 40;
            app.fill(Colour.BLUE.asInt());
            app.ellipse((float) pos.x, (float) pos.y, size, size);
        });

        final List<EnemyPath> paths = Pathfinder.findPaths(
                game.board,
                spawnPoints,
                wizards
        );
        game.enemyPaths = paths;
        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                final EnemyPath path = paths.get(i);
                final double offsetVal = Numerics.lerp(-CELL_SIZE_PX / 2.0, CELL_SIZE_PX / 2.0, (double)i / paths.size());
                final Vector2 offset = new Vector2(offsetVal, offsetVal);
                final Colour[] colours = new Colour[]{
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
                final Colour colour = colours[i % colours.length];
                // Lerp along the path and draw lines/dots to visualise
                for (double d = 0; d <= path.positions.length; d += 1) {
                    final Vector2 pos1 = UiManager.tileToPixelCoords(path.calculatePos(d));
                    final Vector2 pos2 = UiManager.tileToPixelCoords(path.calculatePos(d + 1));
                    final float THICKNESS = 2;

                    app.fill(colour.asInt());
                    app.stroke(colour.asInt());
                    app.strokeWeight(THICKNESS);
                    app.line((float) (pos1.x + offset.x), (float) (pos1.y  + offset.y),
                             (float) (pos2.x + offset.x), (float) (pos2.y  + offset.y));
//                    app.ellipse((float) (pos.x + offset.x), (float) (pos.y  + offset.y), THICKNESS, THICKNESS);
                }
            }
        }
    }
}
