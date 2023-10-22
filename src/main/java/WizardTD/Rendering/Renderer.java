package WizardTD.Rendering;

import WizardTD.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import com.google.common.collect.*;
import lombok.experimental.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import java.time.*;
import java.util.*;

@UtilityClass
@ExtensionMethod(Arrays.class)
public class Renderer {
    public final PImage missingTextureImage = ImageExt.generatePattern(
            GameConfig.TILE_SIZE_PX,
            GameConfig.TILE_SIZE_PX,
            GameConfig.TILE_SIZE_PX / 2,
            2,
            ImageExt.ImagePattern.CHECKERS,
            Colour.BRIGHT_PURPLE,
            Colour.BLACK
    );

    /**
     * This contains cached HashMaps and Lists for use when sorting objects for rendering.
     * This allows memory reuse (lists and maps are pooled), massively increasing performance
     * and reducing allocations.
     * <p>
     * I use thread-locals here just in case, probably not necessary, but it's good practice to be certain
     * (thanks Rust)
     */
    private static final ThreadLocal<HashMap<RenderOrder, List<Renderable>>> renderOrderMaps =
            ThreadLocal.withInitial(HashMap::new);

    /**
     * Sorted array containing the render orders.
     * Iterate over this in order to render everything correctly
     */
    private static final RenderOrder[] sortedRenderOrders =
            RenderOrder.values().stream().sorted().toArray(RenderOrder[]::new);

    public void render(final PApplet app, final GameData game, final UiState ui) {
        Loggers.RENDER.debug("start render");
        final Instant startInstant = Instant.now();
        // When we render, we aggregate all the `Renderables`, and sort them into our map
        // Then iterate through the map in the correct order, and happy days
        final HashMap<RenderOrder, List<Renderable>> renderOrderMap = renderOrderMaps.get();

        renderOrderMap.clear(); // Reset the mapping

        Streams.concat(game.enemies.stream(), game.projectiles.stream(), game.board.stream(), ui.uiElements.stream())
               // Get the set for this render order, or create if missing
               .forEach(obj -> renderOrderMap.computeIfAbsent(obj.getRenderOrder(), $_ -> new ArrayList<>())
                                             .add(obj)// Add the object to that set
               );


        // WARNING: map.get() can return null, if there have been no objects in that render group so far
        // (and therefore the entry was never initialised)
        sortedRenderOrders.stream()
                          .map(key -> new AbstractMap.SimpleEntry<>(key, renderOrderMap.get(key)))
                          .forEach(entry -> {
                              final RenderOrder order = entry.getKey();
                              final @Nullable List<Renderable> objs = entry.getValue();
                              Loggers.RENDER.debug("render group {} (size {})", order, objs != null ? objs.size() : 0);
                              if (objs != null) objs.forEach(obj -> obj.render(app, game, ui));
                          });

        final Instant endInstant = Instant.now();
        Loggers.RENDER.debug(
                "end render; start={}, end={}, time={}ms ",
                startInstant,
                endInstant,
                Duration.between(startInstant, endInstant)
                        .toMillis()
        );
    }

    /**
     * Renders a simple tile image at the given pixel coordinates
     *
     * @param img    Image to render for the tile
     * @param centre Position of the tile, in pixel coordinates
     */
    public void renderSimpleTile(
            final PApplet app, @Nullable PImage img, final Vector2 centre) {
        if (!ImageExt.isValidImage(img)) {
            img = missingTextureImage;
            Loggers.RENDER.debug("tile [{00}, {00}]: missing texture", centre.x, centre.y);
        }
        app.imageMode(PConstants.CENTER);
        app.colorMode(PConstants.ARGB);
        app.image(img, (float) centre.x, (float) centre.y);
    }

    /**
     * Renders a simple tile image at the given tile coordinates
     *
     * @param img Image to render for the entity
     * @param pos Position of the entity, in tile coordinates
     */
    public void renderSimpleEnemy(
            final PApplet app, @Nullable PImage img, final Vector2 pos) {
        if (!ImageExt.isValidImage(img)) {
            img = missingTextureImage;
            Loggers.RENDER.debug("entity ({0}, {0): missing texture", pos.x, pos.y);
        }
        app.imageMode(PConstants.CENTER);
        app.colorMode(PConstants.ARGB);
        final Vector2 pixelPos = UiManager.tileToPixelCoords(pos);
        app.image(img, (float) pixelPos.x, (float) pixelPos.y);
    }
}
