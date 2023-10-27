package WizardTD.Gameplay.Integration;

import WizardTD.*;

public class IntegrationTestApp extends App {
    public IntegrationTestApp() throws AppInitException {
        super();
    }

    @Override
    public void exitActual() {
        // Don't do anything, normally crashes the app
    }
}
