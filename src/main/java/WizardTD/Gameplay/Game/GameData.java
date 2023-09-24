package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Enemies.*;
import WizardTD.Gameplay.Projectiles.*;
import WizardTD.Gameplay.Spawners.*;
import WizardTD.Gameplay.Tiles.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

import static WizardTD.GameConfig.*;
import static org.tinylog.Logger.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class GameData {

    public @NonNull Board board;

    public @NonNull List<@NonNull Enemy> enemies;
    public @NonNull List<@NonNull Projectile> projectiles;

    public @NonNull List<@NonNull Wave> waves;

    public @NonNull WizardHouseTile wizardHouse;

    public @NonNull GameDataConfig config;

    public static @NonNull Optional<GameData> fromGameDescriptor(@NonNull final GameDescriptor desc) {
        trace("creating game from level desc: {}", desc);

        final Board board = desc.board;
        final List<Enemy> enemies = new ArrayList<>();
        final List<Wave> waves = new ArrayList<>();

        trace("locating wizard's house");
        WizardHouseTile fourPrivetDrive = null;
        for (int row = 0; row < BOARD_SIZE_TILES; row++) {
            for (int col = 0; col < BOARD_SIZE_TILES; col++) {
                final Tile tile = desc.board.getTile(row, col);
                if (tile instanceof WizardHousePlaceholderTile) {
                    if (null != fourPrivetDrive) warn("only one wizard allowed");
                    else trace("You're a WizardHouseTile Harry!\nI'm a WHAT?\nA \033[095m{}\033[000m", tile);
                    fourPrivetDrive = new WizardHouseTile();
                    desc.board.setTile(row, col, fourPrivetDrive); // Update the board entry to remove placeholder
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
        return Optional.of(new GameData(board, enemies, new ArrayList<>(), waves, fourPrivetDrive, desc.config));
    }

}