package WizardTD.Event;

import java.lang.annotation.*;

/**
 * Event attribute to mark a method as subscribing to a given event
 */
@Retention(RetentionPolicy.RUNTIME) // Read via reflection
@Target(ElementType.METHOD)
@Repeatable(OnEvents.class)
public @interface OnEvent {

    EventType event();// default EventType.Init;

}