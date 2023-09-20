package WizardTD.UI.Elements;

import WizardTD.Gameplay.Game.*;
import WizardTD.UI.Appearance.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class TextElement extends UiElement {

    public @NonNull Vector2 pos1;
    public @NonNull Vector2 pos2;
    /**
     * See {@link PApplet#rectMode(int)}
     */
    public          int     rectPosMode = PConstants.CORNERS;
    /**
     * See {@link PApplet#textAlign(int)}
     */
    public          int     textAlignMode = PConstants.CENTER;
    public          float   fontSize = Theme.TEXT_SIZE_NORMAL;
    public @NonNull String  text;

    @Override
    public void render(@NonNull final PApplet app, @NonNull final GameData gameData) {
        app.fill(Theme.TEXT.code);
        app.rectMode(this.rectPosMode);
        app.textAlign(this.textAlignMode);
        app.textSize(this.fontSize);
        app.text(
                this.text,
                // For some reason text coords are backwards????
                (float) pos2.x, (float) pos2.y,
                (float) pos1.x, (float) pos1.y
        );
    }
}
