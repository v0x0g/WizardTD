package WizardTD.Gameplay.Projectiles;

import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import lombok.*;
import mikera.vectorz.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Projectile implements Renderable, Tickable {
    public Vector2 position;
    
    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.PROJECTILE;
    }

}
