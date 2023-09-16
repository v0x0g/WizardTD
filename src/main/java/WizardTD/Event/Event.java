package WizardTD.Event;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Event {

    public final @NonNull EventType eventType;

    public final @Nullable Object dataObject;

    public Event(@NonNull final EventType eventType) {
        this(eventType, null);
    }
}