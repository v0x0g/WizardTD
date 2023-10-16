package WizardTD.Event;

@FunctionalInterface
public interface EventMethod {
    void processEvent(Event event);
}
