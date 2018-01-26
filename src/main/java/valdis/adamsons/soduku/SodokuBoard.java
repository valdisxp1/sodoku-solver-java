package valdis.adamsons.soduku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SodokuBoard {
    public static final int SUDOKU_SIZE = 9;
    public static List<Area> areas() {
        ArrayList<Area> list = new ArrayList<Area>(SUDOKU_SIZE * 3);
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            list.add(Area.horizontalLine(i));
            list.add(Area.verticalLine(i));
            list.add(Area.square(i / SUDOKU_SIZE, i % SUDOKU_SIZE));
        }
        return list;
    }

    private final SodokuCell[] grid;

    private SodokuBoard(SodokuCell[] grid) {
        this.grid = grid;
    }

    private static void rangeCheck(int number) {
        if ( number < 1 || number >= SUDOKU_SIZE){
            throw new IndexOutOfBoundsException();
        }
    }

    public SodokuCell getCellAt(int x, int y) {
        rangeCheck(x);
        rangeCheck(y);
        return grid[x * SUDOKU_SIZE + y];
    }

    public Builder builder() {
        return new Builder(grid);
    }

    @Override
    public String toString() {
        //TODO pretty print
        return "";
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
            int arraySize = SUDOKU_SIZE * SUDOKU_SIZE;
            for (int i = 0; i < arraySize; i++) {
                grid[i] = SodokuCell.UNKNOWN;
            }
        }

        public SodokuCell getCellAt(int x, int y) {
            rangeCheck(x);
            rangeCheck(y);
            return grid[x * SUDOKU_SIZE + y];
        }

        public Builder setNumber(int x, int y, int number) {
            rangeCheck(x);
            rangeCheck(y);
            grid[x * SUDOKU_SIZE + y] = SodokuCell.known(number);
            return this;
        }

        public Builder removeNumber(int x, int y, int number) {
            rangeCheck(x);
            rangeCheck(y);
            grid[x * SUDOKU_SIZE + y] = getCellAt(x, y).without(number);
            return this;
        }

        public Builder removeNumberUnsolved(int x, int y, int number) {
            rangeCheck(x);
            rangeCheck(y);
            SodokuCell cell = getCellAt(x, y);
            if (!cell.isSolved()) {
                grid[x * SUDOKU_SIZE + y] = cell.without(number);
            }
            return this;
        }

        public SodokuBoard result() {
            return new SodokuBoard(grid);
        }
    }
}
