package WizardTD.UI.Elements;

import WizardTD.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import processing.core.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class RectElement extends UiElement {
    public Vector2 pos1;
    public Vector2 pos2;
    public Colour fillColour;
    public Colour outlineColour;
    /**
     * See {@link PApplet#rectMode(int)}
     */
    public int rectPosMode = PConstants.CORNERS;

    public RectElement(final Vector2 pos1, final Vector2 pos2, final Colour fillColour, final Colour outlineColour) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.fillColour = fillColour;
        this.outlineColour = outlineColour;
    }

    @Override
    public void render(final App app, final GameData gameData, final UiState uiState) {
        setColours(app, this.fillColour, this.outlineColour);
        app.rectMode(this.rectPosMode);
        app.rect(
                (float) pos1.x, (float) pos1.y,
                (float) pos2.x, (float) pos2.y
        );
    }
}
