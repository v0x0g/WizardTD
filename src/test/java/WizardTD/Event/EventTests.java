package WizardTD.Event;

import lombok.experimental.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtensionMethod(Arrays.class)
public class EventTests {
    @Test

    public void eventManagement() {
        // Test all methods can be subscribed, unsubscribed, and invoked correctly

        final EventType[] types = EventType.values();
        final Set<EventType> events = new HashSet<>();

        final EventMethod eventHandler = (Event event) -> events.add(event.eventType);

        // Don't want this
        // EventManager.reflectAndSubscribe();

        types.stream().forEach(type -> EventManager.subscribe(type, eventHandler));
        types.stream().forEach(type -> EventManager.invokeEvent(new Event(type)));
        assertAll(types.stream().map((type) -> () -> assertTrue(events.contains(type))));
        types.stream().forEach(type -> EventManager.unsubscribe(type, eventHandler));

        EventManager.reflectAndSubscribe();
    }
}
