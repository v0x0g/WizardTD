package WizardTD.Gameplay.Integration;

import WizardTD.*;
import org.junit.jupiter.api.*;

import static java.lang.Thread.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleRunsTests extends IntegrationTest {
    public final static String CONFIG_PATH = TestResources.FULL_CFG_DIR + "simple_runs_tests_config.json";
    
    /**
     * Simple test that just checks the game will run properly
     */
    @Test
    public void simpleGameRuns() {
        setUpApp(CONFIG_PATH);
        assertFalse(app.finished);
    }
    
    @Test
    public void drawingWorks() throws InterruptedException {
        setUpApp(CONFIG_PATH);
        sleep(1000);
        assertFalse(app.finished);
        app.exit();
        sleep(1000);
        assertTrue(app.finished);
    }
}