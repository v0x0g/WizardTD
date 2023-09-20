package WizardTD.UI.Elements;

import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class UiElement extends Renderable {

    /**
     * Helper function that sets up simple shape parameters
     * @param outline
     * @param fill
     */
    protected static void setColours(final @NonNull PApplet app, final int fill, final int outline) {
        if (fill == Colour.NONE.code) app.noFill();
        else app.fill(fill);
        if (outline == Colour.NONE.code) app.noStroke();
        else app.stroke(outline);
    }

    // TODO: Refactor this?
    @Override
    public @NonNull RenderOrder getRenderOrder() {
        return RenderOrder.UI;
    }

}
