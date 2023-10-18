package WizardTD.UI.InteractableElements;

import WizardTD.Delegates.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Input.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import java.util.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class KeyboardElement extends InteractiveElement {

    public final @Nullable KeyPress activationKey;
    public final UiAction<KeyboardElement> click;

    public KeyboardElement(@Nullable final KeyPress activationKey, final UiAction<KeyboardElement> click) {
        this.activationKey = activationKey;
        this.click = click;
    }

    @Override
    public void render(PApplet app, GameData gameData, UiState uiState) {

    }

    @Override
    public boolean isMouseOver(Vector2 mousePos) {
        return false;
    }

    @Override
    public boolean keyMatches(final KeyPress keyPress) {
        return Objects.equals(keyPress, this.activationKey);
    }

    @Override
    public void activate(GameData gameData, UiState uiState) {
        Loggers.UI.debug("activate button {}", this);
        this.click.invoke(this, gameData, uiState);
    }
}
