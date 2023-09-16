package WizardTD.UI;

import lombok.*;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Colours {
    BLACK(0xFF_00_00_00), WHITE(0xFF_00_00_00), LIGHT_GREY(0xFF_D4_D4_D4), GREY(0xFF_88_88_88), DARK_GREY(0xFF_3A_3A_3A),

    RED(0xFF_FF_00_00), GREEN(0xFF_00_FF_00), BLUE(0xFF_00_00_FF),

    YELLOW(0xFF_FF_FF_00), DEEP_ORANGE(0xFF_FF_80_00), BRIGHT_ORANGE(0xFF_FF_BE_00),

    CYAN(0xFF_00_FF_DF), DEEP_PURPLE(0xFF_8A_0B_F0), BRIGHT_PURPLE(0xFF_CA_32_F0),
    ;

    public final int code;
}
