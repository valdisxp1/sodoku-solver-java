package valdis.adamsons.sodoku;

import java.io.*;
import java.text.ParseException;

import static valdis.adamsons.sodoku.SodokuBoard.SODOKU_SIZE;

public class SodokuParser {
    private final File file;

    public SodokuParser(File file) {
        this.file = file;
    }

    public SodokuBoard parse() throws IOException, ParseException {
        SodokuBoard.Builder builder = new SodokuBoard.Builder();
        BufferedReader input = new BufferedReader(new FileReader(file));
        int lineNumber = 0;
        String line;
        while ((line = input.readLine()) != null) {
            if (!line.trim().isEmpty() && lineNumber >= SODOKU_SIZE) {
                throw new ParseException("Too many lines :'" + line + "'", lineNumber);
            }
            String[] array = (line + " ").split(";");
            if (array.length != SODOKU_SIZE) {
                throw new ParseException("the line '" + line + "' is the wrong length, expected " + SODOKU_SIZE + " elements split by \";\", but was " + array.length, lineNumber);
            }
            for (int x = 0; x < SODOKU_SIZE; x++) {
                String item = array[x].trim();
                if (!item.isEmpty()) {
                    builder.setNumber(x, lineNumber, Integer.parseInt(item));
                }
            }
            lineNumber++;

        }
        if (lineNumber != SODOKU_SIZE) {
            throw new ParseException("Expected  " + SODOKU_SIZE + " lines, but got " + lineNumber, lineNumber);
        }
        return builder.result();
    }
}
