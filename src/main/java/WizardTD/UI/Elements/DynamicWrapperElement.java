package WizardTD.UI.Elements;

import WizardTD.Delegates.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;
import lombok.*;
import processing.core.*;

/**
 * Fancy wrapper class that allows you to wrap an inner {@link UiElement}
 * so that it can be modified dynamically for each render
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class DynamicWrapperElement<T extends UiElement> extends UiElement {
    public final T element;
    public final Action3<T, GameData, UiState> preRender;

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        this.preRender.invoke(this.element, gameData, uiState);
        this.element.render(app, gameData, uiState);
    }
}