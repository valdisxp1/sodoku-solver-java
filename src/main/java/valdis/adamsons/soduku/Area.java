package valdis.adamsons.soduku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static valdis.adamsons.soduku.SodokuBoard.SODOKU_SIZE;
import static valdis.adamsons.soduku.SodokuBoard.SODOKU_SIZE_SQRT;

public class Area {
    /**
     * Unmodifiable.
     */
    public final List<Coordinates> data;

    private Area(Coordinates[] data) {
        this.data = Collections.unmodifiableList(Arrays.asList(data));
    }

    public static Area horizontalLine(int y) {
        Coordinates[] data = new Coordinates[SODOKU_SIZE];
        for (int x = 0; x < SODOKU_SIZE; x++) {
            data[x] = new Coordinates(x, y);
        }
        return new Area(data);
    }

    public static Area verticalLine(int x) {
        Coordinates[] data = new Coordinates[SODOKU_SIZE];
        for (int y = 0; y < SODOKU_SIZE; y++) {
            data[y] = new Coordinates(x, y);
        }
        return new Area(data);
    }

    public static Area square(int relativeX, int relativeY) {
        int side = SODOKU_SIZE_SQRT;
        int cornerX = relativeX * side;
        int cornerY = relativeY * side;
        Coordinates[] data = new Coordinates[SODOKU_SIZE];
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
