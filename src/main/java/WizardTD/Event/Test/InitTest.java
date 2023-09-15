package WizardTD.Event.Test;

import WizardTD.Event.*;
import org.checkerframework.checker.nullness.qual.*;

public class InitTest {
    @OnEvent(eventTypes = {EventType.Init, EventType.LoadGraphics})
    public static void Test(@NonNull Event evt){
        
    }
}
