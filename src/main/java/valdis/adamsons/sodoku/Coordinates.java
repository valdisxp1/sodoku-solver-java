package valdis.adamsons.sodoku;

import java.util.Objects;

public class Coordinates {
    final public int x, y;

    public Coordinates(int x, int y) {
        SodokuBoard.coordinateRangeCheck(x);
        SodokuBoard.coordinateRangeCheck(y);
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "" + x + ":" + y;
    }
}
