package WizardTD.Rendering;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;
import com.google.common.collect.*;
import lombok.experimental.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import java.util.*;
import java.util.concurrent.*;

import static WizardTD.UI.GuiConfig.*;

@UtilityClass
@ExtensionMethod(Arrays.class)
public class Renderer {
    private static final @NonNull ThreadLocal<ConcurrentHashMap<RenderOrder, Set<Renderable>>> renderOrderMaps =
            ThreadLocal.withInitial(ConcurrentHashMap::new);
    private static final @NonNull RenderOrder @NonNull [] renderOrders =
            RenderOrder.values().stream().sorted().toArray(RenderOrder[]::new);

    public void renderGameData(@NonNull final PApplet app, @NonNull final GameData game) {
        Loggers.RENDER.debug("start render");
        // When we render, we aggregate all the `Renderables`, and sort them into our map
        // Then iterate through the map in the correct order, and happy days
        final ConcurrentHashMap<RenderOrder, Set<Renderable>> renderOrderMap = renderOrderMaps.get();

        renderOrderMap.clear(); // Reset the mapping
        Streams.concat(game.enemies.stream(), game.projectiles.stream(), game.board.stream())
               .parallel() // Should speed up sorting with MOAR THREADS since we have lots of things to sort
               .forEach(obj ->
                                // Get the set for this render order, or create if missing
                                renderOrderMap.computeIfAbsent(
                                        obj.getRenderOrder(),
                                        $_ -> ConcurrentHashMap.newKeySet()
                                ).add(obj)// Add the object to that set
               );

        renderOrders.stream()
                    // map.get() CAN RETURN NULL, CARE
                    .map(key -> new AbstractMap.SimpleEntry<>(key, renderOrderMap.get(key)))
                    .forEach((final AbstractMap.SimpleEntry<RenderOrder, @Nullable Set<Renderable>> entry) -> {
                        final RenderOrder order = entry.getKey();
                        final @Nullable Set<Renderable> objs = entry.getValue();
                        Loggers.RENDER.debug("render group {}", order);
                        if (objs != null) objs.forEach(obj -> obj.render(app));
                        else Loggers.RENDER.debug("render group {} empty", objs);
                    });

        Loggers.RENDER.debug("end render");
    }

    public void renderSimpleTile(
            @NonNull final PApplet app, @Nullable PImage img, final double centreX, final double centreY) {
        Loggers.RENDER.trace("tile [{00}}, {00}]: render img {}", centreX, centreY, img);
        if (!UiManager.isValidImage(img)) {
            img = UiManager.missingTextureImage;
            Loggers.RENDER.trace("render tile [{00}, {00}]: missing texture", centreX, centreY);
        }
        app.imageMode(PConstants.CENTER);
        app.colorMode(PConstants.ARGB);
        app.image(img, (float) centreX, (float) centreY);
    }

    public @NonNull Vector2 calculateUiCoordsForTile(final int tileX, final int tileY) {
        //noinspection UnnecessaryLocalVariable
        final int globalOffsetX = 0, globalOffsetY = TOPBAR_HEIGHT_PX;
        // Offset by the tile's coordinates, and then half a tile extra to move to the centre 
        final int middlePosX = globalOffsetX + (tileX * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
        final int middlePosY = globalOffsetY + (tileY * CELL_SIZE_PX) + (CELL_SIZE_PX / 2);
        return new Vector2(middlePosX, middlePosY);
    }
}
