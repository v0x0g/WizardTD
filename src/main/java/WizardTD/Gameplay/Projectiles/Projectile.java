package WizardTD.Gameplay.Projectiles;

import WizardTD.Rendering.*;
import lombok.*;
import mikera.vectorz.*;

@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Projectile extends Renderable {

    public Vector2 position;

}
