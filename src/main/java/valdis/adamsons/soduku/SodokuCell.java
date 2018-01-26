package valdis.adamsons.soduku;

import java.util.Arrays;

import static valdis.adamsons.soduku.SodokuBoard.SUDOKU_SIZE;

public class SodokuCell {
    private final boolean[] numbers;

    private SodokuCell(boolean[] numbers) {
        this.numbers = numbers;
    }

    public SodokuCell(int... nums) {
        boolean[] numbers = new boolean[SUDOKU_SIZE];
        for (int num : nums) {
            numbers[num - 1] = true;
        }
        this.numbers = numbers;
    }

    private void valueRangeCheck(int number) {
        if (number < 1 || number > SUDOKU_SIZE) {
            throw new IndexOutOfBoundsException("" + number);
        }
    }

    public boolean canContain(int number) {
        valueRangeCheck(number);
        return numbers[number - 1];
    }

    public boolean isSolved() {
        return getNumblerOfPossibleValues() == 1;
    }

    public boolean isUnsolvable() {
        return getNumblerOfPossibleValues() == 0;
    }


    public int firstValue() {
        for (int n = 1; n <= SUDOKU_SIZE; n++) {
            if (canContain(n)) {
                return n;
            }
        }
        return -1;
    }


    private int getNumblerOfPossibleValues() {
        int possibleValues = 0;
        for (int n = 1; n <= SUDOKU_SIZE; n++) {
            if (canContain(n)) {
                possibleValues++;
            }
        }
        return possibleValues;
    }

    public SodokuCell without(int i) {
        valueRangeCheck(i);
        if (canContain(i)) {
            boolean[] newNumbers = numbers.clone();
            newNumbers[i - 1] = false;
            return new SodokuCell(newNumbers);
        } else {
            return this;
        }
    }

    // TODO generate
    public static SodokuCell UNKNOWN = new SodokuCell(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static SodokuCell known(int i) {
        return new SodokuCell(i);
    }

    @Override
    public String toString() {
        if (isUnsolvable()) {
            return "X";
        } else {
            StringBuilder builder = new StringBuilder();
            for (int n = 1; n <= SUDOKU_SIZE; n++) {
                if (canContain(n)) {
                    builder.append("" + n);
                }
            }
            String str = builder.toString();
            if (str.length() > 1) {
                return "[" + str + "]";
            } else {
                return str;
            }
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SodokuCell)) return false;
        SodokuCell that = (SodokuCell) o;
        return Arrays.equals(numbers, that.numbers);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(numbers);
    }
}
