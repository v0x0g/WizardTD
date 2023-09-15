package WizardTD.Event;

import org.checkerframework.checker.nullness.qual.*;

import java.lang.annotation.*;

/**
 * Event attribute to mark a method as subscribing to some given event(s)
 */
@Retention(RetentionPolicy.RUNTIME) // Read via reflection
@Target(ElementType.METHOD)
public @interface OnEvent {

    EventType @NonNull [] eventTypes();// default EventType.Init;

}