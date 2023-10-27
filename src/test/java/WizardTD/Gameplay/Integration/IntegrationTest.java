package WizardTD.Gameplay.Integration;

import WizardTD.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.*;
import processing.core.*;

public abstract class IntegrationTest {
    @SuppressWarnings("DataFlowIssue")
    @NonNull App app = null;
    
    @SneakyThrows
    @BeforeEach
    public void setUpApp() {
        app = new IntegrationTestApp();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        Thread.sleep(1000); // wait for it to be set up
    }
}