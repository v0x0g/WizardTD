package WizardTD.UI.Appearance;

import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

@UtilityClass
public class Theme {
    public final @NonNull Colour TEXT    = Colour.WHITE;
    public final @NonNull Colour OUTLINE = Colour.LIGHT_GREY;
    public final @NonNull Colour SELECTION_FILL  = Colour.withAlpha(OUTLINE, (byte) 0x80);
    public final @NonNull Colour RANGE_INDICATOR = Colour.YELLOW;
    public final @NonNull Colour MANA = Colour.LIGHT_BLUE;
    public final @NonNull Colour WIDGET_BACKGROUND = Colour.withAlpha(Colour.GREY, (byte) 0x90);
    public final @NonNull Colour APP_BACKGROUND = Colour.DARK_GREY;
    public final @NonNull Colour BUTTON_DISABLED = Colour.NONE;
    public final @NonNull Colour BUTTON_ENABLED = Colour.YELLOW;
    public final @NonNull Colour BUTTON_HOVERED = Colour.withAlpha(BUTTON_ENABLED, (byte) 0x80);
    
    public final float TEXT_SIZE_NORMAL = 14.0f;
    public final float TEXT_SIZE_LARGE = 24.0f;
}