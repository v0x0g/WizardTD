package WizardTD.UI.ClickableElements;

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

import java.util.function.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class ButtonElement extends ClickableElement {

    public final @NonNull BiConsumer<GameData, UiState> click;
    public                int                           fillColourInactive;
    public                int                           fillColourActive;
    public                int                           outlineColour;
    /**
     * See {@link PApplet#rectMode(int)}
     */
    public @NonNull       String                        text;
    public                float                         fontSize;
    public                boolean                       active;

    /**
     * See {@link PApplet#textAlign(int)}
     */
    public int textAlignMode = PConstants.CENTER;

    public ButtonElement(
            @NonNull final Vector2 corner1, @NonNull final Vector2 corner2,
            @NonNull final String text, final float fontSize,
            final int fillColourActive, final int fillColourInactive, final int outlineColour,
            @Nullable final KeyPress activationKey,
            @NonNull final BiConsumer<GameData, UiState> click) {
        super(corner1, corner2, activationKey);
        this.click = click;
        this.fillColourActive = fillColourActive;
        this.fillColourInactive = fillColourInactive;
        this.outlineColour = outlineColour;
        this.text = text;
        this.fontSize = fontSize;
        this.activationKey = activationKey;
    }

    @Override
    public void render(@NonNull final PApplet app, @NonNull final GameData gameData) {
        setColours(app, this.active ? this.fillColourActive : this.fillColourInactive, this.outlineColour);
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
        this.active ^= true; // Toggle :)
        this.click.accept(gameData, uiState);
    }
}
