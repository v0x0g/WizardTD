package WizardTD.Gameplay.Tiles;

import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import static java.lang.Math.*;

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

    /**
     * Internal counter representing how many fireballs we can fire.
     *
     * @See See {@link WizardTD.Gameplay.Spawners.Wave#enemySpawnCounter} for better explanation
     */
    public transient double magazine;

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
         * You need an upgrade of level 20+ which is dumb
         */

        // This is the lowest level hat has all upgrades
        final long towerLevel = min(min(this.damageUpgrades, this.rangeUpgrades), this.damageUpgrades);
        // Since we only have sprites up to level 2, we have to cap it :(
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
        app.rect((float) pixelPos.x, (float) pixelPos.y, SPEED_INDICATOR_RADIUS, SPEED_INDICATOR_RADIUS);
    }

    public void upgradeIfPossible(
            final GameData game,
            final boolean upgradeRange, final boolean upgradeSpeed, final boolean upgradeDamage) {

        // TODO: COST MIGHT BE WRONG I THINK?
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

    public double calculateDamage(final GameData game) {
        final double DAMAGE_INCREASE_MULT = 0.5;
        return game.config.tower.initialTowerDamage * (1 + (this.damageUpgrades * DAMAGE_INCREASE_MULT));
    }

    public double calculateSpeed(final GameData game) {
        final double SPEED_INCREASE_ADD = 0.5;
        return game.config.tower.initialTowerFiringSpeed + (this.speedUpgrades * SPEED_INCREASE_ADD);
    }

    /**
     * Calculates the tower's range (in tiles)
     */
    public double calculateRange(final GameData game) {
        final double RANGE_INCREASE_ADD = 1;
        return game.config.tower.initialTowerRange + ((1 + this.damageUpgrades) * RANGE_INCREASE_ADD);
    }

    @Override
    public void tick(final GameData game, final double gameDeltaTime, final double visualDeltaTime) {
        this.magazine += gameDeltaTime * this.calculateSpeed(game);
        // Clamp the magazine to 1, so we don't save up a massive burst of fireballs
        this.magazine = Math.min(1.0, magazine);
        
        if(this.magazine >= 1.0){
            this.magazine--;
            Loggers.GAMEPLAY.info("FIRE");
        }
    }
}
