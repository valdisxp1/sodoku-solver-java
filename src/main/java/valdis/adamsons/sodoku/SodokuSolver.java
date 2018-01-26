package valdis.adamsons.sodoku;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class SodokuSolver {
    public final SodokuBoard firstBoard;
    private List<Area> areas;
    private SodokuBoard currentBoard;
    private SodokuBoard oldBoard = null;
    private boolean unsolvable = false;

    public SodokuSolver(SodokuBoard firstBoard) {
        this.firstBoard = firstBoard;
        this.areas = SodokuBoard.areas();
        this.currentBoard = firstBoard;
    }

    public boolean isDone() {
        return unsolvable || currentBoard.equals(oldBoard);
    }

    public void iterate() {
        oldBoard = currentBoard;
        SodokuBoard.Builder builder = currentBoard.builder();
        areas.forEach(
                area ->
                {
                    List<Integer> solvedValues = area.data.stream()
                            .map(coordinates -> builder.getCellAt(coordinates))
                            .filter(SodokuCell::isSolved)
                            .map(SodokuCell::firstValue)
                            .sorted()
                            .collect(Collectors.toList());

                    if (existDuplicates(solvedValues)) {
                        unsolvable = true;
                        return;
                    }

                    solvedValues.forEach(
                            value ->
                                    area.data.forEach(
                                            coordinates -> builder.removeNumberFromUnsolved(coordinates, value)
                                    ));
                }
        );
        currentBoard = builder.result();
    }

    private boolean existDuplicates(List<Integer> sortedValues) {
        int oldValue = -1;
        for (Integer value : sortedValues) {
            if (value == oldValue) {
                return true;
            }
            oldValue = value;
        }
        return false;
    }

    public SodokuBoard solve() {
        while (!isDone()) {
            iterate();
        }
        return currentBoard;
    }

    public static void main(String[] args) throws IOException, ParseException {
        if (args.length < 1) {
            System.err.println("Please specify the filename to be parsed");
            System.exit(-1);
        }
        String filename = args[0];
        SodokuBoard board = new SodokuParser(new File(filename)).parse();
        System.out.println("Parsed from " + filename + ":");
        System.out.println(board);
        SodokuSolver solver = new SodokuSolver(board);
        System.out.println("Solution:");
        System.out.println(solver.solve());
    }
}
