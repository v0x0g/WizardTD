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
        final float LINE_THICKNESS = 2.0f;
        final float CIRCLE_SIZE = 16.0f;
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
        

        final List<EnemyPath> paths = Pathfinder.findPaths(game.board);
        game.enemyPaths = paths;
        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                final EnemyPath path = paths.get(i);
                // Give each line a slight offset, so they don't all overlap
                final double offsetVal = Numerics.lerp(-CELL_SIZE_PX / 2.0, CELL_SIZE_PX / 2.0, (double)i / paths.size());
                final Vector2 offset = new Vector2(offsetVal, offsetVal);
                final Colour colour = DRAW_COLOURS[i % DRAW_COLOURS.length];
                // Lerp along the path and draw lines/dots to visualise
                for (double d = 0; d <= path.positions.length; d += 1) {
                    final Vector2 pos1 = UiManager.tileToPixelCoords(path.calculatePos(d));
                    final Vector2 pos2 = UiManager.tileToPixelCoords(path.calculatePos(d + 1));

                    app.fill(colour.asInt());
                    app.stroke(colour.asInt());
                    app.strokeWeight(LINE_THICKNESS);
                    app.line((float) (pos1.x + offset.x), (float) (pos1.y  + offset.y),
                             (float) (pos2.x + offset.x), (float) (pos2.y  + offset.y));
                }
            }
        }
    }
}
