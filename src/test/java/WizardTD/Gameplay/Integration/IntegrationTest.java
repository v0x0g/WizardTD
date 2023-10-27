package WizardTD.Gameplay.Integration;

import WizardTD.*;
import lombok.*;
import org.junit.jupiter.api.*;
import processing.core.*;

public abstract class IntegrationTest {
    public App app = null;

    @SneakyThrows
    public void setUpApp(final String configPath) {
        app = new IntegrationTestApp(configPath);
        app.noLoop();
        PApplet.runSketch(new String[]{"App"}, app);
        app.setup();
        Thread.sleep(1000); // wait for it to be set up
    }
    
    @AfterEach
    public void disposeApp() {
        if (app != null && !app.finished) {
            app.exit();
        }
    }
}