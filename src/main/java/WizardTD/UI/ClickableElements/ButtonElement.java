package WizardTD.UI.ClickableElements;

import WizardTD.Delegates.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Input.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class ButtonElement extends ClickableElement {

    public final @NonNull UiAction<ButtonElement> click;
    public                int                     fillColour;
    public                int                     outlineColour;
    /**
     * See {@link PApplet#rectMode(int)}
     */
    public @NonNull       String                  text;
    public                float                   fontSize;

    /**
     * See {@link PApplet#textAlign(int)}
     */
    public int textAlignMode = PConstants.CENTER;

    public ButtonElement(
            @NonNull final Vector2 corner1, @NonNull final Vector2 corner2,
            @NonNull final String text, final float fontSize,
            final int fillColour, final int outlineColour,
            @Nullable final KeyPress activationKey,
            @NonNull final UiAction<ButtonElement> click) {
        super(corner1, corner2, activationKey);
        this.click = click;
        this.fillColour = fillColour;
        this.outlineColour = outlineColour;
        this.text = text;
        this.fontSize = fontSize;
        this.activationKey = activationKey;
    }

    @Override
    public void render(@NonNull final PApplet app, @NonNull final GameData gameData, @NonNull UiState uiState) {
        setColours(app, this.fillColour, this.outlineColour);
        app.rectMode(PConstants.CORNERS);
        app.rect(
                (float) corner1.x, (float) corner1.y,
                (float) corner2.x, (float) corner2.y
        );
        app.fill(Theme.TEXT.code);
        app.textAlign(this.textAlignMode, this.textAlignMode);
        app.textSize(this.fontSize);
        app.text(
                this.text,
                // For some reason text coords are backwards????
                (float) corner2.x, (float) corner2.y,
                (float) corner1.x, (float) corner1.y
        );
    }

    @Override
    public void activate(final @NonNull GameData gameData, final @NonNull UiState uiState) {
        Loggers.UI.debug("activate button {}", this);
        this.click.invoke(this, gameData, uiState);
    }
}
