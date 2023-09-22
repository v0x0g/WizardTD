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
     *
     * @param outline
     * @param fill
     */
    protected static void setColours(
            final @NonNull PApplet app, final @NonNull Colour fill, final @NonNull Colour outline) {
        if (fill == Colour.NONE) app.noFill();
        else app.fill(fill.asInt());
        if (outline == Colour.NONE) app.noStroke();
        else app.stroke((float) outline.r, (float) outline.g, (float) outline.b, (float) outline.a);
    }

    // TODO: Refactor this?
    @Override
    public @NonNull RenderOrder getRenderOrder() {
        return RenderOrder.UI;
    }

}
