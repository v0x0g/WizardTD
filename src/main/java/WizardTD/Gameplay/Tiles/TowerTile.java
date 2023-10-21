package WizardTD.Gameplay.Tiles;

import WizardTD.Event.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.Rendering.*;
import WizardTD.UI.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

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
        Renderer.renderSimpleTile(app, tileLevel0, UiManager.tileToPixelCoords(this));
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
