package WizardTD.UI.Appearance;

import WizardTD.Ext.*;
import lombok.*;

@ToString
@EqualsAndHashCode
public final class Colour {
    public static final Colour BLACK = new Colour(0xFF_00_00_00);
    public static final Colour WHITE = new Colour(0xFF_FF_FF_FF);
    public static final Colour LIGHT_GREY = new Colour(0xFF_D4_D4_D4);
    public static final Colour GREY = new Colour(0xFF_88_88_88);
    public static final Colour DARK_GREY = new Colour(0xFF_3A_3A_3A);
    public static final Colour RED = new Colour(0xFF_FF_00_00);
    public static final Colour GREEN = new Colour(0xFF_00_FF_00);
    public static final Colour BLUE = new Colour(0xFF_00_00_FF);
    public static final Colour LIGHT_BLUE = new Colour(0xFF_42_A4_FF);
    public static final Colour YELLOW = new Colour(0xFF_FF_FF_00);
    public static final Colour DEEP_ORANGE = new Colour(0xFF_FF_80_00);
    public static final Colour BRIGHT_ORANGE = new Colour(0xFF_FF_BE_00);
    public static final Colour CYAN = new Colour(0xFF_00_FF_DF);
    public static final Colour DEEP_PURPLE = new Colour(0xFF_6A_0B_C0);
    public static final Colour BRIGHT_PURPLE = new Colour(0xFF_CA_32_F0);
    // Because processing is dumb, we can't have a colour of 0000, so make alpha barely zero
    public static final Colour NONE = new Colour(0x01_00_00_00);

    public final double r, g, b, a;

    public Colour(final int code) {
        this.a = intToDouble((code >> 24) & 255);
        this.r = intToDouble((code >> 16) & 255);
        this.g = intToDouble((code >> 8) & 255);
        this.b = intToDouble((code) & 255);
    }

    public Colour(final double r, final double g, final double b, final double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static Colour withAlpha(final Colour colour, final double alpha) {
        return new Colour(colour.r, colour.g, colour.b, alpha);
    }

    public static Colour lerp(final Colour c1, final Colour c2, final double lerp) {
        return new Colour(
                Numerics.lerp(c1.r, c2.r, lerp),
                Numerics.lerp(c1.g, c2.g, lerp),
                Numerics.lerp(c1.b, c2.b, lerp),
                Numerics.lerp(c1.a, c2.a, lerp)
                );
    }

    /**
     * Converts a normalised (0..1) double into a byte (0..255)
     */
    public static int doubleToInt(final double val) {
        //noinspection MagicNumber
        return (int) (Math.min(Math.max(val, 0.0), 1.0) * 255.0);
    }

    /**
     * Converts a normalised (0..1) double into a byte (0..255)
     */
    public static double intToDouble(final int val) {
        //noinspection MagicNumber
        return (val / 255.0);
    }

    @SuppressWarnings("all")
    public int asInt() {
        return
                doubleToInt(this.a) << 24
                | doubleToInt(this.r) << 16
                | doubleToInt(this.g) << 8
                | doubleToInt(this.b) << 0;
    }
}
