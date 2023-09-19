package WizardTD.UI.Elements;

import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

import java.util.function.*;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public final class TextElement extends UiElement {

    public final @NonNull Vector2          pos1;
    public final @NonNull Vector2          pos2;
    /**
     * See {@link PApplet#rectMode(int)}
     */
    public final          int              rectPosMode = PConstants.CORNERS;
    /**
     * See {@link PApplet#textAlign(int)}
     */
    public final          int              textAlignMode = PConstants.CENTER;
    public final          float            fontSize = Theme.TEXT_SIZE_NORMAL;
    public final @NonNull Function<GameData, String> textFunc;

    @Override
    public void render(@NonNull final PApplet app, @NonNull final GameData gameData) {
        app.fill(Theme.TEXT.code);
        app.rectMode(this.rectPosMode);
        app.textAlign(this.textAlignMode);
        app.textSize(this.fontSize);
        app.text(
                this.textFunc.apply(gameData),
                // For some reason text coords are backwards????
                (float) pos2.x, (float) pos2.y,
                (float) pos1.x, (float) pos1.y
        );
    }
}
