package WizardTD.Input;

import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class MousePress {
    public final Vector2 coords;
    public final MouseCode   code;
    public final          int         count;
    public final MouseAction action;
}