package WizardTD.Gameplay.Projectiles;

import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class FireballProjectile extends Projectile {

    /**
     * How many units (tiles) per frame the fireball moves
     */
    public static final double FIREBALL_SPEED = 5.0;

    /**
     * How much damage the fireball should do upon striking an enemy. Note that this is base damage,
     * and will be affected by the damage multiplier
     */
    public double baseDamage;

    public FireballProjectile(final Vector2 position, final double baseDamage) {
        super(position);
        this.baseDamage = baseDamage;
    }

    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.PROJECTILE;
    }

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        throw null;
    }
}
