package WizardTD.Gameplay.Projectiles;

import WizardTD.Rendering.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Projectile extends Renderable {

    public @NonNull Vector2 position;

}
