package WizardTD.Gameplay.Projectiles;

import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode(callSuper = false)
public class Fireball extends Projectile {

    /**
     * How many units (tiles) per frame the fireball moves
     */
    public static final double FIREBALL_SPEED = 5.0;

    /**
     * How much damage the fireball should do upon striking an enemy. Note that this is base damage,
     * and will be affected by the damage multiplier
     */
    public double baseDamage;

    public Fireball(@NonNull final Vector2 position, final double baseDamage) {
        super(position);
        this.baseDamage = baseDamage;
    }

}
