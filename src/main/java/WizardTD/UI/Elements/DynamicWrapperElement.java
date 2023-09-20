package WizardTD.UI.Elements;

import WizardTD.Gameplay.Game.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

import java.util.function.*;

/**
 * Fancy wrapper class that allows you to wrap an inner {@link UiElement}
 * so that it can be modified dynamically for each render 
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class DynamicWrapperElement<T extends UiElement>  extends UiElement{
    public final @NonNull T                       element;
    public final @NonNull BiConsumer<T, GameData> update;

    @Override
    public void render(@NonNull final PApplet app, @NonNull final GameData gameData) {
        this.update.accept(this.element, gameData);
        this.element.render(app, gameData);
    }
}
