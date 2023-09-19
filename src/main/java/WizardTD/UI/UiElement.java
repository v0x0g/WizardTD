package WizardTD.UI;

import WizardTD.Rendering.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class UiElement extends Renderable {

    // TODO: Refactor this?
    @Override
    public @NonNull RenderOrder getRenderOrder() {
        return RenderOrder.UI;
    }

}
