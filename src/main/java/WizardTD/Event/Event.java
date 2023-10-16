package WizardTD.Event;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Event {

    public final EventType eventType;
    // TODO: Redo with fancy enums?
    public final @Nullable Object dataObject;

    public Event(final EventType eventType) {
        this(eventType, null);
    }
}