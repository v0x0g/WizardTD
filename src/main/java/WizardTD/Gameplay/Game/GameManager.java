package WizardTD.Gameplay.Game;

import WizardTD.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Projectiles.*;
import WizardTD.Gameplay.Spawners.*;
import WizardTD.Gameplay.Tiles.*;
import WizardTD.Rendering.*;
import com.google.common.collect.Streams;
import lombok.experimental.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;

import java.util.*;
import java.util.concurrent.*;

import static WizardTD.GameConfig.*;

/**
 * Static class that handles all the game 
 */
@UtilityClass
public class GameManager {
    /**
     * This contains cached HashMaps and Lists for use when grouping objects for ticking.
     * This allows memory reuse (lists and maps are pooled), massively increasing performance
     * and reducing allocations.
     *
     * @see Renderer#renderOrderMaps
     */
    private static final ThreadLocal<List<Tickable>> cachedTickableLists = ThreadLocal.withInitial(ArrayList::new);

    /**
     * Ticks (updates) the game.
     * <p>
     * It ticks would be too large, will split them up into smaller sub-ticks
     *
     * @param app       The app instance
     * @param game      The object storing the game data
     * @param deltaTime Time between the last frame start and the current frame start
     */
    public static void tickGameWithSubtick(final App app, final GameData game, final double deltaTime) {
        /*
         * If the tick takes too long, split it up into multiple smaller sub-ticks
         * This should keep everything accurate, since we aren't making massive long ticks
         * No simulation errors, yay!
         *
         * This essentially calculates how many ticks (`numTicks`) we need to have each tick be under the threshold
         * and does that many ticks, with each tick being (`deltaTime/numTicks`)
         */

        final double speedMultiplier = game.fastForward ? FAST_FORWARD_SPEED : 1.0;

        int numTicks = (int) Math.ceil(deltaTime * speedMultiplier / SUB_TICK_THRESHOLD);
        if (numTicks > MAX_SUB_TICKS_PER_FRAME) {
            Loggers.EVENT.warn(
                    "had to throttle num sub-ticks: wanted {} (max {}), delta={} ({}), mult = {}", numTicks,
                    MAX_SUB_TICKS_PER_FRAME, deltaTime, deltaTime * speedMultiplier, speedMultiplier
            );
            numTicks = MAX_SUB_TICKS_PER_FRAME;
        }
        Loggers.EVENT.trace(
                "deltaTime = {}, thresh = {}, mult = {}, numTicks = {}",
                deltaTime,
                SUB_TICK_THRESHOLD,
                speedMultiplier,
                numTicks
        );

        final double gameDelta = game.paused ? 0.0 : deltaTime * speedMultiplier / numTicks;
        final double visualDelta = deltaTime * speedMultiplier / numTicks;

        for (int i = 0; i < numTicks; i++) {
            Loggers.EVENT.trace("subtick: game={}; visual={}", gameDelta, visualDelta);
            // Only tick game if we are still playing
            // This will freeze the game in the last frame on win/loss
            if (game.gameState == GameState.PLAYING)
                internalTickGame(app, game, gameDelta, visualDelta);
        }

        Debug.Stats.Tick.numTicks = numTicks;
        Debug.Stats.Tick.subTickThresh = SUB_TICK_THRESHOLD;
        Debug.Stats.Tick.speedMultiplier = speedMultiplier;
        Debug.Stats.Tick.gameDelta = gameDelta;
        Debug.Stats.Tick.visualDelta = visualDelta;
    }

    /**
     * Ticks (updates) the game.
     * Will not sub-tick, hence it's internal
     *
     * @param app             The app instance
     * @param game            The object storing the game data
     * @param gameDeltaTime   Time between the last frame start and the current frame start (aka delta-time).
     * @param visualDeltaTime Delta-time used for visual purposes, such as animations and UI
     */
    private static void internalTickGame(
            final App app, final GameData game, final double gameDeltaTime, final double visualDeltaTime) {
        // Absorb mana through the atmosphere using mana accumulators
        game.mana += gameDeltaTime * game.config.mana.initialManaTrickle * game.manaGainMultiplier;
        game.mana = Math.min(game.mana, game.manaCap);

        // Spawn Enemies
        while (!game.waves.isEmpty()) {
            final Wave wave = game.waves.get(0);
            wave.tick(gameDeltaTime);
            if (wave.getWaveState() == Wave.WaveState.COMPLETE) {
                Loggers.GAMEPLAY.debug("wave {} complete, moving onto next: {}  -->  {}", wave.waveNumber, wave);
                game.waves.remove(0);
                continue;
            }

            Enemy enemy;
            while (null != (enemy = wave.getEnemy())) {
                game.enemies.add(enemy);
                final ThreadLocalRandom rng = ThreadLocalRandom.current();
                // Choose a random path for the enemy to go along
                final int pathIdx = rng.nextInt(game.enemyPaths.size());
                final EnemyPath path = game.enemyPaths.get(pathIdx);
                Loggers.GAMEPLAY.trace("spawn enemy {}; path = [{}]: {}", enemy, pathIdx, path);
                enemy.path = path;
            }

            break;
        }

        // Tick all enemies, projectiles, etc.
        // Because ticking might involve modification of the streams (such as removing enemies that die)
        // We need to create a copy of the collections
        // So use a (pooled) list
        final List<Tickable> tickers = cachedTickableLists.get();
        tickers.clear(); // Reset the list
        Streams.<Tickable>concat(game.enemies.stream(), game.board.stream(), game.projectiles.stream())
               .forEach(tickers::add);
        tickers.forEach(e -> e.tick(game, gameDeltaTime, visualDeltaTime));

        // Check if enemies have reached harry potter
        game.enemies.forEach(enemy -> {
            // If the enemy is closer than this threshold, they have reached the house
            // Banish them
            final double DISTANCE_THRESHOLD = 1.0;
            if (Math.abs(enemy.pathProgress - enemy.path.positions.length) < DISTANCE_THRESHOLD) {
                Loggers.GAMEPLAY.debug("enemy {} reached end of path (wizard)", enemy);
                enemy.pathProgress = 0.0;
                // Choose a random path for the enemy to go along
                final int pathIdx = ThreadLocalRandom.current().nextInt(game.enemyPaths.size());
                final EnemyPath path = game.enemyPaths.get(pathIdx);
                Loggers.GAMEPLAY.trace("switch path {}; path = [{}]: {}", enemy, pathIdx, path);
                enemy.path = path;
                game.mana -= enemy.health;
            }
        });

        // Update win/loss status
        if (game.gameState == GameState.PLAYING && game.mana <= 0.0) {
            game.gameState = GameState.LOST;
            Loggers.GAMEPLAY.info("game lost (out of mana)");
        }
        else if (game.gameState == GameState.PLAYING && game.waves.isEmpty() && game.enemies.isEmpty()) {
            game.gameState = GameState.WON;
            Loggers.GAMEPLAY.info("game won (no more waves)");
        }
    }

    public void killEnemy(final GameData game, final Enemy enemy) {
        if (!enemy.isAlive) {
            Logger.warn("can't kill enemy {}, already dead", enemy);
            return;
        }

        if (!game.enemies.remove(enemy)) {
            Logger.warn("can't kill enemy {}, doesn't exist in game", enemy);
            return;
        }

        Loggers.GAMEPLAY.debug("kill enemy {}", enemy);

        enemy.isAlive = false;
        enemy.health = 0.0;
        game.mana += enemy.manaGainedOnKill;
        game.mana = Math.min(game.mana, game.manaCap);
        enemy.onDeath(game);
    }

    /**
     * Deals damage to an enemy, killing them if their health reaches zero
     *
     * @param baseDamage Base damage to be dealt (before damage multipliers)
     */
    public void damageEnemy(final GameData game, final Enemy enemy, final double baseDamage) {
        if (!enemy.isAlive) return;
        final double dmg = baseDamage * enemy.damageMultiplier;
        enemy.health -= dmg;
        Loggers.GAMEPLAY.trace(
                "damage enemy {}: {} x {} ({}) -> {} hp",
                enemy,
                baseDamage,
                enemy.damageMultiplier,
                dmg,
                enemy.health
        );
        if (enemy.health <= 0) killEnemy(game, enemy);
    }

    public void killProjectile(final GameData game, final Projectile projectile) {
        if (!game.projectiles.remove(projectile)) {
            Logger.warn("can't remove projectile {}, doesn't exist in game", projectile);
        }
        Loggers.GAMEPLAY.debug("kill projectile {}", projectile);
    }

    public @Nullable Enemy getNextEnemy(final GameData game, final Vector2 nearPos, final double maxDist) {
        // This is a linear search, but it shouldn't be an issue since we should be < 1000 elems
        // We also add a little bit of randomness so that we don't always target the same enemy with multiple projectiles
        final long RANDOMISE = 10;
        final Enemy[] enemies = game.enemies.stream()
                                            .filter(enemy -> nearPos.distance(enemy.position) < maxDist)
//                           .sorted(Comparator.comparingDouble(enemy -> nearPos.distanceSquared(enemy.position)))
                                            .sorted(Comparator.comparingDouble(
                                                    enemy -> enemy.path.positions.length - enemy.pathProgress))
                                            .limit(RANDOMISE)
                                            .toArray(Enemy[]::new);
        if (enemies.length == 0) return null;
        return enemies[ThreadLocalRandom.current().nextInt(enemies.length)];
    }

    /**
     * Updates the pathfinding for the board (rescans the board)
     */
    public void updatePathfinding(final GameData game) {
        Loggers.GAMEPLAY.info("updating pathfinding");
        game.enemyPaths = Pathfinder.findPaths(game.board);
        // Randomise the ordering of the list, to make it a little spicier
        for (int i = 0; i < 10; i++) Collections.shuffle(game.enemyPaths);
        Loggers.GAMEPLAY.info("pathfinding done");
    }

    /**
     * Places a tower at the given tile, or upgrades it if there is one present.
     */
    public void placeOrUpgradeTower(
            final GameData game, final Tile tile,
            final boolean upgradeRange, final boolean upgradeSpeed, final boolean upgradeDamage) {
        final TowerTile tower;
        if (tile instanceof TowerTile) {
            // Already existing tower
            tower = (TowerTile) tile;
            Loggers.GAMEPLAY.debug("preexisting tower at {}", tower.getPos());
        }
        else if (tile instanceof GrassTile) {
            // Can place a tower there
            if (game.mana > game.config.tower.towerCost) {
                game.mana -= game.config.tower.towerCost;
                tower = new TowerTile();
                game.board.setTile(tile.getPos().getX(), tile.getPos().getY(), tower);
                Loggers.GAMEPLAY.debug("placed new tower at {}", tile.getPos());
            }
            else {
                // Want to place tile but no mana
                Loggers.GAMEPLAY.debug("insufficient mana to place tower");
                return;
            }
        }
        else {
            Loggers.GAMEPLAY.debug("tile not grass or tower, ignoring");
            return;
        }

        // Now apply upgrades, if possible
        tower.upgradeIfPossible(game, upgradeRange, upgradeSpeed, upgradeDamage);
    }

    /**
     * Removes a tower at the given tile's position
     */
    public void removeTower(final GameData game, final Tile tile) {
        if (!(tile instanceof TowerTile)) return;
        game.board.setTile(tile.getPos().getX(), tile.getPos().getY(), new GrassTile());
    }

}