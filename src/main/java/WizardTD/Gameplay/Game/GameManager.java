package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Tiles.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.dataflow.qual.*;
import processing.data.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import static WizardTD.GameConfig.*;
import static java.util.Optional.*;
import static org.tinylog.Logger.*;

/**
 * Static class that
 */
@UtilityClass
public class GameManager {

    @NonNull
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

    @NonNull
    @SideEffectFree
    private Optional<@NonNull List<@NonNull String>> loadLevelLayout(@NonNull final String fileName) {
        debug("loading level layout");
        final Path path = Paths.get(fileName);
        trace("level layout path={}", path);
        final List<String> dataStr;
        try {
            trace("reading layout file");
            dataStr = Files.readAllLines(path);
            trace("layout file read: contents are \n\"\n{}\n\"",
                    String.join("\n",
                            (Iterable<String>) dataStr.stream().map((s) -> "'" + s + "'")::iterator
                    )
            );

            return Optional.of(dataStr);
        } catch (final IOException e) {
            debug(e, "error reading layout file");
            return empty();
        }
    }

    @NonNull
    @SideEffectFree
    public Optional<GameDescriptor> loadGameDescriptor() {
        debug("loading level descriptor");

        // Load the config object
        final Optional<JSONObject> maybeConfigJson = loadGameConfig();
        if (!maybeConfigJson.isPresent()) {
            debug("can't load game: failed getting config");
            return empty();
        }
        final JSONObject conf = maybeConfigJson.get();

        // Load a bunch of stuff
        // Any invalid json access will throw a RuntimeException
        // So catch them all in one go to simplify this
        final GameDataConfig gameDataConfig;
        try {
            // Names are purposefully like this to match JSON
            final double initial_tower_range = conf.getDouble("initial_tower_range");
            final double initial_tower_firing_speed = conf.getDouble("initial_tower_firing_speed");
            final double initial_tower_damage = conf.getDouble("initial_tower_damage");
            final double initial_mana = conf.getDouble("initial_mana");
            final double initial_mana_cap = conf.getDouble("initial_mana_cap");
            final double initial_mana_gained_per_second = conf.getDouble("initial_mana_gained_per_second");
            final double tower_cost = conf.getDouble("tower_cost");
            final double mana_pool_spell_initial_cost = conf.getDouble("mana_pool_spell_initial_cost");
            final double mana_pool_spell_cost_increase_per_use = conf.getDouble("mana_pool_spell_cost_increase_per_use");
            final double mana_pool_spell_cap_multiplier = conf.getDouble("mana_pool_spell_cap_multiplier");
            final double mana_pool_spell_mana_gained_multiplier = conf.getDouble("mana_pool_spell_mana_gained_multiplier");

            gameDataConfig = new GameDataConfig(
                    new GameDataConfig.TowerConfig(
                            initial_tower_range,
                            initial_tower_firing_speed,
                            initial_tower_damage,
                            tower_cost
                    ),
                    new GameDataConfig.ManaConfig(
                            initial_mana,
                            initial_mana_cap,
                            initial_mana_gained_per_second
                    ),
                    new GameDataConfig.SpellConfig(
                            new GameDataConfig.SpellConfig.ManaPool(
                                    mana_pool_spell_initial_cost,
                                    mana_pool_spell_cost_increase_per_use,
                                    mana_pool_spell_mana_gained_multiplier,
                                    mana_pool_spell_cap_multiplier
                            )
                    )
            );
            trace("level config={}", gameDataConfig);
        } catch (final RuntimeException e) {
            debug(e, "can't load level: failed parsing config json: (simple values)");
            return empty();
        }

        final Board board;
        try {
            final String layoutFileName = conf.getString("layout");
            trace("level layout file at {}", layoutFileName);
            final Optional<List<String>> maybeLayoutData = loadLevelLayout(layoutFileName);
            if (!maybeLayoutData.isPresent()) {
                debug("can't load level: couldn't load layout");
                return empty();
            }
            final List<String> lines = maybeLayoutData.get();
            trace("got layout data");

            board = new Board();
            // Parse the board
            // This is ugly as but Java doesn't have so much stuff so whatever
            // I hate this syntax
            if (lines.size() != BOARD_SIZE_TILES) {
                debug("level layout invalid: expected {} lines but got {}", BOARD_SIZE_TILES, lines.size());
                return empty();
            }
            for (int row = 0; row < BOARD_SIZE_TILES; row++) {
                final String line = lines.get(row);
                if (line.length() != BOARD_SIZE_TILES) {
                    debug("level layout invalid: line {}: expected {} chars but got {}", row + 1, BOARD_SIZE_TILES, line.length());
                    return empty();
                }
                for (int col = 0; col < BOARD_SIZE_TILES; col++) {
                    final char tileChar = line.charAt(col);
                    final Optional<Tile> tile = Tile.fromChar(tileChar);
                    trace("tile char [{00}] [{00}]: '{}' -> {}", row, col, tileChar, tile);
                    if (!tile.isPresent()) {
                        debug("invalid tile char '{}'", tileChar);
                        return empty();
                    }
                    board.setTile(row, col, tile.get());
                }
            }
        } catch (final Exception e) {
            debug("can't load level: failed parsing config json (layout)");
            return empty();
        }

//        // Parse waves separately since they're complicated
//        try {
//            // TODO: Waves
//            // TODO: Refactor this shitty code
//            JSONArray jWaves = conf.getJSONArray("waves");
//            // Iter all waves and map to Wave objects
//            JsonArrayToStream(jWaves)
//                    .map((j) -> {
//                        trace("wave: {}", j);
//                        return new Wave(
//                                j.getDouble("duration"),
//                                j.getDouble("pre_wave_pause"),
//                                JsonArrayToStream(j.getJSONArray("monster"))
//                                        .map((m) -> {
//                                            trace("\tmonster: {}", m);
//                                            long qty = m.getLong("quantity");
//                                            double hp = m.getDouble("hp");
//                                            double manaPerKill = m.getDouble("mana_gained_on_kill");
//                                            double speed = m.getDouble("speed");
//                                            double dmgMult = m.getDouble("armour");
//                                            // TODO: Refactor to abstract static method to parse from json?
//                                            return switch (m.getString("type")){
//                                                case "gremlin" -> BasicEnemyFactory.;
//                                                case "beetle" -> new Beetle();
//                                                case "worm" -> new Worm();
//                                            };
//                                        })
//                        );
//                    })
//        } catch (RuntimeException e) {
//            debug(e, "can't load game: failed parsing config json (waves)");
//            return empty();
//        }

        debug("got level descriptor");
        return Optional.of(new GameDescriptor(
                "Test Name",
                board,
                gameDataConfig,
                new ArrayList<>() //TODO: waves
        ));
    }

    @NonNull Stream<JSONObject> JsonArrayToStream(final JSONArray arr) {
        return IntStream.range(0, arr.size()).mapToObj(arr::getJSONObject);
    }

}
