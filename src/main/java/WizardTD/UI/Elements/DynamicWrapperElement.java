package WizardTD.UI.Elements;

import WizardTD.Delegates.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

/**
 * Fancy wrapper class that allows you to wrap an inner {@link UiElement}
 * so that it can be modified dynamically for each render 
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class DynamicWrapperElement<T extends UiElement>  extends UiElement{
    public final @NonNull T                             element;
    public final @NonNull Action3<T, GameData, UiState> preRender;

    @Override
    public void render(@NonNull final PApplet app, @NonNull final GameData gameData, @NonNull final UiState uiState) {
        this.preRender.invoke(this.element, gameData, uiState);
        this.element.render(app, gameData, uiState);
    }
}