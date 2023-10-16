package WizardTD.Gameplay.Enemies;

import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = false)
public class WormEnemy extends Enemy {

    public WormEnemy(final double health, @NonNull final Vector2 position, final double speed, final double damageMultiplier, final double manaGainedOnKill) {
        super(health, position, speed, damageMultiplier, manaGainedOnKill);
    }

    @Override
    public void render(@NonNull PApplet app, @NonNull GameData gameData, @NonNull UiState uiState) {
        Renderer.renderSimpleTile();
    }
}
