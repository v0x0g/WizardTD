package WizardTD.Gameplay.Integration;

import org.junit.jupiter.api.*;

public abstract class IntegrationTest {
    @BeforeEach
    public void setUpHeadlessMode() {
//        System.setProperty("java.awt.headless", "true");
    }
}