package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Spawners.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

import static WizardTD.GameConfig.*;
import static org.tinylog.Logger.*;

@ToString
@EqualsAndHashCode
public final class GameData {

    public @NonNull Board board;

    public @NonNull List<@NonNull Enemy> enemies;

    public @NonNull List<@NonNull Tower> towers;

    public @NonNull List<@NonNull Wave> waves;

    public @NonNull WizardHouse wizardHouse;

    public @NonNull GameDataConfig config;

    private GameData(@NonNull final Board board, @NonNull final List<@NonNull Enemy> enemies, @NonNull final List<@NonNull Tower> towers, @NonNull final List<@NonNull Wave> waves, @NonNull final WizardHouse wizardHouse, @NonNull final GameDataConfig config) {
        this.board = board;
        this.enemies = enemies;
        this.towers = towers;
        this.waves = waves;
        this.wizardHouse = wizardHouse;
        this.config = config;
    }

    public static @NonNull Optional<GameData> fromGameDescriptor(@NonNull final GameDescriptor desc) {
        trace("creating game from level desc: {}", desc);

        final Board board = desc.board;
        final List<Tower> towers = new ArrayList<>();
        final List<Enemy> enemies = new ArrayList<>();
        final List<Wave> waves = new ArrayList<>();

        trace("locating wizard's house");
        WizardHouse fourPrivetDrive = null;
        for (int row = 0; row < BOARD_SIZE_TILES; row++) {
            for (int col = 0; col < BOARD_SIZE_TILES; col++) {
                final Tile tile = desc.board.getTile(row, col);
                if (tile instanceof WizardHousePlaceholder) {
                    if (null != fourPrivetDrive) warn("only one wizard allowed");
                    else trace("You're a WizardHouse Harry!\nI'm a WHAT?\nA \033[095m{}\033[000m", tile);
                    fourPrivetDrive = new WizardHouse();
//                    desc.board.setTile(row, col, fourPrivetDrive); // Update the board entry to remove placeholder
                    fourPrivetDrive.mana = desc.config.mana.initialManaValue;
                    fourPrivetDrive.manaCap = desc.config.mana.initialManaCap;
                    fourPrivetDrive.manaTrickle = desc.config.mana.initialManaTrickle;
                }
            }
        }
        if (fourPrivetDrive == null) {
            warn("expected a wizard but didn't find house");
            return Optional.empty();
        }

        error("TODO: Waves");
        return Optional.of(new GameData(board, enemies, towers, waves, fourPrivetDrive, desc.config));
    }

}