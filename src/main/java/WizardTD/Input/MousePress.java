package WizardTD.Input;

import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class MousePress {
    public final @NonNull Vector2 coords;
    public final @NonNull MouseCode   code;
    public final          int         count;
    public final @NonNull MouseAction action;
}