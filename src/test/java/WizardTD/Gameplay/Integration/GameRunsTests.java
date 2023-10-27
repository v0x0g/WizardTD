package WizardTD.Gameplay.Integration;

import WizardTD.*;
import org.junit.jupiter.api.*;
import processing.core.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameRunsTests extends IntegrationTest {
    @Test
    public void gameRuns() throws InterruptedException, App.AppInitException {
//        System.setProperty("java.awt.headless", "true");
//        App app = assertDoesNotThrow(IntegrationTestApp::new);
//        app.noLoop();
//        App.runSketch(new String[]{}, app);
//        Thread.sleep(10000);
//        app.exit();

        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
//        app.delay(5000);
    }
}