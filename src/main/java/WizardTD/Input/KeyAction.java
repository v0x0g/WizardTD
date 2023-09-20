package WizardTD.Input;

import lombok.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.event.*;

import java.util.*;

@ToString
@AllArgsConstructor
@ExtensionMethod(Arrays.class)
public enum KeyAction {
    PRESS(processing.event.KeyEvent.PRESS),
    RELEASE(KeyEvent.RELEASE),
    ;

    public final int code;

    public static @Nullable KeyAction fromInt(final int code) {
        return KeyAction
                .values()
                .stream()
                .filter((v) -> v.code == code)
                .findFirst()
                .orElse(null);
    }
}
