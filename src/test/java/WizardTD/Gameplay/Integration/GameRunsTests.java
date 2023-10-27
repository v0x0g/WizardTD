package WizardTD.Gameplay.Integration;

import WizardTD.*;
import org.junit.jupiter.api.*;
import processing.core.*;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class GameRunsTests extends IntegrationTest {
    @Test
    public void gameRuns() throws InterruptedException {
        sleep(1000);
        assertFalse(app.finished);
        app.exit();
        sleep(1000);
        assertTrue(app.finished);
    }
}