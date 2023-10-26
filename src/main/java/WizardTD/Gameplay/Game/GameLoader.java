package WizardTD.Gameplay.Game;

import WizardTD.*;
import WizardTD.Delegates.*;
import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Projectiles.*;
import WizardTD.Gameplay.Spawners.*;
import WizardTD.Gameplay.Spells.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.experimental.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.dataflow.qual.*;
import processing.data.*;

import java.io.*;
import java.math.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

import static WizardTD.Ext.JsonExt.*;
import static WizardTD.GameConfig.*;
import static java.util.Optional.empty;
import static org.tinylog.Logger.debug;
import static org.tinylog.Logger.trace;

@UtilityClass
public class GameLoader {
    /**
     * Loads the game config from disk, and returns it as a JSON object
     * @param configPath The path to the config file
     */
    @SideEffectFree
    private Optional<JSONObject> loadGameConfig(final String configPath) {
        debug("loading game config");
        final Path path = Paths.get(configPath);
        trace("config path={}", path);
        final String dataStr;
        try {
            trace("reading config file");
            dataStr = new String(Files.readAllBytes(path));
            trace("config file read: contents are \"{}\"", dataStr);
        } catch (final IOException e) {
            debug(e, "error reading config file");
            return empty();
        }
        try {
            final JSONObject data = JSONObject.parse(dataStr);
            trace("parsed config: {}", data);
            return Optional.of(data);
        } catch (final RuntimeException e) {
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
        } catch (final IOException e) {
            debug(e, "error reading layout file");
            return empty();
        }
    }

    /**
     * Loads the game descriptor from the disk
     * <p>
     * This can then be used to actually instantiate the gameData object
     * @param configPath The path to the config file
     */
    @SideEffectFree
    public @Nullable GameDescriptor loadGameDescriptor(final String configPath) {
        debug("loading game descriptor");

        // Load the config object
        final Optional<JSONObject> maybeConfigJson = loadGameConfig(configPath);
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
        } catch (final RuntimeException e) {
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
        } catch (final Exception e) {
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
        } catch (final RuntimeException e) {
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

        final GameData game = new GameData(
                GameState.PLAYING, board, enemies, projectiles, waves,
                new ArrayList<>(), desc.config, spells
        );
        game.mana = mana_;
        game.manaCap = manaCap_;

        GameManager.updatePathfinding(game);

        return game;
    }

    /**
     * Restarts the game, by creating a new game
     */
    public void resetGame(final App app) {
        // It's genuinely almost impossible to get game cloning working
        // There are references, to references, to references etc.
        // The only feasible way without a metric fuck-ton of boilerplate
        // is to just reload the game from disk
        
        // As a bonus, it allows for hot-reloading from disk
        app.gameData =  createGame(loadGameDescriptor(GameConfig.CONFIG_PATH));
    }
}
