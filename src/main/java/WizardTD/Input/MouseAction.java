package WizardTD.Input;

import lombok.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

@ToString
@AllArgsConstructor
@ExtensionMethod(Arrays.class)
public enum MouseAction {
    PRESS(processing.event.MouseEvent.PRESS),
    RELEASE(processing.event.MouseEvent.RELEASE);

    public final int code;

    public static @Nullable MouseAction fromInt(final int code) {
        return MouseAction
                .values()
                .stream()
                .filter((v) -> v.code == code)
                .findFirst()
                .orElse(null);
    }
}
