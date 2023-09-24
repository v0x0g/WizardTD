package WizardTD.UI.Appearance;

import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

@UtilityClass
public class Theme {
    public final @NonNull Colour TEXT    = Colour.WHITE;
    public final @NonNull Colour OUTLINE = Colour.LIGHT_GREY;
    public final @NonNull Colour SELECTION_FILL  = Colour.withAlpha(OUTLINE, 0.5);
    public final @NonNull Colour RANGE_INDICATOR = Colour.YELLOW;
    public final @NonNull Colour MANA = Colour.LIGHT_BLUE;
    public final @NonNull Colour WIDGET_BACKGROUND = Colour.withAlpha(Colour.GREY, 0.6);
    public final @NonNull Colour APP_BACKGROUND = Colour.DARK_GREY;
    public final @NonNull Colour BUTTON_ENABLED = Colour.withAlpha(Colour.YELLOW, 0.5);
    public final @NonNull Colour BUTTON_HOVERED = Colour.withAlpha(BUTTON_ENABLED, 0.3);
    public final @NonNull Colour BUTTON_DISABLED = Colour.withAlpha(BUTTON_ENABLED, 0.1);
    
    public final float TEXT_SIZE_NORMAL = 14.0f;
    public final float TEXT_SIZE_LARGE = 24.0f;
}
