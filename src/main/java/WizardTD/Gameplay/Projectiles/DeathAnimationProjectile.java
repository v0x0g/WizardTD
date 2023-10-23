package WizardTD.Gameplay.Projectiles;

import WizardTD.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.*;

/**
 * "Projectile" class for a dying enemy animation
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class DeathAnimationProjectile extends Projectile {
    public final ImageAnimation animation;
    public double progress = 0.0;
    
    public DeathAnimationProjectile(final Vector2 position, final ImageAnimation animation) {
        super(position);
        this.animation = animation;
    }

    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.ENTITY;
    }

    @Override
    public void render(final App app, final GameData gameData, final UiState uiState) {
        final PImage img = this.animation.getImage(progress);
        Renderer.renderSimpleEnemy(app,img, this.position);
    }

    @Override
    public void tick(final @NonNull GameData game, final double gameDeltaTime, final double visualDeltaTime) {
        this.progress += visualDeltaTime;
        if(this.progress >= this.animation.duration) GameManager.killProjectile(game, this);
    }
}
