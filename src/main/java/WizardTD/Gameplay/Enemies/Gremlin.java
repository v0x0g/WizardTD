package WizardTD.Gameplay.Enemies;

import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode(callSuper = false)

public class Gremlin extends Enemy {

    public Gremlin(final long health, @NonNull final Vector2 position, final double speed, final double damageMultiplier, final long manaGainedOnKill) {
        super(health, position, speed, damageMultiplier, manaGainedOnKill);
    }

}
