package valdis.adamsons.soduku;

import static valdis.adamsons.soduku.SudokuBoard.SUDOKU_SIZE;

public class SudokuCell {
    private final boolean[] numbers;

    private SudokuCell(boolean[] numbers){
        this.numbers = numbers;
    }

    public SudokuCell(int... nums) {
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
        return false;
    }

    public SudokuCell without(int i) {
        rangeCheck(i);
        if (canContain(i)) {
            boolean[] newNumbers = numbers.clone();
            newNumbers[i - 1] = false;
            return new SudokuCell(newNumbers);
        } else {
            return this;
        }
    }

    public static SudokuCell UNKNOWN = new SudokuCell();

    public static SudokuCell known(int i) {
        return new SudokuCell(i);
    }
}
