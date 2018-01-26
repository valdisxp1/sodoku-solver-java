package valdis.adamsons.soduku;

import static valdis.adamsons.soduku.SodokuBoard.SUDOKU_SIZE;

public class SodokuCell {
    private final boolean[] numbers;

    private SodokuCell(boolean[] numbers){
        this.numbers = numbers;
    }

    public SodokuCell(int... nums) {
        boolean[] numbers = new boolean[SUDOKU_SIZE];
        for (int num : nums) {
            numbers[num - 1] = true;
        }
        this.numbers = numbers;
    }

    private void rangeCheck(int number) {
        if ( number < 1 || number >= SUDOKU_SIZE){
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean canContain(int number) {
        rangeCheck(number);
        return numbers[number - 1];
    }

    public boolean isSolved() {
        return getNumblerOfPossibleValues() == 1;
    }

    public boolean isUnsolvable() {
        return getNumblerOfPossibleValues() == 0;
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
        rangeCheck(i);
        if (canContain(i)) {
            boolean[] newNumbers = numbers.clone();
            newNumbers[i - 1] = false;
            return new SodokuCell(newNumbers);
        } else {
            return this;
        }
    }

    public static SodokuCell UNKNOWN = new SodokuCell();

    public static SodokuCell known(int i) {
        return new SodokuCell(i);
    }
}
