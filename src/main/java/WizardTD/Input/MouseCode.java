package WizardTD.Input;

import lombok.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

@ToString
@AllArgsConstructor
@ExtensionMethod(Arrays.class)
public enum MouseCode {
    LMB(37),
    RMB(39),
    MMB(3),
    MOVE(0);

    public final int code;

    public static @Nullable MouseCode fromInt(final int code) {
        return MouseCode
                .values()
                .stream()
                .filter((v) -> v.code == code)
                .findFirst()
                .orElse(null);
    }
}
