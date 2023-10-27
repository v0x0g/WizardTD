package WizardTD.Gameplay.Integration;

import org.junit.jupiter.api.*;

import static java.lang.Thread.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleRunsTests extends IntegrationTest {
    /**
     * Simple test that just checks the game will run properly
     */
    @Test
    public void simpleGameRuns() throws InterruptedException {
        // Only just started
        assertFalse(app.finished);
        app.exit();
        sleep(1000);
        assertTrue(app.finished);
    }
    
    @Test
    public void drawingWorks() throws InterruptedException {
        
        sleep(1000);
        assertFalse(app.finished);
        app.exit();
        sleep(1000);
        assertTrue(app.finished);
    }
}