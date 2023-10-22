package WizardTD.Gameplay.Projectiles;

import WizardTD.Rendering.*;
import WizardTD.*;
import lombok.*;
import mikera.vectorz.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Projectile implements Renderable, Tickable {

    public Vector2 position;

}
