package WizardTD.Gameplay.Projectiles;

import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Projectile {

    public @NonNull Vector2 position;

}
