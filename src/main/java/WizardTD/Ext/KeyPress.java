package WizardTD.Ext;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class KeyPress {
    public final @NonNull KeyCode keyCode;
    public final boolean isRepeat;
    public final boolean isPressDown;
}
