package WizardTD.Event;

/**
 * Enum representing what different types of events can happen
 */
public enum EventType {
    /**
     * Called right at the beginning of the app start, just after `main()`
     */
    Bootstrap,

    /**
     * Called inside the `PApplet::setup()` method
     * 
     * Should be used to init graphics, sprites, etc
     */
    AppSetup,
    ;
}