package WizardTD.Gameplay.Game;

import WizardTD.*;
import WizardTD.Delegates.*;
import WizardTD.Ext.*;
import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Pathfinding.*;
import WizardTD.Gameplay.Projectiles.*;
import WizardTD.Gameplay.Spawners.*;
import WizardTD.Gameplay.Spells.*;
import WizardTD.Gameplay.Tiles.*;
import WizardTD.Rendering.*;
import com.google.common.collect.Streams;
import lombok.experimental.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.dataflow.qual.*;
import org.tinylog.*;
import processing.core.*;
import processing.data.*;

import java.io.*;
import java.math.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

import static WizardTD.Ext.JsonExt.*;
import static WizardTD.GameConfig.*;
import static java.util.Optional.*;
import static org.tinylog.Logger.*;

/**
 * Static class that
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
     * Loads the game config from disk, and returns it as a JSON object
     */
    @SideEffectFree
    private Optional<JSONObject> loadGameConfig() {
        debug("loading game config");
        final Path path = Paths.get(WizardTD.GameConfig.CONFIG_PATH);
        trace("config path={}", path);
        final String dataStr;
        try {
            trace("reading config file");
            dataStr = new String(Files.readAllBytes(path));
            trace("config file read: contents are \"{}\"", dataStr);
        }
        catch (final IOException e) {
            debug(e, "error reading config file");
            return empty();
        }
        try {
            final JSONObject data = JSONObject.parse(dataStr);
            trace("parsed config: {}", data);
            return Optional.of(data);
        }
        catch (final RuntimeException e) {
            debug(e, "error parsing config");
            return empty();
        }
    }

    /**
     * Loads the given level layout file from disk
     */
    @SideEffectFree
    private Optional<List<String>> loadLevelLayoutFile(final String fileName) {
        debug("loading level layout");
        final Path path = Paths.get(fileName);
        trace("level layout path={}", path);
        final List<String> dataStr;
        try {
            trace("reading layout file");
            dataStr = Files.readAllLines(path);
            trace(
                    "layout file read: contents are \n\"\n{}\n\"",
                    String.join(
                            "\n",
                            (Iterable<String>) dataStr
                                    .stream()
                                    .map((s) -> "'" + s + "'")::iterator
                    )
            );

            return Optional.of(dataStr);
        }
        catch (final IOException e) {
            debug(e, "error reading layout file");
            return empty();
        }
    }

    /**
     * Loads the game descriptor from the disk
     * <p>
     * This can then be used to actually instantiate the gameData object
     */
    @SideEffectFree
    public @Nullable GameDescriptor loadGameDescriptor() {
        debug("loading game descriptor");

        // Load the config object
        final Optional<JSONObject> maybeConfigJson = loadGameConfig();
        if (!maybeConfigJson.isPresent()) {
            debug("can't load game: failed getting config");
            return null;
        }
        final JSONObject conf = maybeConfigJson.get();

        // Load a bunch of stuff
        // Any invalid json access will throw a RuntimeException
        // So catch them all in one go to simplify this
        final GameDataConfig gameDataConfig;
        try {
            // Names are purposefully like this to match JSON
            final double initial_tower_range = conf.getDouble("initial_tower_range") / TILE_SIZE_PX;
            final double initial_tower_firing_speed = conf.getDouble("initial_tower_firing_speed");
            final double initial_tower_damage = conf.getDouble("initial_tower_damage");
            final double initial_mana = conf.getDouble("initial_mana");
            final double initial_mana_cap = conf.getDouble("initial_mana_cap");
            final double initial_mana_gained_per_second = conf.getDouble("initial_mana_gained_per_second");
            final double tower_cost = conf.getDouble("tower_cost");
            final double mana_pool_spell_initial_cost = conf.getDouble("mana_pool_spell_initial_cost");
            final double mana_pool_spell_cost_increase_per_use =
                    conf.getDouble("mana_pool_spell_cost_increase_per_use");
            final double mana_pool_spell_cap_multiplier = conf.getDouble("mana_pool_spell_cap_multiplier");
            final double mana_pool_spell_mana_gained_multiplier =
                    conf.getDouble("mana_pool_spell_mana_gained_multiplier");

            gameDataConfig = new GameDataConfig(
                    new GameDataConfig.TowerConfig(initial_tower_range, initial_tower_firing_speed,
                                                   initial_tower_damage, tower_cost
                    ), new GameDataConfig.ManaConfig(initial_mana, initial_mana_cap, initial_mana_gained_per_second),
                    new GameDataConfig.SpellConfig(new GameDataConfig.SpellConfig.ManaPool(
                            mana_pool_spell_initial_cost, mana_pool_spell_cost_increase_per_use,
                            mana_pool_spell_mana_gained_multiplier, mana_pool_spell_cap_multiplier
                    ))
            );
            trace("level config={}", gameDataConfig);
        }
        catch (final RuntimeException e) {
            debug(e, "can't load level: failed parsing config json: (simple values)");
            return null;
        }

        final Board board;
        try {
            final String layoutFileName = conf.getString("layout");
            trace("level layout file at {}", layoutFileName);
            final Optional<List<String>> maybeLayoutData = loadLevelLayoutFile(layoutFileName);
            if (!maybeLayoutData.isPresent()) {
                debug("can't load level: couldn't load layout");
                return null;
            }
            final List<String> lines = maybeLayoutData.get();
            trace("got layout data");

            board = new Board();
            // Parse the board
            // This is ugly as but Java doesn't have so much stuff so whatever
            // I hate this syntax
            if (lines.size() != BOARD_SIZE_TILES) {
                debug("level layout invalid: expected {} lines but got {}", BOARD_SIZE_TILES, lines.size());
                return null;
            }
            for (int col = 0; col < BOARD_SIZE_TILES; col++) {
                final String line = lines.get(col);
                // According to https://edstem.org/au/courses/12539/discussion/1573048?comment=3524920
                // Lines can be shorter than required, we just assume the rest is grass
                // I still choose to fail on longer lines though
                if (line.length() > BOARD_SIZE_TILES) {
                    debug(
                            "level layout invalid: line {}: expected at most {} chars but got {}", col + 1,
                            BOARD_SIZE_TILES, line.length()
                    );
                    return null;
                }
                for (int row = 0; row < BOARD_SIZE_TILES; row++) {
                    final Tile tile;
                    if (row < line.length()) {
                        final char tileChar = line.charAt(row);
                        final Optional<Tile> maybeTile = Tile.fromChar(tileChar);
                        trace("tile char (line)[{00}] (char)[{00}]: '{}' -> {}", col, row, tileChar, maybeTile);
                        if (!maybeTile.isPresent()) {
                            debug("invalid tile char '{}'", tileChar);
                            return null;
                        }
                        tile = maybeTile.get();
                    }
                    else {
                        // Fallback when line is shorter than expected
                        // https://i.imgflip.com/3a8eu4.jpg
                        trace("tile char (line)[{00}] (char)[{00}] fallback as grass", col, row);
                        tile = new GrassTile();
                    }
                    board.setTile(row, col, tile);
                }
            }
        }
        catch (final Exception e) {
            debug("can't load level: failed parsing config json (layout)");
            return null;
        }

        // Parse waves separately since they 're complicated
        final List<Wave> waves;
        try {
            // TODO: Refactor this shitty code
            final JSONArray jWaves = conf.getJSONArray("waves");
            // Iter all waves and map to Wave objects
            final AtomicLong waveNumber = new AtomicLong(1);
            waves = jsonArrayToStream(jWaves)
                    .map((j) -> {
                        trace("wave: {}", j);
                        final double duration = j.getDouble("duration");
                        final double preWavePause = j.getDouble("pre_wave_pause");

                        final List<EnemyFactory> factories = jsonArrayToStream(j.getJSONArray("monsters"))
                                .map((m) -> {
                                    trace("\tmonster: {}", m);
                                    final long qty = m.getLong("quantity", 0);
                                    final BigInteger qty_big = qty <= 0 ? null : BigInteger.valueOf(qty);
                                    final double hp = m.getDouble("hp");
                                    final double manaPerKill = m.getDouble("mana_gained_on_kill");
                                    double speed = m.getDouble("speed");
                                    // pixels per frame -> tiles/sec
                                    speed = speed * REFERENCE_FPS / TILE_SIZE_PX;
                                    final double dmgMult = m.getDouble("armour");

                                    final HashMap<String, Func5<? extends Enemy, Double, Vector2, Double, Double, Double>>
                                            enemyTypes =
                                            new HashMap<String, Func5<? extends Enemy, Double, Vector2, Double, Double, Double>>() {{
                                                put("gremlin", GremlinEnemy::new);
                                                put("worm", WormEnemy::new);
                                                put("beetle", BeetleEnemy::new);
                                            }};
                                    final Func5<? extends Enemy, Double, Vector2, Double, Double, Double>
                                            ctor = enemyTypes.get(m.getString("type"));

                                    return new EnemyFactory(
                                            hp,
                                            speed,
                                            dmgMult,
                                            manaPerKill,
                                            qty_big,
                                            (fact) -> ctor.invoke(
                                                    fact.health,
                                                    new Vector2(0, 0),
                                                    fact.speed,
                                                    fact.damageMultiplier,
                                                    fact.manaGainedOnKill
                                            )
                                    );
                                })
                                .collect(Collectors.<EnemyFactory>toList());
                        final BigInteger totalQuantity =
                                factories
                                        .stream()
                                        .map((list) -> list.maxQuantity ==
                                                       null ? BigInteger.ZERO : list.maxQuantity)
                                        .reduce(BigInteger.valueOf(0), BigInteger::add);

                        return new Wave(
                                duration,
                                preWavePause,
                                totalQuantity.doubleValue() / duration,
                                waveNumber.getAndIncrement(),
                                factories
                        );
                    })
                    .collect(Collectors.toList());
        }
        catch (final RuntimeException e) {
            debug(e, "can't load game: failed parsing config json (waves)");
            return null;
        }

        debug("got level descriptor");
        return new GameDescriptor("Test Name", board, gameDataConfig, waves);
    }

    /**
     * Actually creates a game object, that can then be played
     */
    public static GameData createGame(final GameDescriptor desc) {
        trace("creating game from level desc: {}", desc);

        final Board board = desc.board;
        final List<Enemy> enemies = new ArrayList<>();
        final List<Projectile> projectiles = new ArrayList<>();
        final List<Wave> waves = desc.waves;
        final GameSpells spells = new GameSpells(new ManaSpell(desc.config.spell.manaPool.initialCost));

        final double mana_ = desc.config.mana.initialManaValue;
        final double manaCap_ = desc.config.mana.initialManaCap;

        final GameData game = new GameData(board, enemies, projectiles, waves, new ArrayList<>(), desc.config, spells);
        game.mana = mana_;
        game.manaCap = manaCap_;

        updatePathfinding(game);

        return game;
    }

    /**
     * Ticks (updates) the game.
     * <p>
     * It ticks would be too large, will split them up into smaller sub-ticks
     *
     * @param app       The app instance
     * @param game      The object storing the game data
     * @param deltaTime Time between the last frame start and the current frame start
     */
    public static void tickGameWithSubtick(final PApplet app, final GameData game, final double deltaTime) {
        /*
         * If the tick takes too long, split it up into multiple smaller sub-ticks
         * This should keep everything accurate, since we aren't making massive long ticks
         * No simulation errors, yay!
         *
         * This essentially calculates how many ticks (`numTicks`) we need to have each tick be under the threshold
         * and does that many ticks, with each tick being (`deltaTime/numTicks`)
         */

        final double speedMultiplier = game.fastForward ? FAST_FORWARD_SPEED : 1.0;

        final int numTicks = (int) Math.ceil(deltaTime * speedMultiplier / SUB_TICK_THRESHOLD);
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
            final PApplet app, final GameData game, final double gameDeltaTime, final double visualDeltaTime) {
        // Absorb mana through the atmosphere using mana accumulators
        game.mana += gameDeltaTime * game.config.mana.initialManaTrickle * game.manaGainMultiplier;
        game.mana = Math.min(game.mana, game.manaCap);

        // Spawn Enemies
        while (!game.waves.isEmpty()) {
            final Wave wave = game.waves.get(0);
            wave.tick(gameDeltaTime);
            if (wave.getWaveState() == Wave.WaveState.COMPLETE) {
                Loggers.GAMEPLAY.debug("wave complete, moving onto next");
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

        // Tick all enemies, projectiles, etc
        // Because ticking might involve modification of the streams (such as removing enemies that die)
        // We need to create a copy of the collections
        // So use a (pooled) list

        // When we render, we aggregate all the `Renderables`, and sort them into our map
        // Then iterate through the map in the correct order, and happy days
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
        return game.enemies.stream()
                           .filter(enemy -> nearPos.distance(enemy.position) < maxDist)
//                           .min(Comparator.comparingDouble(enemy -> nearPos.distanceSquared(enemy.position)))
                           .max(Comparator.comparingDouble(enemy -> enemy.pathProgress / enemy.path.positions.length))
                           .orElse(null);
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
            final PApplet app, final GameData game, final Tile tile,
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
}