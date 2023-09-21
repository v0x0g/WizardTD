package WizardTD.UI.Elements;

import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class RectElement extends UiElement {
    public @NonNull Vector2 pos1;
    public @NonNull Vector2 pos2;
    public          int     fillColour;
    public          int     outlineColour;
    /**
     * See {@link PApplet#rectMode(int)}
     */
    public          int     rectPosMode = PConstants.CORNERS;

    public RectElement(@NonNull final Vector2 pos1, @NonNull final Vector2 pos2, final int fillColour, final int outlineColour) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.fillColour = fillColour;
        this.outlineColour = outlineColour;
    }

    @Override
    public void render(@NonNull final PApplet app, @NonNull final GameData gameData, @NonNull UiState uiState) {
        setColours(app, this.fillColour, this.outlineColour);
        app.rectMode(this.rectPosMode);
        app.rect(
                (float) pos1.x, (float) pos1.y,
                (float) pos2.x, (float) pos2.y
        );
    }
}
