package WizardTD.UI.Appearance;

import lombok.experimental.*;

@UtilityClass
public class Theme {
    public final Colour TEXT = Colour.WHITE;
    public final Colour WIDGET_BACKGROUND = Colour.GREY.withAlpha(0.6);
    public final Colour APP_BACKGROUND = Colour.DARK_GREY;

    public final Colour RANGE_INDICATOR_OUTLINE = Colour.YELLOW;
    public final Colour RANGE_INDICATOR_FILL = Colour.YELLOW.withAlpha(0.1);
    public final Colour OUTLINE = Colour.LIGHT_GREY;

    public final Colour TOWER_UPGRADE_RANGE = Colour.RED.withAlpha(1.0);
    public final Colour TOWER_UPGRADE_DAMAGE = Colour.RED.withAlpha(1.0);
    public final Colour TOWER_UPGRADE_SPEED = Colour.LIGHT_BLUE.withAlpha(1.0);

    public final Colour BUTTON_ENABLED = Colour.YELLOW.withAlpha(0.5);
    public final Colour BUTTON_HOVERED = BUTTON_ENABLED.withAlpha(0.3);
    public final Colour BUTTON_DISABLED = BUTTON_ENABLED.withAlpha(0.1);

    public final Colour MANA = Colour.LIGHT_BLUE;
    public final Colour HEALTH_FULL = Colour.GREEN;
    public final Colour HEALTH_MISSING = Colour.RED;

    public final float OUTLINE_WEIGHT = 2.0f;
    public final float TEXT_SIZE_NORMAL = 12.0f;
    public final float TEXT_SIZE_LARGE = 28.0f;

    public Colour buttonColour(final boolean enabled, final boolean hovered) {
        return enabled ? BUTTON_ENABLED :
               hovered ? BUTTON_HOVERED :
               BUTTON_DISABLED;
    }
}
