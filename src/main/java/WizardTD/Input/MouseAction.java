package WizardTD.Input;

import lombok.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

@ToString
@AllArgsConstructor
@ExtensionMethod(Arrays.class)
public enum MouseAction {
    CLICK(processing.event.MouseEvent.CLICK),
    DRAG(processing.event.MouseEvent.DRAG),
    ENTER(processing.event.MouseEvent.ENTER),
    EXIT(processing.event.MouseEvent.EXIT),
    MOVE(processing.event.MouseEvent.MOVE),
    PRESS(processing.event.MouseEvent.PRESS),
    RELEASE(processing.event.MouseEvent.RELEASE),
    WHEEL(processing.event.MouseEvent.WHEEL);

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
