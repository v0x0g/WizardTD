package WizardTD.Rendering;

import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import com.google.common.collect.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

import static WizardTD.UI.Appearance.GuiConfig.*;

@UtilityClass
@ExtensionMethod(Arrays.class)
public class Renderer {
    public final @NonNull PImage missingTextureImage = ImageExt.generatePattern(
            GuiConfig.CELL_SIZE_PX,
            GuiConfig.CELL_SIZE_PX,
            CELL_SIZE_PX >> 2,
            2,
            ImageExt.ImagePattern.CHECKERS,
            Colour.BRIGHT_PURPLE.getCode(),
            Colour.BLACK.getCode()
    );
    private static final @NonNull ThreadLocal<ConcurrentHashMap<RenderOrder, List<Renderable>>> renderOrderMaps =
            ThreadLocal.withInitial(ConcurrentHashMap::new);
    private static final @NonNull RenderOrder @NonNull [] renderOrders =
            RenderOrder.values()
                       .stream()
                       .sorted()
                       .toArray(RenderOrder[]::new);

    public void render(@NonNull final PApplet app, @NonNull final GameData game, @NonNull final UiState ui) {
        Loggers.RENDER.debug("start render");
        final Instant startInstant = Instant.now();
        // When we render, we aggregate all the `Renderables`, and sort them into our map
        // Then iterate through the map in the correct order, and happy days
        final ConcurrentHashMap<RenderOrder, List<Renderable>> renderOrderMap = renderOrderMaps.get();

        renderOrderMap.clear(); // Reset the mapping
        Streams.concat(game.enemies.stream(), game.projectiles.stream(), game.board.stream(), ui.uiElements.stream())
               .forEach(obj -> // Get the set for this render order, or create if missing
                                renderOrderMap
                                        .computeIfAbsent(obj.getRenderOrder(), $_ -> new ArrayList<>())
                                        .add(obj)// Add the object to that set
               );

        renderOrders.stream()
                    // map.get() CAN RETURN NULL, CARE
                    .map(key -> new AbstractMap.SimpleEntry<>(key, renderOrderMap.get(key)))
                    .forEach((final AbstractMap.SimpleEntry<RenderOrder, @Nullable List<Renderable>> entry) -> {
                        final RenderOrder order = entry.getKey();
                        final @Nullable List<Renderable> objs = entry.getValue();
                        Loggers.RENDER.debug("render group {}", order);
                        if (objs != null) objs.forEach(obj -> obj.render(app, game, ui));
                        else Loggers.RENDER.debug("render group {} empty", order);
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

    public void renderSimpleTile(
            @NonNull final PApplet app, @Nullable PImage img, final double centreX, final double centreY) {
        Loggers.RENDER.trace("tile [{00}}, {00}]: render img {}", centreX, centreY, img);
        if (!ImageExt.isValidImage(img)) {
            img = missingTextureImage;
            Loggers.RENDER.trace("render tile [{00}, {00}]: missing texture", centreX, centreY);
        }
        app.imageMode(PConstants.CENTER);
        app.colorMode(PConstants.ARGB);
        app.image(img, (float) centreX, (float) centreY);
    }

}
