package valdis.adamsons.sodoku;

import java.util.Arrays;

import static valdis.adamsons.sodoku.SodokuBoard.SODOKU_SIZE;

public class SodokuCell {
    private final boolean[] numbers;
    private final int possibleValues;

    private SodokuCell(boolean[] numbers, int possibleValues) {
        this.numbers = numbers;
        this.possibleValues = possibleValues;
    }

    public SodokuCell(int... nums) {
        boolean[] numbers = new boolean[SODOKU_SIZE];
        for (int num : nums) {
            numbers[num - 1] = true;
        }
        this.possibleValues = numbers.length;
        this.numbers = numbers;
    }

    private void valueRangeCheck(int number) {
        if (number < 1 || number > SODOKU_SIZE) {
            throw new IndexOutOfBoundsException("" + number);
        }
    }

    public boolean canContain(int number) {
        valueRangeCheck(number);
        return numbers[number - 1];
    }

    public boolean isSolved() {
        return possibleValues == 1;
    }

    public boolean isUnsolvable() {
        return possibleValues == 0;
    }

    public int firstValue() {
        for (int n = 1; n <= SODOKU_SIZE; n++) {
            if (canContain(n)) {
                return n;
            }
        }
        return -1;
    }

    public SodokuCell without(int i) {
        valueRangeCheck(i);
        if (canContain(i)) {
            boolean[] newNumbers = numbers.clone();
            newNumbers[i - 1] = false;
            return new SodokuCell(newNumbers, this.possibleValues - 1);
        } else {
            return this;
        }
    }

    public static final SodokuCell UNKNOWN;

    static {
        boolean[] numbers = new boolean[SODOKU_SIZE];
        for (int num = 1; num <= SODOKU_SIZE; num++) {
            numbers[num - 1] = true;
        }
        UNKNOWN = new SodokuCell(numbers, SODOKU_SIZE);
    }

    public static SodokuCell known(int i) {
        return new SodokuCell(i);
    }

    @Override
    public String toString() {
        if (this.equals(UNKNOWN)) {
            return " ";
        } else if (isUnsolvable()) {
            return "X";
        } else {
            StringBuilder builder = new StringBuilder();
            for (int n = 1; n <= SODOKU_SIZE; n++) {
                if (canContain(n)) {
                    builder.append(n);
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
