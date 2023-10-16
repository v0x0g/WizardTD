package WizardTD.Input;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class KeyPress {
    public final KeyCode keyCode;
    public final boolean isRepeat;
    public final KeyAction action;
}
