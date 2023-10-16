package WizardTD.Event;

import java.lang.annotation.*;

/**
 * Event attribute to mark a method as subscribing to some given event(s)
 * <p>
 * You will most likely have methods marked with this attribute considered "unused",
 * however this is not the case, and
 */
@Retention(RetentionPolicy.RUNTIME) // Read via reflection
@Target(ElementType.METHOD)
@SuppressWarnings("unused")
public @interface OnEvent {

    EventType[] eventTypes();// default EventType.Init;

}