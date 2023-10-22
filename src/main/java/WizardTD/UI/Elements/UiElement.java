package WizardTD.UI.Elements;

import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
import lombok.*;
import processing.core.*;

@ToString
public abstract class UiElement implements Renderable {

    /**
     * Helper function that sets up simple shape parameters
     *
     * @param outline
     * @param fill
     */
    protected static void setColours(
            final PApplet app, final Colour fill, final Colour outline) {
        if (fill == Colour.NONE) app.noFill();
        else app.fill(fill.asInt());
        if (outline == Colour.NONE) app.noStroke();
        else app.stroke((float) outline.r, (float) outline.g, (float) outline.b, (float) outline.a);
        app.strokeWeight(Theme.OUTLINE_WEIGHT);
    }

    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.UI;
    }

}
