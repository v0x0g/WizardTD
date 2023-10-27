package WizardTD.Gameplay.Tiles;

import WizardTD.*;
import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Gameplay.Projectiles.*;
import WizardTD.Rendering.*;
import WizardTD.UI.Appearance.*;
import WizardTD.UI.*;
import lombok.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

import static java.lang.Math.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class TowerTile extends Tile {
    public static final double TOWER_UPGRADE_COST = 20.0;
    public static final double TOWER_UPGRADE_COST_INCREASE = 10.0;
    public static final double RANGE_INCREASE_ADD = 1;
    public static final double SPEED_INCREASE_ADD = 0.5;
    public static final double DAMAGE_INCREASE_MULT = 0.5;

    public static @Nullable PImage tileLevel0 = null;
    public static @Nullable PImage tileLevel1 = null;
    public static @Nullable PImage tileLevel2 = null;

    public long rangeUpgrades = 0;
    public long speedUpgrades = 0;
    public long damageUpgrades = 0;

    /**
     * Internal counter representing how many fireballs we can fire.
     *
     * @see WizardTD.Gameplay.Spawners.Wave#enemySpawnCounter
     */
    public transient double magazine;

    @SuppressWarnings({"unused", "DataFlowIssue"})
    @OnEvent(eventTypes = EventType.AppSetup)
    private static void loadImages(final Event event) {
        final App app = (App) event.dataObject;
        tileLevel0 = UiManager.loadImage(app, Resources.Tiles.Tower.Tile0);
        tileLevel1 = UiManager.loadImage(app, Resources.Tiles.Tower.Tile1);
        tileLevel2 = UiManager.loadImage(app, Resources.Tiles.Tower.Tile2);
    }

    @Override
    public void render(final App app, final GameData gameData, final UiState uiState) {
        /*
         * NOTE: Due to tile rendering order, the upgrades might get cut off by other tiles
         * This is only an issue at stupid upgrade levels, so it's not really considered
         * You need an upgrade of level 20+ which is dumb
         */

        // This is the lowest level that has all upgrades
        final long towerLevel = min(min(this.damageUpgrades, this.rangeUpgrades), this.speedUpgrades);
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
        // Special maths: `ln(x^2 + 1)`
        final double speedIndicatorWidth = Math.log(Math.pow(this.speedUpgrades - cappedTowerLevel, 2) + 1);
        // `1 - a/(x+a), a=20`
        final double speedIndicatorStrength = 1.0 - (20.0 / (this.speedUpgrades - cappedTowerLevel + 20.0));
        app.strokeWeight((float) speedIndicatorWidth);
        app.stroke(Colour.lerp(Theme.TOWER_UPGRADE_SPEED, Colour.BLACK, speedIndicatorStrength).asInt());
        app.rect((float) pixelPos.x, (float) pixelPos.y, SPEED_INDICATOR_RADIUS, SPEED_INDICATOR_RADIUS);

        // Render range upgrade
        final float RANGE_INDICATOR_SIZE = 10.0f;
        app.rectMode(PConstants.CENTER);
        app.textAlign(PConstants.LEFT);
        app.fill(Theme.TOWER_UPGRADE_RANGE.asInt());
        final int numRangeIndicators = (int) (this.rangeUpgrades - cappedTowerLevel);
        app.textSize(RANGE_INDICATOR_SIZE);
        final Vector2 rangePos = (Vector2) pixelPos.addCopy(new Vector2(-16, -12));
        final StringBuilder rangeStr = new StringBuilder();
        for (int i = 0; i < numRangeIndicators; i++) rangeStr.append('O');
        app.text(rangeStr.toString(), (float) rangePos.x, (float) rangePos.y);

        // Render damage upgrade
        final float DAMAGE_INDICATOR_SIZE = 10.0f;
        app.rectMode(PConstants.CENTER);
        app.textAlign(PConstants.LEFT);
        app.fill(Theme.TOWER_UPGRADE_DAMAGE.asInt());
        final int numDamageIndicators = (int) (this.damageUpgrades - cappedTowerLevel);
        app.textSize(DAMAGE_INDICATOR_SIZE);
        final Vector2 damagePos = (Vector2) pixelPos.addCopy(new Vector2(-16, 12));
        final StringBuilder damageStr = new StringBuilder();
        for (int i = 0; i < numDamageIndicators; i++) damageStr.append('X');
        app.text(damageStr.toString(), (float) damagePos.x, (float) damagePos.y);
    }

    /**
     * Upgrades the tower, if there are upgrades that can be afforded.
     */
    public void upgradeIfPossible(
            final GameData game,
            final boolean upgradeRange, final boolean upgradeSpeed, final boolean upgradeDamage) {

        if (upgradeRange && game.mana > this.rangeCost()) {
            game.mana -= this.rangeCost();

            this.rangeUpgrades++;
            Loggers.GAMEPLAY.debug("upgrade tower range {}: {}", this, this.rangeUpgrades);
        }
        if (upgradeSpeed && game.mana > this.speedCost()) {
            game.mana -= this.speedCost();

            this.speedUpgrades++;
            Loggers.GAMEPLAY.debug("upgrade tower speed {}: {}", this, this.speedUpgrades);
        }
        if (upgradeDamage && game.mana > this.damageCost()) {
            game.mana -= this.damageCost();

            this.damageUpgrades++;
            Loggers.GAMEPLAY.debug("upgrade tower damage {}: {}", this, this.damageUpgrades);
        }
    }

    public double calculateDamage(final GameData game) {
        return game.config.tower.initialTowerDamage * (1 + (this.damageUpgrades * DAMAGE_INCREASE_MULT));
    }

    public double calculateSpeed(final GameData game) {
        return game.config.tower.initialTowerFiringSpeed + (this.speedUpgrades * SPEED_INCREASE_ADD);
    }

    /**
     * Calculates the tower's range (in tiles)
     */
    public double calculateRange(final GameData game) {
        return game.config.tower.initialTowerRange + (this.rangeUpgrades * RANGE_INCREASE_ADD);
    }

    /**
     * Calculates the cost of upgrading the tower's damage
     */
    public double damageCost() {
        return TOWER_UPGRADE_COST + (TOWER_UPGRADE_COST_INCREASE * this.damageUpgrades);
    }

    /**
     * Calculates the cost of upgrading the tower's speed
     */
    public double speedCost() {
        return TOWER_UPGRADE_COST + (TOWER_UPGRADE_COST_INCREASE * this.speedUpgrades);
    }

    /**
     * Calculates the cost of upgrading the tower's range
     */
    public double rangeCost() {
        return TOWER_UPGRADE_COST + (TOWER_UPGRADE_COST_INCREASE * this.rangeUpgrades);
    }

    @Override
    public void tick(final @NonNull GameData game, final double gameDeltaTime, final double visualDeltaTime) {
        this.magazine += gameDeltaTime * this.calculateSpeed(game);

        // Can shoot multiple times per frame
        while (true) {
            // Don't have any fireballs
            if (this.magazine < 1.0) return;

            final double range = this.calculateRange(game);

            final Vector2 thisPos = new Vector2(this.getPos().getX(), this.getPos().getY());
            final Enemy enemy = GameManager.getNextEnemy(game, thisPos, range);

            if (enemy == null) {
                // Clamp the magazine, so we don't save up a massive burst of fireballs
                // when there are no enemies
                // If there are enemies nearby, it's fine
                this.magazine = Math.min(1.0, magazine);

                return;
            }

            this.magazine--;

            final double damage = this.calculateDamage(game);

            final FireballProjectile fireball = new FireballProjectile(thisPos, enemy, damage);
            game.projectiles.add(fireball);
            Loggers.GAMEPLAY.info("tower fire: tower={}, target={}, fireball={}", this, enemy, fireball);
        }
    }
}