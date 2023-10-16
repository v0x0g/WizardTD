package WizardTD.Rendering;

import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;
import processing.core.*;

/**
 * Base class for all objects that should be rendered to the screen
 */
public abstract class Renderable {
    /**
     * Returns the `RenderOrder` for this object, which respresents the time at which it should be rendered
     */
    public abstract RenderOrder getRenderOrder();

    /**
     * Renders the object, using the specified `app` instance
     */
    public abstract void render(final PApplet app, GameData gameData, UiState uiState);
}
