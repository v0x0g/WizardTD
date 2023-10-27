package WizardTD.Gameplay.Integration;

import WizardTD.Gameplay.Game.*;
import WizardTD.*;
import WizardTD.UI.*;
import WizardTD.UI.InteractableElements.*;
import mikera.vectorz.*;
import org.junit.jupiter.api.*;

import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

public class UiTests extends IntegrationTest {
    public final static String CONFIG_PATH = TestResources.FULL_CFG_DIR + "full_test_1_config.json";

    @Test
    public void sidebarButtonsClick(){
        setUpApp(CONFIG_PATH);
        
        final UiState ui = app.uiState;
        assertFalse(ui.upgradeRange);
        assertFalse(ui.upgradeSpeed);
        assertFalse(ui.upgradeDamage);
        assertFalse(ui.wantsPlaceTower);
        assertFalse(app.gameData.paused);
        assertFalse(app.gameData.fastForward);
        
        ui.uiElements
                .stream()
                .filter(ButtonElement.class::isInstance)
                .map(ButtonElement.class::cast)
                .forEach(b -> b.activate(app ,app.gameData, ui));

        assertTrue(ui.wantsPlaceTower);
        assertTrue(ui.upgradeRange);
        assertTrue(ui.upgradeSpeed);
        assertTrue(ui.upgradeDamage);
        assertTrue(app.gameData.paused);
        assertTrue(app.gameData.fastForward);
    }

    @Test
    public void sidebarButtonsHover(){
        setUpApp(CONFIG_PATH);
        
        app.uiState.uiElements
                .stream()
                .filter(InteractiveElement.class::isInstance)
                .map(InteractiveElement.class::cast)
                .forEach(e -> assertFalse(e.isMouseOver(new Vector2(-9999, -99999))));
        
        // Check that the board button reacts to hover
        // Don't know which one it is exactly, so just OR them all together
        assertTrue(app.uiState.uiElements
                .stream()
                .filter(InteractiveElement.class::isInstance)
                .map(InteractiveElement.class::cast)
                // Should hover over game tile
                .map(e -> e.isMouseOver(new Vector2(320, 320)))
                .reduce(false, Boolean::logicalOr));
    }

    @Test
    public void debugOverlays() throws InterruptedException {
        setUpApp(CONFIG_PATH);
        
        Debug.f3OverlayEnabled = true;
        Debug.pathfindingOverlayEnabled = true;
        Debug.tileHoverOverlayEnabled = true;
        Debug.towerUpgradeOverlayEnabled = true;
        
        Thread.sleep(1000);
        
        Debug.f3OverlayEnabled = false;
        Debug.pathfindingOverlayEnabled = false;
        Debug.tileHoverOverlayEnabled = false;
        Debug.towerUpgradeOverlayEnabled = false;
        
        Thread.sleep(1000);
        
        // Check values are being updated
        assertTrue(Debug.Stats.Frames.frameCount > 0);
        assertTrue(Debug.Stats.Frames.thisTick > 0);
        assertTrue(Debug.Stats.Frames.lastTick > 0);
        assertTrue(Debug.Stats.Frames.avgFps > 0);
        assertTrue(Debug.Stats.Frames.deltaTime > 0);
        assertTrue(Debug.Stats.Frames.fps > 0);
    }
}
