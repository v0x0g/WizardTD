package WizardTD.Rendering;

import WizardTD.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;
import processing.core.*;

/**
 * Interface for all objects that should be rendered to the screen
 */
public interface Renderable {
    /**
     * Returns the `RenderOrder` for this object, which respresents the time at which it should be rendered
     */
    RenderOrder getRenderOrder();

    /**
     * Renders the object, using the specified `app` instance
     */
    void render(final App app, GameData gameData, UiState uiState);
}
