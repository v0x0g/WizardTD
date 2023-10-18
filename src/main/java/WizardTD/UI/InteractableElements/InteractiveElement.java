package WizardTD.UI.InteractableElements;

import WizardTD.Gameplay.Game.*;
import WizardTD.Input.*;
import WizardTD.UI.Elements.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;

@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public abstract class InteractiveElement extends UiElement {
//    /**
//     * Top left corner of the object
//     */
//    public Vector2 corner1;
//    /**
//     * Bottom right corner of the object
//     */
//    public Vector2 corner2;
//    /**
//     * A key press that can be used to activate the button, as an alternative to clicking it
//     */
//    public @Nullable KeyPress activationKey;


    /**
     * Checks whether the mouse is currently over this UI Element
     *
     * @param mousePos Mouse position, pixel coords
     * @return Whether the mouse position is considered as hovering over this element
     */
    public abstract boolean isMouseOver(final Vector2 mousePos);

    /**
     * Checks whether the keypress should activate this element
     */
    public abstract boolean keyMatches(final KeyPress keyPress);

    /**
     * Activates the element (will be called whenever the element is clicked)
     */
    public abstract void activate(GameData gameData, UiState uiState);

    /**
     * Called each frame, to update whether the object is being hovered or not
     * @param isHovering Is the mouse currently hovering over this object
     */
    public void hovering(final boolean isHovering, final GameData gameData, final UiState uiState){}
}
