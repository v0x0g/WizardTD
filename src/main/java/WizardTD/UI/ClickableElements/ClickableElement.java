package WizardTD.UI.ClickableElements;

import WizardTD.Ext.*;
import WizardTD.UI.Elements.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public abstract class ClickableElement extends UiElement {
    /**
     * Top left corner of the object
     */
    public @NonNull Vector2 corner1;
    /**
     * Bottom right corner of the object
     */
    public @NonNull  Vector2 corner2;
    /**
     * A key press that can be used to activate the button, as an alternative to clicking it
     */
    public @Nullable KeyPress activationKey;
}
