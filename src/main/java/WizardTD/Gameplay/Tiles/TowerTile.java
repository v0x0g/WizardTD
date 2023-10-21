package WizardTD.Gameplay.Tiles;

import WizardTD.*;
import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import WizardTD.UI.Appearance.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class TowerTile extends Tile {
    public static final double TOWER_UPGRADE_COST = 20.0;
    public static @Nullable PImage tileLevel0 = null;
    public static @Nullable PImage tileLevel1 = null;
    public static @Nullable PImage tileLevel2 = null;
    public long rangeUpgrades = 0;
    public long speedUpgrades = 0;
    public long damageUpgrades = 0;

    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(final Event event) {
        final PApplet app = (PApplet) event.dataObject;
        tileLevel0 = UiManager.loadImage(app, Resources.Tiles.Tower.Tile0);
        tileLevel1 = UiManager.loadImage(app, Resources.Tiles.Tower.Tile1);
        tileLevel2 = UiManager.loadImage(app, Resources.Tiles.Tower.Tile2);
    }

    @Override
    public void render(final PApplet app, final GameData gameData, final UiState uiState) {
        /*
         * NOTE: Due to tile rendering order, the upgrades might get cut off by other tiles
         * This is only an issue at stupid upgrade levels, so it's not really considered 
         */
        
        // Tower
        final long towerLevel = min(min(this.damageUpgrades, this.rangeUpgrades), this.damageUpgrades);
        final long cappedTowerLevel = min(2, towerLevel);
        
        final PImage tileImage = cappedTowerLevel == 0 ? tileLevel0 :
                                 cappedTowerLevel == 1 ? tileLevel1 :
                                 tileLevel2;

        Renderer.renderSimpleTile(app, tileImage, UiManager.tileToPixelCoords(this));
        final Vector2 pixelPos = UiManager.tileToPixelCoords(this);
        
        // Render speed upgrade
        final float SPEED_INDICATOR_RADIUS = 20.0f;
        app.rectMode(PConstants.CENTER);
        app.noFill();
        app.stroke(Theme.TOWER_UPGRADE_SPEED.asInt());
        final float speedIndicatorStrength = (this.speedUpgrades - cappedTowerLevel);
        app.strokeWeight(speedIndicatorStrength);
        app.rect((float) pixelPos.x, (float)pixelPos.y, SPEED_INDICATOR_RADIUS, SPEED_INDICATOR_RADIUS);
    }

    public void upgradeIfPossible(
            final GameData game,
            final boolean upgradeRange, final boolean upgradeSpeed, final boolean upgradeDamage) {

        if (upgradeRange && game.mana > TOWER_UPGRADE_COST) {
            game.mana -= TOWER_UPGRADE_COST;

            this.rangeUpgrades++;
            Loggers.GAMEPLAY.debug("upgrade tower range {}: {}", this, this.rangeUpgrades);
        }
        if (upgradeSpeed && game.mana > TOWER_UPGRADE_COST) {
            game.mana -= TOWER_UPGRADE_COST;

            this.speedUpgrades++;
            Loggers.GAMEPLAY.debug("upgrade tower speed {}: {}", this, this.speedUpgrades);
        }
        if (upgradeDamage && game.mana > TOWER_UPGRADE_COST) {
            game.mana -= TOWER_UPGRADE_COST;

            this.damageUpgrades++;
            Loggers.GAMEPLAY.debug("upgrade tower damage {}: {}", this, this.damageUpgrades);
        }
    }
}
