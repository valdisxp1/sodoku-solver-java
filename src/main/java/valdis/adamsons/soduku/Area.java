package valdis.adamsons.soduku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static valdis.adamsons.soduku.SodokuBoard.SUDOKU_SIZE;
import static valdis.adamsons.soduku.SodokuBoard.SUDOKU_SIZE_SQRT;

public class Area {
    /**
     * Unmodifiable.
     */
    public final List<Coordinates> data;

    private Area(Coordinates[] data) {
        this.data = Collections.unmodifiableList(Arrays.asList(data));
    }

    public static Area horizontalLine(int y) {
        Coordinates[] data = new Coordinates[SUDOKU_SIZE];
        for (int x = 0; x < SUDOKU_SIZE; x++) {
            data[x] = new Coordinates(x, y);
        }
        return new Area(data);
    }

    public static Area verticalLine(int x) {
        Coordinates[] data = new Coordinates[SUDOKU_SIZE];
        for (int y = 0; y < SUDOKU_SIZE; y++) {
            data[y] = new Coordinates(x, y);
        }
        return new Area(data);
    }

    public static Area square(int relativeX, int relativeY) {
        int side = SUDOKU_SIZE_SQRT;
        int cornerX = relativeX * side;
        int cornerY = relativeY * side;
        Coordinates[] data = new Coordinates[SUDOKU_SIZE];
        for (int x = 0; x < side; x++) {
            for (int y = 0; y < side; y++) {
                data[x * side + y] = new Coordinates(cornerX + x, cornerY + y);
            }
        }
        return new Area(data);
    }

    @Override
    public String toString() {
        return "Area("+ data + ')';
    }
}
