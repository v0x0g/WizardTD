package WizardTD.UI.Appearance;

import lombok.*;

import java.nio.*;

@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public final class Colour {
    public static final @NonNull Colour BLACK         = new Colour(0xFF_00_00_00);
    public static final @NonNull Colour WHITE         = new Colour(0xFF_FF_FF_FF);
    public static final @NonNull Colour LIGHT_GREY    = new Colour(0xFF_D4_D4_D4);
    public static final @NonNull Colour GREY          = new Colour(0xFF_88_88_88);
    public static final @NonNull Colour DARK_GREY     = new Colour(0xFF_3A_3A_3A);
    public static final @NonNull Colour RED           = new Colour(0xFF_FF_00_00);
    public static final @NonNull Colour GREEN         = new Colour(0xFF_00_FF_00);
    public static final @NonNull Colour BLUE          = new Colour(0xFF_00_00_FF);
    public static final @NonNull Colour LIGHT_BLUE    = new Colour(0xFF_42_A4_FF);
    public static final @NonNull Colour YELLOW        = new Colour(0xFF_FF_FF_00);
    public static final @NonNull Colour DEEP_ORANGE   = new Colour(0xFF_FF_80_00);
    public static final @NonNull Colour BRIGHT_ORANGE = new Colour(0xFF_FF_BE_00);
    public static final @NonNull Colour CYAN          = new Colour(0xFF_00_FF_DF);
    public static final @NonNull Colour DEEP_PURPLE   = new Colour(0xFF_6A_0B_C0);
    public static final @NonNull Colour BRIGHT_PURPLE = new Colour(0xFF_CA_32_F0);
    public static final @NonNull Colour NONE          = new Colour(0x00_00_00_00);

    public final int code;

    public static Colour withAlpha(final @NonNull Colour colour, final byte alpha){
        // Reinterpret as byte[4]
        final ByteBuffer bytes = ByteBuffer.allocate(4).putInt(colour.code);
        // Override alpha
        bytes.put(0, alpha);
        return new Colour(bytes.getInt(0));
    }
}
