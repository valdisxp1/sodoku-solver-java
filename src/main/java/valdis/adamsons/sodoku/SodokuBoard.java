package valdis.adamsons.sodoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SodokuBoard {
    public static final int SODOKU_SIZE = 9;
    public static final int SODOKU_SIZE_SQRT = (int) Math.sqrt(SODOKU_SIZE);

    public static List<Area> areas() {
        ArrayList<Area> list = new ArrayList<Area>(SODOKU_SIZE * 3);
        for (int i = 0; i < SODOKU_SIZE; i++) {
            list.add(Area.horizontalLine(i));
            list.add(Area.verticalLine(i));
            list.add(Area.square(i / SODOKU_SIZE_SQRT, i % SODOKU_SIZE_SQRT));
        }
        return list;
    }

    private final SodokuCell[] grid;

    private SodokuBoard(SodokuCell[] grid) {
        this.grid = grid;
    }

    static void coordinateRangeCheck(int number) {
        if (number < 0 || number > SODOKU_SIZE) {
            throw new IndexOutOfBoundsException(""+number);
        }
    }

    public SodokuCell getCellAt(int x, int y) {
        coordinateRangeCheck(x);
        coordinateRangeCheck(y);
        return grid[x * SODOKU_SIZE + y];
    }

    public Builder builder() {
        return new Builder(grid);
    }

    private static final String HLINE = generateHLine();

    private static String generateHLine(){
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < SODOKU_SIZE; x++) {
            if (x % SODOKU_SIZE_SQRT == 0) {
                builder.append('+');
            }
            builder.append('-');
        }
        builder.append("+\n");
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < SODOKU_SIZE; y++) {
            if (y % SODOKU_SIZE_SQRT == 0) {
                builder.append(HLINE);
            }
            for (int x = 0; x < SODOKU_SIZE; x++) {
                if (x % SODOKU_SIZE_SQRT == 0) {
                    builder.append('|');
                }
                builder.append(getCellAt(x, y).toString());
            }
            builder.append("|\n");
        }
        builder.append(HLINE);
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SodokuBoard)) return false;
        SodokuBoard that = (SodokuBoard) o;
        return Arrays.equals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(grid);
    }

    public static class Builder {
        private SodokuCell[] grid;

        private Builder(SodokuCell[] grid) {
            this.grid = grid.clone();
        }

        public Builder() {
            int arraySize = SODOKU_SIZE * SODOKU_SIZE;
            grid = new SodokuCell[arraySize];
            for (int i = 0; i < arraySize; i++) {
                grid[i] = SodokuCell.UNKNOWN;
            }
        }

        public SodokuCell getCellAt(Coordinates coordinates) {
            return grid[coordinates.x * SODOKU_SIZE + coordinates.y];
        }

        public Builder setNumber(int x, int y, int number) {
            coordinateRangeCheck(x);
            coordinateRangeCheck(y);
            grid[x * SODOKU_SIZE + y] = SodokuCell.known(number);
            return this;
        }

        public Builder removeNumberFromUnsolved(Coordinates coordinates, int number) {
            SodokuCell cell = getCellAt(coordinates);
            if (!cell.isSolved()) {
                grid[coordinates.x * SODOKU_SIZE + coordinates.y] = cell.without(number);
            }
            return this;
        }

        public SodokuBoard result() {
            return new SodokuBoard(grid);
        }
    }
}
