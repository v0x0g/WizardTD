package WizardTD.Input;

import lombok.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class KeyPress {
    public final KeyCode keyCode;
    public final boolean isRepeat;
    public final KeyAction action;
}
