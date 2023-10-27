package WizardTD.Gameplay.Game;

import WizardTD.*;
import lombok.experimental.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtensionMethod(Arrays.class)
public class GameLoaderTests {
    @Test
    public void validConfigs() {
        // Assert they all load nicely
        assertAll(
                TestResources.VALID_CONFIGS
                        .stream()
                        .map(GameLoader::loadGameDescriptor)
                        .map(g -> () -> Assertions.assertNotNull(g))
        );
    }

    @Test
    public void invalidConfigs() {
        assertAll(
                TestResources.INVALID_CONFIGS
                        .stream()
                        .map(GameLoader::loadGameDescriptor)
                        .map(g -> () -> Assertions.assertNull(g))
        );
    }

    @Test
    public void validLevels() {
        // Assert they all load nicely
        assertAll(
                TestResources.VALID_LEVELS
                        .stream()
                        .map(GameLoader::loadLevelLayoutFile)
                        .map(lines -> lines != null ? GameLoader.parseBoard(lines) : null)
                        .map(g -> () -> Assertions.assertNotNull(g))
        );
    }

    @Test
    public void invalidLevels() {
        assertAll(
                TestResources.INVALID_LEVELS
                        .stream()
                        .map(GameLoader::loadLevelLayoutFile)
                        .map(lines -> lines != null ? GameLoader.parseBoard(lines) : null)
                        .map(g -> () -> Assertions.assertNull(g))
        );
    }
}