package WizardTD.Event;

import org.checkerframework.checker.nullness.qual.*;

@FunctionalInterface
public interface EventMethod {
    void processEvent(Event event);
}
