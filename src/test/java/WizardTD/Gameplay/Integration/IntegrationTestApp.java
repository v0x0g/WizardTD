package WizardTD.Gameplay.Integration;

import WizardTD.*;

public class IntegrationTestApp extends App {
    public IntegrationTestApp(final String configPath) throws AppInitException {
        super(configPath);
    }

    @Override
    public void exitActual() {
        // Don't do anything, normally crashes the app
    }
}
