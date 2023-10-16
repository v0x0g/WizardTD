package WizardTD.UI.ClickableElements;

import WizardTD.Gameplay.Game.*;
import WizardTD.Input.*;
import WizardTD.UI.Elements.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;

@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public abstract class ClickableElement extends UiElement {
    /**
     * Top left corner of the object
     */
    public Vector2 corner1;
    /**
     * Bottom right corner of the object
     */
    public Vector2 corner2;
    /**
     * A key press that can be used to activate the button, as an alternative to clicking it
     */
    public @Nullable KeyPress activationKey;

    public abstract void activate(GameData gameData, UiState uiState);
}
