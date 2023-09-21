package WizardTD.Gameplay.Enemies;

import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;
import sun.reflect.generics.reflectiveObjects.*;

@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Enemy extends Renderable {

    /**
     * How much health the enemy has remaining
     */
    public long health;

    /**
     * Where the enemy is on the map
     */
    public @NonNull Vector2 position;

    /**
     * How many units (tiles) per frame the monster moves
     */
    public double speed;

    /**
     * Multiplier on how much damage the enemy takes
     */
    public double damageMultiplier;

    /**
     * How much mana the wizard will gain upon killing this enemy
     */
    public long manaGainedOnKill;

    @Override
    public @NonNull RenderOrder getRenderOrder() {
        throw new NotImplementedException();
    }

    @Override
    public void render(@NonNull final PApplet app, @NonNull GameData gameData, @NonNull UiState uiState) {
        throw new NotImplementedException();
    }
}
