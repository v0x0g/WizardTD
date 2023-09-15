package WizardTD.Ext;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.event.*;

import java.util.*;

@ToString
@AllArgsConstructor
public enum MouseAction {
    CLICK(MouseEvent.CLICK), DRAG(MouseEvent.DRAG), ENTER(MouseEvent.ENTER), EXIT(MouseEvent.EXIT), MOVE(MouseEvent.MOVE), PRESS(MouseEvent.PRESS), RELEASE(MouseEvent.RELEASE), WHEEL(MouseEvent.WHEEL);

    final int actionCode;

    public static @NonNull Optional<@NonNull MouseAction> fromInt(final int code) {
        return Arrays.stream(MouseAction.values()).filter((v) -> v.actionCode == code).findFirst();
    }
}
