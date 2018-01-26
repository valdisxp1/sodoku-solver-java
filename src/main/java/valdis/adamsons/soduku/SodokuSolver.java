package valdis.adamsons.soduku;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class SodokuSolver {
    private final SodokuBoard firstBoard;
    private List<Area> areas;
    private SodokuBoard currentBoard;
    private SodokuBoard oldBoard = null;

    public SodokuSolver(SodokuBoard firstBoard) {
        this.firstBoard = firstBoard;
        this.areas = SodokuBoard.areas();
        this.currentBoard = firstBoard;
    }

    public boolean isDone() {
        // or it is solved
        return currentBoard.equals(oldBoard);
    }

    public void iterate() {
        oldBoard = currentBoard;
        SodokuBoard.Builder builder = currentBoard.builder();
        areas.forEach(
                area ->
                {
                    List<Integer> solvedValues = area.data.stream()
                            .map(coordinates -> builder.getCellAt(coordinates.x, coordinates.y))
                            .filter(SodokuCell::isSolved)
                            .map(SodokuCell::firstValue)
                            .collect(Collectors.toList());
                    // TODO check if values are unique
                    solvedValues.forEach(
                            value ->
                                    area.data.forEach(
                                            coordinates -> builder.removeNumberUnsolved(coordinates.x, coordinates.y, value)
                                    ));
                }
        );
        currentBoard = builder.result();
    }

    public SodokuBoard solve() {
        while(!isDone()){
            iterate();
        }
        return currentBoard;
    }

    public static void main(String[] args) throws IOException, ParseException {
        //TODO check argcount
        SodokuBoard board = new SodokuParser(new File(args[0])).parse();
        System.out.println(board);
        SodokuSolver solver = new SodokuSolver(board);
        System.out.println(solver.solve());
    }
}
