package WizardTD.UI.InteractableElements;

import WizardTD.Delegates.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Input.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import java.util.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class ButtonElement extends InteractiveElement {

    public final Vector2 corner1, corner2;
    public final @Nullable KeyPress activationKey;

    public final UiAction<ButtonElement> click;
    public Colour fillColour;
    public Colour outlineColour;
    /**
     * See {@link PApplet#rectMode(int)}
     */
    public @Nullable String text;
    public float fontSize;

    /**
     * See {@link PApplet#textAlign(int)}
     */
    public int textAlignMode = PConstants.CENTER;

    public ButtonElement(
            final Vector2 corner1, final Vector2 corner2,
            final @Nullable String text, final float fontSize,
            final Colour fillColour, final Colour outlineColour,
            @Nullable final KeyPress activationKey,
            final UiAction<ButtonElement> click) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.click = click;
        this.fillColour = fillColour;
        this.outlineColour = outlineColour;
        this.text = text;
        this.fontSize = fontSize;
        this.activationKey = activationKey;
    }

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        setColours(app, this.fillColour, this.outlineColour);
        app.rectMode(PConstants.CORNERS);
        app.rect(
                (float) corner1.x, (float) corner1.y,
                (float) corner2.x, (float) corner2.y
        );

        if (text != null && fontSize != 0) {
            app.fill(Theme.TEXT.asInt());
            app.textAlign(this.textAlignMode, this.textAlignMode);
            app.textSize(this.fontSize);
            app.text(
                    this.text,
                    // For some reason text coords are backwards????
                    (float) corner2.x, (float) corner2.y,
                    (float) corner1.x, (float) corner1.y
            );
        }
    }

    @Override
    public void activate(final PApplet app, final GameData gameData, final UiState uiState) {
        Loggers.UI.debug("activate button {}", this);
        this.click.invoke(this, app, gameData, uiState);
    }

    @Override
    public boolean isMouseOver(final Vector2 coords) {
        return coords.x >= this.corner1.x && coords.y >= this.corner1.y &&
               coords.x <= this.corner2.x && coords.y <= this.corner2.y;
    }

    @Override
    public boolean keyMatches(final KeyPress keyPress) {
        return Objects.equals(keyPress, this.activationKey);
    }
}
