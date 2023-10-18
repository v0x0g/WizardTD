package WizardTD.UI.Appearance;

import lombok.experimental.*;

@UtilityClass
public class Theme {
    public final Colour TEXT = Colour.WHITE;
    public final Colour OUTLINE = Colour.LIGHT_GREY;
    public final Colour SELECTION_FILL = Colour.withAlpha(OUTLINE, 0.5);
    public final Colour RANGE_INDICATOR = Colour.YELLOW;
    public final Colour MANA = Colour.LIGHT_BLUE;
    public final Colour WIDGET_BACKGROUND = Colour.withAlpha(Colour.GREY, 0.6);
    public final Colour APP_BACKGROUND = Colour.DARK_GREY;

    public final Colour BUTTON_ENABLED = Colour.withAlpha(Colour.YELLOW, 0.5);
    public final Colour BUTTON_HOVERED = Colour.withAlpha(BUTTON_ENABLED, 0.3);
    public final Colour BUTTON_DISABLED = Colour.withAlpha(BUTTON_ENABLED, 0.1);

    public final float TEXT_SIZE_NORMAL = 14.0f;
    public final float TEXT_SIZE_LARGE = 24.0f;

    public Colour buttonColour(final boolean enabled, final boolean hovered) {
        return enabled ? BUTTON_ENABLED :
               hovered ? BUTTON_HOVERED :
               BUTTON_DISABLED;
    }
}
