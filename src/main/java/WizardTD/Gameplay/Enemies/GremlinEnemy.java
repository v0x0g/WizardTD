package WizardTD.Gameplay.Enemies;

import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode(callSuper = false)

public class GremlinEnemy extends Enemy {

    public GremlinEnemy(final double health, @NonNull final Vector2 position, final double speed, final double damageMultiplier, final double manaGainedOnKill) {
        super(health, position, speed, damageMultiplier, manaGainedOnKill);
    }

}
