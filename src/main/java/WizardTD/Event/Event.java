package WizardTD.Event;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Event {

    public final @NonNull EventType eventType;

}