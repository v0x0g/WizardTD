package WizardTD.Rendering;

import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

/**
 * Base class for all objects that should be rendered to the screen
 */
public abstract class Renderable {
    /**
     * Returns the `RenderOrder` for this object, which respresents the time at which it should be rendered
     */
    public abstract @NonNull RenderOrder getRenderOrder();

    /**
     * Renders the object, using the specified `app` instance
     */
    public abstract void render(final @NonNull PApplet app);
}
