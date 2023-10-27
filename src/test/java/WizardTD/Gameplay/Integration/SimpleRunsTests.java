package WizardTD.Gameplay.Integration;

import org.junit.jupiter.api.*;

import static java.lang.Thread.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleRunsTests extends IntegrationTest {
    @Test
    public void simpleGameRuns() throws InterruptedException {
        sleep(1000);
        assertFalse(app.finished);
        app.exit();
        sleep(1000);
        assertTrue(app.finished);
    }
}