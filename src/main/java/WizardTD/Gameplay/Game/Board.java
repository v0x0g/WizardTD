package WizardTD.Gameplay.Game;

import WizardTD.Gameplay.Tiles.*;
import lombok.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.*;
import java.util.*;
import java.util.stream.*;

import static WizardTD.GameConfig.*;

@EqualsAndHashCode
@ExtensionMethod(Arrays.class)
public final class Board {

    /**
     * Array storing the map of tiles for the board.
     * <p>
     * This should always be sized exactly `[BOARD_SIZE_TILES][BOARD_SIZE_TILES]`.
     * Indices are `[row][col]`
     */

    public final @NonNull Tile @NonNull [] @NonNull [] tiles =
            // Default value is filled with invalid tiles
            IntStream.range(0, BOARD_SIZE_TILES).mapToObj(
                    $_ -> IntStream.range(0, BOARD_SIZE_TILES).mapToObj($__ -> (@NonNull Tile) new InvalidTile())
                                   .toArray(Tile[]::new)).toArray(Tile[][]::new);

    public @NonNull Tile getTile(final int row, final int col) {
        return this.tiles[row][col];
    }

    public @NonNull Optional<Tile> maybeGetTile(final int row, final int col) {
        if (row < 0 || col < 0 || row >= BOARD_SIZE_TILES || col >= BOARD_SIZE_TILES) return Optional.empty();
        return Optional.of(getTile(row, col));
    }

    public @NonNull <T extends Tile> Optional<T> maybeGetTileGeneric(
            final @NonNull Class<T> tClass, final int row, final int col) {
        final @NonNull Optional<Tile> tile = maybeGetTile(row, col);
        // Because java is fucking dumb, I can't just do a simple `if instanceof T` check with the generic type
        // Because when type erasure happens, T just vanishes to nothing, and therefore it's always true even when it's wrong
        // See https://stackoverflow.com/a/17072077
        //  if (tile.isPresent() && tile.get() instanceof T) return (T) tile.get();

        if (tile.isPresent() && tClass.isAssignableFrom(tile.get().getClass())) {
            // Cast is safe because we asserted types match above
            // noinspection unchecked
            return Optional.of((T) tile.get());
        }
        return Optional.empty();
    }

    public void setTile(final int row, final int col, @NonNull final Tile tile) {
        this.tiles[row][col] = tile;
        // As a note:
        // This won't work if the tile instances are shared across positions (instanced tiling)
        // So we hope the user creates a new instance each time
        tile.setPosX(row);
        tile.setPosY(col);
    }

    @Override
    public @NonNull String toString() {
        // Don't question it
        // It works, ok???
        // Stop judging me like that

        // It maps each tile, translating via ToString and then getting the first char
        // And then prints the tiles in a grid
        return MessageFormat.format("Board'{'tiles={0}'}'", String.join(
                "\n", (Iterable<String>) this.tiles.stream().map(row -> String.join(
                        " ",
                        (Iterable<String>) row.stream().map(tile -> tile.toString().substring(0, 1))::iterator
                ))::iterator));
    }

    /**
     * Returns a stream over all the tiles in this board
     */
    public @NonNull Stream<Tile> stream() {
        return this.tiles.stream().flatMap(row -> row.stream());
    }
}